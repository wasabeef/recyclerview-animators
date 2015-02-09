package jp.wasabeef.recyclerview.animators.internal;

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
        v.setAlpha(1f);
        v.setRotation(0f);
        v.setRotationX(0f);
        v.setRotationY(0f);
        v.setScaleX(1f);
        v.setScaleY(1f);
        v.setTranslationX(0f);
        v.setTranslationY(0f);
    }
}
