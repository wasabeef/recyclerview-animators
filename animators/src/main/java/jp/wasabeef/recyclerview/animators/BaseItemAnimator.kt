package jp.wasabeef.recyclerview.animators

import android.animation.Animator
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import jp.wasabeef.recyclerview.animators.holder.AnimateViewHolder
import jp.wasabeef.recyclerview.internal.ViewHelper.clear
import java.util.ArrayList
import kotlin.math.abs

/*
* Copyright (C) 2020 Daichi Furiya / Wasabeef
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
abstract class BaseItemAnimator : SimpleItemAnimator() {

  private val pendingRemovals = ArrayList<RecyclerView.ViewHolder>()
  private val pendingAdditions = ArrayList<RecyclerView.ViewHolder>()
  private val pendingMoves = ArrayList<MoveInfo>()
  private val pendingChanges = ArrayList<ChangeInfo>()
  private val additionsList = ArrayList<ArrayList<RecyclerView.ViewHolder>>()
  private val movesList = ArrayList<ArrayList<MoveInfo>>()
  private val changesList = ArrayList<ArrayList<ChangeInfo>>()
  protected var addAnimations = ArrayList<RecyclerView.ViewHolder>()
  private val moveAnimations = ArrayList<RecyclerView.ViewHolder>()
  protected var removeAnimations = ArrayList<RecyclerView.ViewHolder>()
  private val changeAnimations = ArrayList<RecyclerView.ViewHolder>()
  protected var interpolator: Interpolator = DecelerateInterpolator()

  companion object {
    private const val DEBUG = false
  }

  init {
    supportsChangeAnimations = false
  }

  private class MoveInfo(
    var holder: RecyclerView.ViewHolder,
    var fromX: Int,
    var fromY: Int,
    var toX: Int,
    var toY: Int
  )

  private class ChangeInfo private constructor(
    oldHolder: RecyclerView.ViewHolder,
    newHolder: RecyclerView.ViewHolder
  ) {
    var oldHolder: RecyclerView.ViewHolder? = oldHolder
    var newHolder: RecyclerView.ViewHolder? = newHolder
    var fromX = 0
    var fromY = 0
    var toX = 0
    var toY = 0

    constructor(
      oldHolder: RecyclerView.ViewHolder,
      newHolder: RecyclerView.ViewHolder,
      fromX: Int,
      fromY: Int,
      toX: Int,
      toY: Int
    ) : this(oldHolder, newHolder) {
      this.fromX = fromX
      this.fromY = fromY
      this.toX = toX
      this.toY = toY
    }

    override fun toString(): String {
      return ("ChangeInfo{" + "oldHolder=" + oldHolder + ", newHolder=" + newHolder + ", fromX="
        + fromX + ", fromY=" + fromY + ", toX=" + toX + ", toY=" + toY + '}')
    }
  }

  override fun runPendingAnimations() {
    val removalsPending = pendingRemovals.isNotEmpty()
    val movesPending = pendingMoves.isNotEmpty()
    val changesPending = pendingChanges.isNotEmpty()
    val additionsPending = pendingAdditions.isNotEmpty()
    if (!removalsPending && !movesPending && !additionsPending && !changesPending) {
      // nothing to animate
      return
    }
    // First, remove stuff
    for (holder in pendingRemovals) {
      doAnimateRemove(holder)
    }
    pendingRemovals.clear()
    // Next, move stuff
    if (movesPending) {
      val moves = ArrayList(pendingMoves)
      movesList.add(moves)
      pendingMoves.clear()
      val mover = Runnable {
        val removed = movesList.remove(moves)
        if (!removed) {
          // already canceled
          return@Runnable
        }
        for (moveInfo in moves) {
          animateMoveImpl(
            moveInfo.holder, moveInfo.fromX, moveInfo.fromY, moveInfo.toX,
            moveInfo.toY
          )
        }
        moves.clear()
      }
      if (removalsPending) {
        val view = moves[0].holder.itemView
        view.postOnAnimationDelayed(mover, removeDuration)
      } else {
        mover.run()
      }
    }
    // Next, change stuff, to run in parallel with move animations
    if (changesPending) {
      val changes = ArrayList(pendingChanges)
      changesList.add(changes)
      pendingChanges.clear()
      val changer = Runnable {
        val removed = changesList.remove(changes)
        if (!removed) {
          // already canceled
          return@Runnable
        }
        for (change in changes) {
          animateChangeImpl(change)
        }
        changes.clear()
      }
      if (removalsPending) {
        val holder = changes[0].oldHolder
        holder!!.itemView.postOnAnimationDelayed(changer, removeDuration)
      } else {
        changer.run()
      }
    }
    // Next, add stuff
    if (additionsPending) {
      val additions = ArrayList(pendingAdditions)
      additionsList.add(additions)
      pendingAdditions.clear()
      val adder = Runnable {
        val removed = additionsList.remove(additions)
        if (!removed) {
          // already canceled
          return@Runnable
        }
        for (holder in additions) {
          doAnimateAdd(holder)
        }
        additions.clear()
      }
      if (removalsPending || movesPending || changesPending) {
        val removeDuration = if (removalsPending) removeDuration else 0
        val moveDuration = if (movesPending) moveDuration else 0
        val changeDuration = if (changesPending) changeDuration else 0
        val totalDelay = removeDuration + moveDuration.coerceAtLeast(changeDuration)
        val view = additions[0].itemView
        view.postOnAnimationDelayed(adder, totalDelay)
      } else {
        adder.run()
      }
    }
  }

  protected open fun preAnimateRemoveImpl(holder: RecyclerView.ViewHolder) {}

  protected open fun preAnimateAddImpl(holder: RecyclerView.ViewHolder) {}

  protected abstract fun animateRemoveImpl(holder: RecyclerView.ViewHolder)

  protected abstract fun animateAddImpl(holder: RecyclerView.ViewHolder)

  private fun preAnimateRemove(holder: RecyclerView.ViewHolder) {
    clear(holder.itemView)
    if (holder is AnimateViewHolder) {
      holder.preAnimateRemoveImpl(holder)
    } else {
      preAnimateRemoveImpl(holder)
    }
  }

  private fun preAnimateAdd(holder: RecyclerView.ViewHolder) {
    clear(holder.itemView)
    if (holder is AnimateViewHolder) {
      holder.preAnimateAddImpl(holder)
    } else {
      preAnimateAddImpl(holder)
    }
  }

  private fun doAnimateRemove(holder: RecyclerView.ViewHolder) {
    if (holder is AnimateViewHolder) {
      holder.animateRemoveImpl(holder, DefaultRemoveAnimatorListener(holder))
    } else {
      animateRemoveImpl(holder)
    }
    removeAnimations.add(holder)
  }

  private fun doAnimateAdd(holder: RecyclerView.ViewHolder) {
    if (holder is AnimateViewHolder) {
      holder.animateAddImpl(holder, DefaultAddAnimatorListener(holder))
    } else {
      animateAddImpl(holder)
    }
    addAnimations.add(holder)
  }

  override fun animateRemove(holder: RecyclerView.ViewHolder): Boolean {
    endAnimation(holder)
    preAnimateRemove(holder)
    pendingRemovals.add(holder)
    return true
  }

  protected fun getRemoveDelay(holder: RecyclerView.ViewHolder): Long {
    return abs(holder.oldPosition * removeDuration / 4)
  }

  override fun animateAdd(holder: RecyclerView.ViewHolder): Boolean {
    endAnimation(holder)
    preAnimateAdd(holder)
    pendingAdditions.add(holder)
    return true
  }

  protected fun getAddDelay(holder: RecyclerView.ViewHolder): Long {
    return abs(holder.adapterPosition * addDuration / 4)
  }

  override fun animateMove(
    holder: RecyclerView.ViewHolder,
    fromX: Int,
    fromY: Int,
    toX: Int,
    toY: Int
  ): Boolean {
    var fX = fromX
    var fY = fromY
    val view = holder.itemView
    fX += holder.itemView.translationX.toInt()
    fY += holder.itemView.translationY.toInt()
    endAnimation(holder)
    val deltaX = toX - fX
    val deltaY = toY - fY
    if (deltaX == 0 && deltaY == 0) {
      dispatchMoveFinished(holder)
      return false
    }
    if (deltaX != 0) {
      view.translationX = -deltaX.toFloat()
    }
    if (deltaY != 0) {
      view.translationY = -deltaY.toFloat()
    }
    pendingMoves.add(MoveInfo(holder, fX, fY, toX, toY))
    return true
  }

  private fun animateMoveImpl(
    holder: RecyclerView.ViewHolder,
    fromX: Int,
    fromY: Int,
    toX: Int,
    toY: Int
  ) {
    val view = holder.itemView
    val deltaX = toX - fromX
    val deltaY = toY - fromY
    if (deltaX != 0) {
      view.animate().translationX(0f)
    }
    if (deltaY != 0) {
      view.animate().translationY(0f)
    }
    // TODO: make EndActions end listeners instead, since end actions aren't called when
    // vpas are canceled (and can't end them. why?)
    // need listener functionality in VPACompat for this. Ick.
    moveAnimations.add(holder)
    val animation = view.animate()
    animation.setDuration(moveDuration).setListener(object : AnimatorListenerAdapter() {
      override fun onAnimationStart(animator: Animator) {
        dispatchMoveStarting(holder)
      }

      override fun onAnimationCancel(animator: Animator) {
        if (deltaX != 0) {
          view.translationX = 0f
        }
        if (deltaY != 0) {
          view.translationY = 0f
        }
      }

      override fun onAnimationEnd(animator: Animator) {
        animation.setListener(null)
        dispatchMoveFinished(holder)
        moveAnimations.remove(holder)
        dispatchFinishedWhenDone()
      }
    }).start()
  }

  override fun animateChange(
    oldHolder: RecyclerView.ViewHolder, newHolder: RecyclerView.ViewHolder, fromX: Int, fromY: Int,
    toX: Int, toY: Int
  ): Boolean {
    if (oldHolder === newHolder) {
      // Don't know how to run change animations when the same view holder is re-used.
      // run a move animation to handle position changes.
      return animateMove(oldHolder, fromX, fromY, toX, toY)
    }
    val prevTranslationX = oldHolder.itemView.translationX
    val prevTranslationY = oldHolder.itemView.translationY
    val prevAlpha = oldHolder.itemView.alpha
    endAnimation(oldHolder)
    val deltaX = (toX - fromX - prevTranslationX).toInt()
    val deltaY = (toY - fromY - prevTranslationY).toInt()
    // recover prev translation state after ending animation
    oldHolder.itemView.translationX = prevTranslationX
    oldHolder.itemView.translationY = prevTranslationY
    oldHolder.itemView.alpha = prevAlpha
    // carry over translation values
    endAnimation(newHolder)
    newHolder.itemView.translationX = -deltaX.toFloat()
    newHolder.itemView.translationY = -deltaY.toFloat()
    newHolder.itemView.alpha = 0f
    pendingChanges.add(ChangeInfo(oldHolder, newHolder, fromX, fromY, toX, toY))
    return true
  }

  private fun animateChangeImpl(changeInfo: ChangeInfo) {
    val holder = changeInfo.oldHolder
    val view = holder?.itemView
    val newHolder = changeInfo.newHolder
    val newView = newHolder?.itemView
    if (view != null) {
      if (changeInfo.oldHolder != null) changeAnimations.add(changeInfo.oldHolder!!)
      val oldViewAnim = view.animate().setDuration(
        changeDuration
      )
      oldViewAnim.translationX(changeInfo.toX - changeInfo.fromX.toFloat())
      oldViewAnim.translationY(changeInfo.toY - changeInfo.fromY.toFloat())
      oldViewAnim.alpha(0f).setListener(object : AnimatorListenerAdapter() {
        override fun onAnimationStart(animator: Animator) {
          dispatchChangeStarting(changeInfo.oldHolder, true)
        }

        override fun onAnimationEnd(animator: Animator) {
          oldViewAnim.setListener(null)
          view.alpha = 1f
          view.translationX = 0f
          view.translationY = 0f
          dispatchChangeFinished(changeInfo.oldHolder, true)
          if (changeInfo.oldHolder != null) changeAnimations.remove(changeInfo.oldHolder!!)
          dispatchFinishedWhenDone()
        }
      }).start()
    }
    if (newView != null) {
      if (changeInfo.newHolder != null) changeAnimations.add(changeInfo.newHolder!!)
      val newViewAnimation = newView.animate()
      newViewAnimation.translationX(0f).translationY(0f).setDuration(changeDuration).alpha(1f)
        .setListener(object : AnimatorListenerAdapter() {
          override fun onAnimationStart(animator: Animator) {
            dispatchChangeStarting(changeInfo.newHolder, false)
          }

          override fun onAnimationEnd(animator: Animator) {
            newViewAnimation.setListener(null)
            newView.alpha = 1f
            newView.translationX = 0f
            newView.translationY = 0f
            dispatchChangeFinished(changeInfo.newHolder, false)
            if (changeInfo.newHolder != null) changeAnimations.remove(changeInfo.newHolder!!)
            dispatchFinishedWhenDone()
          }
        }).start()
    }
  }

  private fun endChangeAnimation(infoList: MutableList<ChangeInfo>, item: RecyclerView.ViewHolder) {
    for (i in infoList.indices.reversed()) {
      val changeInfo = infoList[i]
      if (endChangeAnimationIfNecessary(changeInfo, item)) {
        if (changeInfo.oldHolder == null && changeInfo.newHolder == null) {
          infoList.remove(changeInfo)
        }
      }
    }
  }

  private fun endChangeAnimationIfNecessary(changeInfo: ChangeInfo) {
    if (changeInfo.oldHolder != null) {
      endChangeAnimationIfNecessary(changeInfo, changeInfo.oldHolder)
    }
    if (changeInfo.newHolder != null) {
      endChangeAnimationIfNecessary(changeInfo, changeInfo.newHolder)
    }
  }

  private fun endChangeAnimationIfNecessary(
    changeInfo: ChangeInfo,
    item: RecyclerView.ViewHolder?
  ): Boolean {
    var oldItem = false
    when {
      changeInfo.newHolder === item -> {
        changeInfo.newHolder = null
      }
      changeInfo.oldHolder === item -> {
        changeInfo.oldHolder = null
        oldItem = true
      }
      else -> {
        return false
      }
    }
    item!!.itemView.alpha = 1f
    item.itemView.translationX = 0f
    item.itemView.translationY = 0f
    dispatchChangeFinished(item, oldItem)
    return true
  }

  override fun endAnimation(item: RecyclerView.ViewHolder) {
    val view = item.itemView
    // this will trigger end callback which should set properties to their target values.
    view.animate().cancel()
    // TODO if some other animations are chained to end, how do we cancel them as well?
    for (i in pendingMoves.indices.reversed()) {
      val moveInfo = pendingMoves[i]
      if (moveInfo.holder === item) {
        view.translationY = 0f
        view.translationX = 0f
        dispatchMoveFinished(item)
        pendingMoves.removeAt(i)
      }
    }
    endChangeAnimation(pendingChanges, item)
    if (pendingRemovals.remove(item)) {
      clear(item.itemView)
      dispatchRemoveFinished(item)
    }
    if (pendingAdditions.remove(item)) {
      clear(item.itemView)
      dispatchAddFinished(item)
    }
    for (i in changesList.indices.reversed()) {
      val changes = changesList[i]
      endChangeAnimation(changes, item)
      if (changes.isEmpty()) {
        changesList.removeAt(i)
      }
    }
    for (i in movesList.indices.reversed()) {
      val moves = movesList[i]
      for (j in moves.indices.reversed()) {
        val moveInfo = moves[j]
        if (moveInfo.holder === item) {
          view.translationY = 0f
          view.translationX = 0f
          dispatchMoveFinished(item)
          moves.removeAt(j)
          if (moves.isEmpty()) {
            movesList.removeAt(i)
          }
          break
        }
      }
    }
    for (i in additionsList.indices.reversed()) {
      val additions = additionsList[i]
      if (additions.remove(item)) {
        clear(item.itemView)
        dispatchAddFinished(item)
        if (additions.isEmpty()) {
          additionsList.removeAt(i)
        }
      }
    }

    // animations should be ended by the cancel above.
    check(!(removeAnimations.remove(item) && DEBUG)) { "after animation is cancelled, item should not be in " + "mRemoveAnimations list" }
    check(!(addAnimations.remove(item) && DEBUG)) { "after animation is cancelled, item should not be in " + "mAddAnimations list" }
    check(!(changeAnimations.remove(item) && DEBUG)) { "after animation is cancelled, item should not be in " + "mChangeAnimations list" }
    check(!(moveAnimations.remove(item) && DEBUG)) { "after animation is cancelled, item should not be in " + "mMoveAnimations list" }
    dispatchFinishedWhenDone()
  }

  override fun isRunning(): Boolean {
    return (pendingAdditions.isNotEmpty() || pendingChanges.isNotEmpty() || pendingMoves.isNotEmpty()
      || pendingRemovals.isNotEmpty() || moveAnimations.isNotEmpty() || removeAnimations.isNotEmpty()
      || addAnimations.isNotEmpty() || changeAnimations.isNotEmpty() || movesList.isNotEmpty()
      || additionsList.isNotEmpty() || changesList.isNotEmpty())
  }

  /**
   * Check the state of currently pending and running animations. If there are none
   * pending/running, call #dispatchAnimationsFinished() to notify any
   * listeners.
   */
  private fun dispatchFinishedWhenDone() {
    if (!isRunning) {
      dispatchAnimationsFinished()
    }
  }

  override fun endAnimations() {
    var count = pendingMoves.size
    for (i in count - 1 downTo 0) {
      val item = pendingMoves[i]
      val view = item.holder.itemView
      view.translationY = 0f
      view.translationX = 0f
      dispatchMoveFinished(item.holder)
      pendingMoves.removeAt(i)
    }
    count = pendingRemovals.size
    for (i in count - 1 downTo 0) {
      val item = pendingRemovals[i]
      dispatchRemoveFinished(item)
      pendingRemovals.removeAt(i)
    }
    count = pendingAdditions.size
    for (i in count - 1 downTo 0) {
      val item = pendingAdditions[i]
      clear(item.itemView)
      dispatchAddFinished(item)
      pendingAdditions.removeAt(i)
    }
    count = pendingChanges.size
    for (i in count - 1 downTo 0) {
      endChangeAnimationIfNecessary(pendingChanges[i])
    }
    pendingChanges.clear()
    if (!isRunning) {
      return
    }
    var listCount = movesList.size
    for (i in listCount - 1 downTo 0) {
      val moves = movesList[i]
      count = moves.size
      for (j in count - 1 downTo 0) {
        val moveInfo = moves[j]
        val item = moveInfo.holder
        val view = item.itemView
        view.translationY = 0f
        view.translationX = 0f
        dispatchMoveFinished(moveInfo.holder)
        moves.removeAt(j)
        if (moves.isEmpty()) {
          movesList.remove(moves)
        }
      }
    }
    listCount = additionsList.size
    for (i in listCount - 1 downTo 0) {
      val additions = additionsList[i]
      count = additions.size
      for (j in count - 1 downTo 0) {
        val item = additions[j]
        val view = item.itemView
        view.alpha = 1f
        dispatchAddFinished(item)
        //this check prevent exception when removal already happened during finishing animation
        if (j < additions.size) {
          additions.removeAt(j)
        }
        if (additions.isEmpty()) {
          additionsList.remove(additions)
        }
      }
    }
    listCount = changesList.size
    for (i in listCount - 1 downTo 0) {
      val changes = changesList[i]
      count = changes.size
      for (j in count - 1 downTo 0) {
        endChangeAnimationIfNecessary(changes[j])
        if (changes.isEmpty()) {
          changesList.remove(changes)
        }
      }
    }
    cancelAll(removeAnimations)
    cancelAll(moveAnimations)
    cancelAll(addAnimations)
    cancelAll(changeAnimations)
    dispatchAnimationsFinished()
  }

  private fun cancelAll(viewHolders: List<RecyclerView.ViewHolder>) {
    for (i in viewHolders.indices.reversed()) {
      viewHolders[i].itemView.animate().cancel()
    }
  }

  open class AnimatorListenerAdapter : Animator.AnimatorListener {
    override fun onAnimationStart(animator: Animator) {}
    override fun onAnimationEnd(animator: Animator) {}
    override fun onAnimationCancel(animator: Animator) {}
    override fun onAnimationRepeat(animator: Animator) {}
  }

  inner class DefaultAddAnimatorListener(var viewHolder: RecyclerView.ViewHolder) :
    AnimatorListenerAdapter() {
    override fun onAnimationStart(animator: Animator) {
      dispatchAddStarting(viewHolder)
    }

    override fun onAnimationCancel(animator: Animator) {
      clear(viewHolder.itemView)
    }

    override fun onAnimationEnd(animator: Animator) {
      clear(viewHolder.itemView)
      dispatchAddFinished(viewHolder)
      addAnimations.remove(viewHolder)
      dispatchFinishedWhenDone()
    }
  }

  protected inner class DefaultRemoveAnimatorListener(var viewHolder: RecyclerView.ViewHolder) :
    AnimatorListenerAdapter() {
    override fun onAnimationStart(animator: Animator) {
      dispatchRemoveStarting(viewHolder)
    }

    override fun onAnimationCancel(animator: Animator) {
      clear(viewHolder.itemView)
    }

    override fun onAnimationEnd(animator: Animator) {
      clear(viewHolder.itemView)
      dispatchRemoveFinished(viewHolder)
      removeAnimations.remove(viewHolder)
      dispatchFinishedWhenDone()
    }
  }
}
