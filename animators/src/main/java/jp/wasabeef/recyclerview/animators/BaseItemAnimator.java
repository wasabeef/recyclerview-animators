package jp.wasabeef.recyclerview.animators;
/*
 * Copyright (C) 2020 Wasabeef
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.recyclerview.widget.SimpleItemAnimator;
import java.util.ArrayList;
import java.util.List;
import jp.wasabeef.recyclerview.animators.holder.AnimateViewHolder;
import jp.wasabeef.recyclerview.internal.ViewHelper;

public abstract class BaseItemAnimator extends SimpleItemAnimator {

  private static final boolean DEBUG = false;

  private final ArrayList<ViewHolder> pendingRemovals = new ArrayList<>();
  private final ArrayList<ViewHolder> pendingAdditions = new ArrayList<>();
  private final ArrayList<MoveInfo> pendingMoves = new ArrayList<>();
  private final ArrayList<ChangeInfo> pendingChanges = new ArrayList<>();

  private final ArrayList<ArrayList<ViewHolder>> additionsList = new ArrayList<>();
  private final ArrayList<ArrayList<MoveInfo>> movesList = new ArrayList<>();
  private final ArrayList<ArrayList<ChangeInfo>> changesList = new ArrayList<>();

  protected ArrayList<ViewHolder> addAnimations = new ArrayList<>();
  private final ArrayList<ViewHolder> moveAnimations = new ArrayList<>();
  protected ArrayList<ViewHolder> removeAnimations = new ArrayList<>();
  private final ArrayList<ViewHolder> changeAnimations = new ArrayList<>();

  protected Interpolator interpolator = new DecelerateInterpolator();

  private static class MoveInfo {

    public ViewHolder holder;
    public int fromX, fromY, toX, toY;

    private MoveInfo(ViewHolder holder, int fromX, int fromY, int toX, int toY) {
      this.holder = holder;
      this.fromX = fromX;
      this.fromY = fromY;
      this.toX = toX;
      this.toY = toY;
    }
  }

  private static class ChangeInfo {

    public ViewHolder oldHolder, newHolder;
    public int fromX, fromY, toX, toY;

    private ChangeInfo(ViewHolder oldHolder, ViewHolder newHolder) {
      this.oldHolder = oldHolder;
      this.newHolder = newHolder;
    }

    private ChangeInfo(ViewHolder oldHolder, ViewHolder newHolder, int fromX, int fromY, int toX,
      int toY) {
      this(oldHolder, newHolder);
      this.fromX = fromX;
      this.fromY = fromY;
      this.toX = toX;
      this.toY = toY;
    }

    @Override public String toString() {
      return "ChangeInfo{" + "oldHolder=" + oldHolder + ", newHolder=" + newHolder + ", fromX="
        + fromX + ", fromY=" + fromY + ", toX=" + toX + ", toY=" + toY + '}';
    }
  }

  public BaseItemAnimator() {
    super();
    setSupportsChangeAnimations(false);
  }

  public void setInterpolator(Interpolator interpolator) {
    this.interpolator = interpolator;
  }

  @Override public void runPendingAnimations() {
    boolean removalsPending = !pendingRemovals.isEmpty();
    boolean movesPending = !pendingMoves.isEmpty();
    boolean changesPending = !pendingChanges.isEmpty();
    boolean additionsPending = !pendingAdditions.isEmpty();
    if (!removalsPending && !movesPending && !additionsPending && !changesPending) {
      // nothing to animate
      return;
    }
    // First, remove stuff
    for (ViewHolder holder : pendingRemovals) {
      doAnimateRemove(holder);
    }
    pendingRemovals.clear();
    // Next, move stuff
    if (movesPending) {
      final ArrayList<MoveInfo> moves = new ArrayList<>(pendingMoves);
      movesList.add(moves);
      pendingMoves.clear();
      Runnable mover = new Runnable() {
        @Override public void run() {
          boolean removed = movesList.remove(moves);
          if (!removed) {
            // already canceled
            return;
          }
          for (MoveInfo moveInfo : moves) {
            animateMoveImpl(moveInfo.holder, moveInfo.fromX, moveInfo.fromY, moveInfo.toX,
              moveInfo.toY);
          }
          moves.clear();
        }
      };
      if (removalsPending) {
        View view = moves.get(0).holder.itemView;
        ViewCompat.postOnAnimationDelayed(view, mover, getRemoveDuration());
      } else {
        mover.run();
      }
    }
    // Next, change stuff, to run in parallel with move animations
    if (changesPending) {
      final ArrayList<ChangeInfo> changes = new ArrayList<>(pendingChanges);
      changesList.add(changes);
      pendingChanges.clear();
      Runnable changer = () -> {
        boolean removed = changesList.remove(changes);
        if (!removed) {
          // already canceled
          return;
        }
        for (ChangeInfo change : changes) {
          animateChangeImpl(change);
        }
        changes.clear();
      };
      if (removalsPending) {
        ViewHolder holder = changes.get(0).oldHolder;
        ViewCompat.postOnAnimationDelayed(holder.itemView, changer, getRemoveDuration());
      } else {
        changer.run();
      }
    }
    // Next, add stuff
    if (additionsPending) {
      final ArrayList<ViewHolder> additions = new ArrayList<>(pendingAdditions);
      additionsList.add(additions);
      pendingAdditions.clear();
      Runnable adder = () -> {
        boolean removed = additionsList.remove(additions);
        if (!removed) {
          // already canceled
          return;
        }
        for (ViewHolder holder : additions) {
          doAnimateAdd(holder);
        }
        additions.clear();
      };
      if (removalsPending || movesPending || changesPending) {
        long removeDuration = removalsPending ? getRemoveDuration() : 0;
        long moveDuration = movesPending ? getMoveDuration() : 0;
        long changeDuration = changesPending ? getChangeDuration() : 0;
        long totalDelay = removeDuration + Math.max(moveDuration, changeDuration);
        View view = additions.get(0).itemView;
        ViewCompat.postOnAnimationDelayed(view, adder, totalDelay);
      } else {
        adder.run();
      }
    }
  }

  protected void preAnimateRemoveImpl(final RecyclerView.ViewHolder holder) {
  }

  protected void preAnimateAddImpl(final RecyclerView.ViewHolder holder) {
  }

  protected abstract void animateRemoveImpl(final RecyclerView.ViewHolder holder);

  protected abstract void animateAddImpl(final RecyclerView.ViewHolder holder);

  private void preAnimateRemove(final RecyclerView.ViewHolder holder) {
    ViewHelper.clear(holder.itemView);

    if (holder instanceof AnimateViewHolder) {
      ((AnimateViewHolder) holder).preAnimateRemoveImpl(holder);
    } else {
      preAnimateRemoveImpl(holder);
    }
  }

  private void preAnimateAdd(final RecyclerView.ViewHolder holder) {
    ViewHelper.clear(holder.itemView);

    if (holder instanceof AnimateViewHolder) {
      ((AnimateViewHolder) holder).preAnimateAddImpl(holder);
    } else {
      preAnimateAddImpl(holder);
    }
  }

  private void doAnimateRemove(final RecyclerView.ViewHolder holder) {
    if (holder instanceof AnimateViewHolder) {
      ((AnimateViewHolder) holder).animateRemoveImpl(holder, new DefaultRemoveVpaListener(holder));
    } else {
      animateRemoveImpl(holder);
    }

    removeAnimations.add(holder);
  }

  private void doAnimateAdd(final RecyclerView.ViewHolder holder) {
    if (holder instanceof AnimateViewHolder) {
      ((AnimateViewHolder) holder).animateAddImpl(holder, new DefaultAddVpaListener(holder));
    } else {
      animateAddImpl(holder);
    }

    addAnimations.add(holder);
  }

  @Override public boolean animateRemove(final ViewHolder holder) {
    endAnimation(holder);
    preAnimateRemove(holder);
    pendingRemovals.add(holder);
    return true;
  }

  protected long getRemoveDelay(final RecyclerView.ViewHolder holder) {
    return Math.abs(holder.getOldPosition() * getRemoveDuration() / 4);
  }

  @Override public boolean animateAdd(final ViewHolder holder) {
    endAnimation(holder);
    preAnimateAdd(holder);
    pendingAdditions.add(holder);
    return true;
  }

  protected long getAddDelay(final RecyclerView.ViewHolder holder) {
    return Math.abs(holder.getAdapterPosition() * getAddDuration() / 4);
  }

  @Override
  public boolean animateMove(final ViewHolder holder, int fromX, int fromY, int toX, int toY) {
    final View view = holder.itemView;
    fromX += holder.itemView.getTranslationX();
    fromY += holder.itemView.getTranslationY();
    endAnimation(holder);
    int deltaX = toX - fromX;
    int deltaY = toY - fromY;
    if (deltaX == 0 && deltaY == 0) {
      dispatchMoveFinished(holder);
      return false;
    }
    if (deltaX != 0) {
      view.setTranslationX(-deltaX);
    }
    if (deltaY != 0) {
      view.setTranslationY(-deltaY);
    }
    pendingMoves.add(new MoveInfo(holder, fromX, fromY, toX, toY));
    return true;
  }

  private void animateMoveImpl(final ViewHolder holder, int fromX, int fromY, int toX, int toY) {
    final View view = holder.itemView;
    final int deltaX = toX - fromX;
    final int deltaY = toY - fromY;
    if (deltaX != 0) {
      ViewCompat.animate(view).translationX(0);
    }
    if (deltaY != 0) {
      ViewCompat.animate(view).translationY(0);
    }
    // TODO: make EndActions end listeners instead, since end actions aren't called when
    // vpas are canceled (and can't end them. why?)
    // need listener functionality in VPACompat for this. Ick.
    moveAnimations.add(holder);
    final ViewPropertyAnimatorCompat animation = ViewCompat.animate(view);
    animation.setDuration(getMoveDuration()).setListener(new VpaListenerAdapter() {
      @Override public void onAnimationStart(View view) {
        dispatchMoveStarting(holder);
      }

      @Override public void onAnimationCancel(View view) {
        if (deltaX != 0) {
          view.setTranslationX(0);
        }
        if (deltaY != 0) {
          view.setTranslationY(0);
        }
      }

      @Override public void onAnimationEnd(View view) {
        animation.setListener(null);
        dispatchMoveFinished(holder);
        moveAnimations.remove(holder);
        dispatchFinishedWhenDone();
      }
    }).start();
  }

  @Override
  public boolean animateChange(ViewHolder oldHolder, ViewHolder newHolder, int fromX, int fromY,
    int toX, int toY) {
    if (oldHolder == newHolder) {
      // Don't know how to run change animations when the same view holder is re-used.
      // run a move animation to handle position changes.
      return animateMove(oldHolder, fromX, fromY, toX, toY);
    }
    final float prevTranslationX = oldHolder.itemView.getTranslationX();
    final float prevTranslationY = oldHolder.itemView.getTranslationY();
    final float prevAlpha = oldHolder.itemView.getAlpha();
    endAnimation(oldHolder);
    int deltaX = (int) (toX - fromX - prevTranslationX);
    int deltaY = (int) (toY - fromY - prevTranslationY);
    // recover prev translation state after ending animation
    oldHolder.itemView.setTranslationX(prevTranslationX);
    oldHolder.itemView.setTranslationY(prevTranslationY);
    oldHolder.itemView.setAlpha(prevAlpha);
    if (newHolder != null && newHolder.itemView != null) {
      // carry over translation values
      endAnimation(newHolder);
      newHolder.itemView.setTranslationX(-deltaX);
      newHolder.itemView.setTranslationY(-deltaY);
      newHolder.itemView.setAlpha(0);
    }
    pendingChanges.add(new ChangeInfo(oldHolder, newHolder, fromX, fromY, toX, toY));
    return true;
  }

  private void animateChangeImpl(final ChangeInfo changeInfo) {
    final ViewHolder holder = changeInfo.oldHolder;
    final View view = holder == null ? null : holder.itemView;
    final ViewHolder newHolder = changeInfo.newHolder;
    final View newView = newHolder != null ? newHolder.itemView : null;
    if (view != null) {
      changeAnimations.add(changeInfo.oldHolder);
      final ViewPropertyAnimatorCompat oldViewAnim =
        ViewCompat.animate(view).setDuration(getChangeDuration());
      oldViewAnim.translationX(changeInfo.toX - changeInfo.fromX);
      oldViewAnim.translationY(changeInfo.toY - changeInfo.fromY);
      oldViewAnim.alpha(0).setListener(new VpaListenerAdapter() {
        @Override public void onAnimationStart(View view) {
          dispatchChangeStarting(changeInfo.oldHolder, true);
        }

        @Override public void onAnimationEnd(View view) {
          oldViewAnim.setListener(null);
          view.setAlpha(1);
          view.setTranslationX(0);
          view.setTranslationY(0);
          dispatchChangeFinished(changeInfo.oldHolder, true);
          changeAnimations.remove(changeInfo.oldHolder);
          dispatchFinishedWhenDone();
        }
      }).start();
    }
    if (newView != null) {
      changeAnimations.add(changeInfo.newHolder);
      final ViewPropertyAnimatorCompat newViewAnimation = ViewCompat.animate(newView);
      newViewAnimation.translationX(0).translationY(0).setDuration(getChangeDuration()).
        alpha(1).setListener(new VpaListenerAdapter() {
        @Override public void onAnimationStart(View view) {
          dispatchChangeStarting(changeInfo.newHolder, false);
        }

        @Override public void onAnimationEnd(View view) {
          newViewAnimation.setListener(null);
          newView.setAlpha(1);
          newView.setTranslationX(0);
          newView.setTranslationY(0);
          dispatchChangeFinished(changeInfo.newHolder, false);
          changeAnimations.remove(changeInfo.newHolder);
          dispatchFinishedWhenDone();
        }
      }).start();
    }
  }

  private void endChangeAnimation(List<ChangeInfo> infoList, ViewHolder item) {
    for (int i = infoList.size() - 1; i >= 0; i--) {
      ChangeInfo changeInfo = infoList.get(i);
      if (endChangeAnimationIfNecessary(changeInfo, item)) {
        if (changeInfo.oldHolder == null && changeInfo.newHolder == null) {
          infoList.remove(changeInfo);
        }
      }
    }
  }

  private void endChangeAnimationIfNecessary(ChangeInfo changeInfo) {
    if (changeInfo.oldHolder != null) {
      endChangeAnimationIfNecessary(changeInfo, changeInfo.oldHolder);
    }
    if (changeInfo.newHolder != null) {
      endChangeAnimationIfNecessary(changeInfo, changeInfo.newHolder);
    }
  }

  private boolean endChangeAnimationIfNecessary(ChangeInfo changeInfo, ViewHolder item) {
    boolean oldItem = false;
    if (changeInfo.newHolder == item) {
      changeInfo.newHolder = null;
    } else if (changeInfo.oldHolder == item) {
      changeInfo.oldHolder = null;
      oldItem = true;
    } else {
      return false;
    }
    item.itemView.setAlpha(1);
    item.itemView.setTranslationX(0);
    item.itemView.setTranslationY(0);
    dispatchChangeFinished(item, oldItem);
    return true;
  }

  @Override public void endAnimation(ViewHolder item) {
    final View view = item.itemView;
    // this will trigger end callback which should set properties to their target values.
    ViewCompat.animate(view).cancel();
    // TODO if some other animations are chained to end, how do we cancel them as well?
    for (int i = pendingMoves.size() - 1; i >= 0; i--) {
      MoveInfo moveInfo = pendingMoves.get(i);
      if (moveInfo.holder == item) {
        view.setTranslationY(0);
        view.setTranslationX(0);
        dispatchMoveFinished(item);
        pendingMoves.remove(i);
      }
    }
    endChangeAnimation(pendingChanges, item);
    if (pendingRemovals.remove(item)) {
      ViewHelper.clear(item.itemView);
      dispatchRemoveFinished(item);
    }
    if (pendingAdditions.remove(item)) {
      ViewHelper.clear(item.itemView);
      dispatchAddFinished(item);
    }

    for (int i = changesList.size() - 1; i >= 0; i--) {
      ArrayList<ChangeInfo> changes = changesList.get(i);
      endChangeAnimation(changes, item);
      if (changes.isEmpty()) {
        changesList.remove(i);
      }
    }
    for (int i = movesList.size() - 1; i >= 0; i--) {
      ArrayList<MoveInfo> moves = movesList.get(i);
      for (int j = moves.size() - 1; j >= 0; j--) {
        MoveInfo moveInfo = moves.get(j);
        if (moveInfo.holder == item) {
          view.setTranslationY(0);
          view.setTranslationX(0);
          dispatchMoveFinished(item);
          moves.remove(j);
          if (moves.isEmpty()) {
            movesList.remove(i);
          }
          break;
        }
      }
    }
    for (int i = additionsList.size() - 1; i >= 0; i--) {
      ArrayList<ViewHolder> additions = additionsList.get(i);
      if (additions.remove(item)) {
        ViewHelper.clear(item.itemView);
        dispatchAddFinished(item);
        if (additions.isEmpty()) {
          additionsList.remove(i);
        }
      }
    }

    // animations should be ended by the cancel above.
    if (removeAnimations.remove(item) && DEBUG) {
      throw new IllegalStateException(
        "after animation is cancelled, item should not be in " + "mRemoveAnimations list");
    }

    if (addAnimations.remove(item) && DEBUG) {
      throw new IllegalStateException(
        "after animation is cancelled, item should not be in " + "mAddAnimations list");
    }

    if (changeAnimations.remove(item) && DEBUG) {
      throw new IllegalStateException(
        "after animation is cancelled, item should not be in " + "mChangeAnimations list");
    }

    if (moveAnimations.remove(item) && DEBUG) {
      throw new IllegalStateException(
        "after animation is cancelled, item should not be in " + "mMoveAnimations list");
    }
    dispatchFinishedWhenDone();
  }

  @Override public boolean isRunning() {
    return (!pendingAdditions.isEmpty() || !pendingChanges.isEmpty() || !pendingMoves.isEmpty()
      || !pendingRemovals.isEmpty() || !moveAnimations.isEmpty() || !removeAnimations.isEmpty()
      || !addAnimations.isEmpty() || !changeAnimations.isEmpty() || !movesList.isEmpty()
      || !additionsList.isEmpty() || !changesList.isEmpty());
  }

  /**
   * Check the state of currently pending and running animations. If there are none
   * pending/running, call #dispatchAnimationsFinished() to notify any
   * listeners.
   */
  private void dispatchFinishedWhenDone() {
    if (!isRunning()) {
      dispatchAnimationsFinished();
    }
  }

  @Override public void endAnimations() {
    int count = pendingMoves.size();
    for (int i = count - 1; i >= 0; i--) {
      MoveInfo item = pendingMoves.get(i);
      View view = item.holder.itemView;
      view.setTranslationY(0);
      view.setTranslationX(0);
      dispatchMoveFinished(item.holder);
      pendingMoves.remove(i);
    }
    count = pendingRemovals.size();
    for (int i = count - 1; i >= 0; i--) {
      ViewHolder item = pendingRemovals.get(i);
      dispatchRemoveFinished(item);
      pendingRemovals.remove(i);
    }
    count = pendingAdditions.size();
    for (int i = count - 1; i >= 0; i--) {
      ViewHolder item = pendingAdditions.get(i);
      ViewHelper.clear(item.itemView);
      dispatchAddFinished(item);
      pendingAdditions.remove(i);
    }
    count = pendingChanges.size();
    for (int i = count - 1; i >= 0; i--) {
      endChangeAnimationIfNecessary(pendingChanges.get(i));
    }
    pendingChanges.clear();
    if (!isRunning()) {
      return;
    }

    int listCount = movesList.size();
    for (int i = listCount - 1; i >= 0; i--) {
      ArrayList<MoveInfo> moves = movesList.get(i);
      count = moves.size();
      for (int j = count - 1; j >= 0; j--) {
        MoveInfo moveInfo = moves.get(j);
        ViewHolder item = moveInfo.holder;
        View view = item.itemView;
        view.setTranslationY(0);
        view.setTranslationX(0);
        dispatchMoveFinished(moveInfo.holder);
        moves.remove(j);
        if (moves.isEmpty()) {
          movesList.remove(moves);
        }
      }
    }
    listCount = additionsList.size();
    for (int i = listCount - 1; i >= 0; i--) {
      ArrayList<ViewHolder> additions = additionsList.get(i);
      count = additions.size();
      for (int j = count - 1; j >= 0; j--) {
        ViewHolder item = additions.get(j);
        View view = item.itemView;
        view.setAlpha(1);
        dispatchAddFinished(item);
        //this check prevent exception when removal already happened during finishing animation
        if (j < additions.size()) {
          additions.remove(j);
        }
        if (additions.isEmpty()) {
          additionsList.remove(additions);
        }
      }
    }
    listCount = changesList.size();
    for (int i = listCount - 1; i >= 0; i--) {
      ArrayList<ChangeInfo> changes = changesList.get(i);
      count = changes.size();
      for (int j = count - 1; j >= 0; j--) {
        endChangeAnimationIfNecessary(changes.get(j));
        if (changes.isEmpty()) {
          changesList.remove(changes);
        }
      }
    }

    cancelAll(removeAnimations);
    cancelAll(moveAnimations);
    cancelAll(addAnimations);
    cancelAll(changeAnimations);

    dispatchAnimationsFinished();
  }

  void cancelAll(List<ViewHolder> viewHolders) {
    for (int i = viewHolders.size() - 1; i >= 0; i--) {
      ViewCompat.animate(viewHolders.get(i).itemView).cancel();
    }
  }

  private static class VpaListenerAdapter implements ViewPropertyAnimatorListener {

    @Override public void onAnimationStart(View view) {
    }

    @Override public void onAnimationEnd(View view) {
    }

    @Override public void onAnimationCancel(View view) {
    }
  }

  protected class DefaultAddVpaListener extends VpaListenerAdapter {

    RecyclerView.ViewHolder viewHolder;

    public DefaultAddVpaListener(final RecyclerView.ViewHolder holder) {
      viewHolder = holder;
    }

    @Override public void onAnimationStart(View view) {
      dispatchAddStarting(viewHolder);
    }

    @Override public void onAnimationCancel(View view) {
      ViewHelper.clear(view);
    }

    @Override public void onAnimationEnd(View view) {
      ViewHelper.clear(view);
      dispatchAddFinished(viewHolder);
      addAnimations.remove(viewHolder);
      dispatchFinishedWhenDone();
    }
  }

  protected class DefaultRemoveVpaListener extends VpaListenerAdapter {

    RecyclerView.ViewHolder viewHolder;

    public DefaultRemoveVpaListener(final RecyclerView.ViewHolder holder) {
      viewHolder = holder;
    }

    @Override public void onAnimationStart(View view) {
      dispatchRemoveStarting(viewHolder);
    }

    @Override public void onAnimationCancel(View view) {
      ViewHelper.clear(view);
    }

    @Override public void onAnimationEnd(View view) {
      ViewHelper.clear(view);
      dispatchRemoveFinished(viewHolder);
      removeAnimations.remove(viewHolder);
      dispatchFinishedWhenDone();
    }
  }
}
