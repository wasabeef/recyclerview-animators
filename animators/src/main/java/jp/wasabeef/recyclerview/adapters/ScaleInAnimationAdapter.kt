package jp.wasabeef.recyclerview.adapters

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Copyright (C) 2020 Daichi Furiya / Wasabeef
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
open class ScaleInAnimationAdapter @JvmOverloads constructor(
  adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>,
  private val from: Float = DEFAULT_SCALE_FROM
) : AnimationAdapter(adapter) {

  override fun getAnimators(view: View): Array<Animator> {
    val scaleX = ObjectAnimator.ofFloat(view, "scaleX", from, 1f)
    val scaleY = ObjectAnimator.ofFloat(view, "scaleY", from, 1f)
    return arrayOf(scaleX, scaleY)
  }

  companion object {
    private const val DEFAULT_SCALE_FROM = .5f
  }
}
