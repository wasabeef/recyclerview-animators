package jp.wasabeef.recyclerview.animators;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import jp.wasabeef.recyclerview.animators.internal.ChangeDispatcher;

public class AnimateChange {
  private ChangeDispatcher mDispatcher;

  public void setAnimatorChangeDispatcher(ChangeDispatcher animatorChangeDispatcher) {
    this.mDispatcher = animatorChangeDispatcher;
  }

  public void animateChange(final RecyclerView.ViewHolder oldHolder, final RecyclerView.ViewHolder newHolder, int fromX, int fromY, int toX, int toY) {
    final View view = oldHolder.itemView;
    final View newView = newHolder.itemView;
    long oldAnimDuration = (long) (0.5f * mDispatcher.getChangeDuration());
    long newAnimDuration = mDispatcher.getChangeDuration();

    if (view != null) {
      final ViewPropertyAnimatorCompat oldViewAnim = ViewCompat.animate(view);
      oldViewAnim.translationX(toX - fromX);
      oldViewAnim.translationY(toY - fromY);

      oldViewAnim.setDuration(oldAnimDuration);
      oldViewAnim.alpha(0).setListener(new ViewPropertyAnimatorListener() {
        @Override
        public void onAnimationStart(View view) {
          mDispatcher.dispatchChangeStarting(oldHolder, true);
        }

        @Override
        public void onAnimationEnd(View view) {
          oldViewAnim.setListener(null);
          ViewCompat.setAlpha(view, 1);
          ViewCompat.setTranslationX(view, 0);
          ViewCompat.setTranslationY(view, 0);
          mDispatcher.dispatchChangeFinished(oldHolder, true);
        }

        @Override
        public void onAnimationCancel(View view) { }
      }).start();
    }

    if (newView != null) {
      final ViewPropertyAnimatorCompat newViewAnimation = ViewCompat.animate(newView);
      newViewAnimation.translationX(0).translationY(0);

      newViewAnimation.setDuration(newAnimDuration);
      newViewAnimation.alpha(1).setListener(new ViewPropertyAnimatorListener() {
        @Override public void onAnimationStart(View view) {
          mDispatcher.dispatchChangeStarting(newHolder, false);
        }

        @Override public void onAnimationEnd(View view) {
          newViewAnimation.setListener(null);
          ViewCompat.setAlpha(newView, 1);
          ViewCompat.setTranslationX(newView, 0);
          ViewCompat.setTranslationY(newView, 0);
          mDispatcher.dispatchChangeFinished(newHolder, false);
        }

        @Override
        public void onAnimationCancel(View view) { }
      }).start();
    }
  }

}
