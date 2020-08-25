package jp.wasabeef.recyclerview.animators.holder

import androidx.core.view.ViewPropertyAnimatorListener
import androidx.recyclerview.widget.RecyclerView

interface AnimateViewHolder {
  fun preAnimateAddImpl(holder: RecyclerView.ViewHolder)
  fun preAnimateRemoveImpl(holder: RecyclerView.ViewHolder)
  fun animateAddImpl(holder: RecyclerView.ViewHolder, listener: ViewPropertyAnimatorListener)
  fun animateRemoveImpl(
    holder: RecyclerView.ViewHolder,
    listener: ViewPropertyAnimatorListener
  )
}
