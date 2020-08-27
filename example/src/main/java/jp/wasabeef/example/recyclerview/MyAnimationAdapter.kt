package jp.wasabeef.example.recyclerview

import androidx.recyclerview.widget.RecyclerView
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter

class MyAnimatorAdapter constructor(
  adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>,
  from: Float = 0.5f
) : AlphaInAnimationAdapter(adapter, from) {
}
