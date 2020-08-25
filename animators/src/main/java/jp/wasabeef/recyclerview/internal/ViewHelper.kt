package jp.wasabeef.recyclerview.internal

import android.view.View
import androidx.core.view.ViewCompat

/**
 * Copyright (C) 2020 Wasabeef
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
object ViewHelper {
  @JvmStatic
  fun clear(v: View) {
    v.alpha = 1f
    v.scaleY = 1f
    v.scaleX = 1f
    v.translationY = 0f
    v.translationX = 0f
    v.rotation = 0f
    v.rotationY = 0f
    v.rotationX = 0f
    v.pivotY = v.measuredHeight / 2f
    v.pivotX = v.measuredWidth / 2f
    ViewCompat.animate(v).setInterpolator(null).startDelay = 0
  }
}
