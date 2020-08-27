package jp.wasabeef.recyclerview.internal

import android.view.View

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
object ViewHelper {
  @JvmStatic
  fun clear(v: View) {
    v.apply {
      alpha = 1f
      scaleY = 1f
      scaleX = 1f
      translationY = 0f
      translationX = 0f
      rotation = 0f
      rotationY = 0f
      rotationX = 0f
      pivotY = v.measuredHeight / 2f
      pivotX = v.measuredWidth / 2f
      animate().setInterpolator(null).startDelay = 0
    }
  }
}
