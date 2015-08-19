package jp.wasabeef.recyclerview.animators.internal;

import android.support.v4.view.ViewCompat;
import android.view.View;

/**
 * Copyright (C) 2015 Wasabeef
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

public final class ViewHelper {

  public static void clear(View v) {
    ViewCompat.setAlpha(v, 1);
    ViewCompat.setScaleY(v, 1);
    ViewCompat.setScaleX(v, 1);
    ViewCompat.setTranslationY(v, 0);
    ViewCompat.setTranslationX(v, 0);
    ViewCompat.setRotation(v, 0);
    ViewCompat.setRotationY(v, 0);
    ViewCompat.setRotationX(v, 0);
    // @TODO https://code.google.com/p/android/issues/detail?id=80863
    //        ViewCompat.setPivotY(v, v.getMeasuredHeight() / 2);
    v.setPivotY(v.getMeasuredHeight() / 2);
    ViewCompat.setPivotX(v, v.getMeasuredWidth() / 2);
    ViewCompat.animate(v).setInterpolator(null);
  }
}
