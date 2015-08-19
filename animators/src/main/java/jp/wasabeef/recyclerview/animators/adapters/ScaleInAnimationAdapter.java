package jp.wasabeef.recyclerview.animators.adapters;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
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

public class ScaleInAnimationAdapter extends AnimationAdapter {

  private static final float DEFAULT_SCALE_FROM = .5f;
  private final float mFrom;

  public ScaleInAnimationAdapter(RecyclerView.Adapter adapter) {
    this(adapter, DEFAULT_SCALE_FROM);
  }

  public ScaleInAnimationAdapter(RecyclerView.Adapter adapter, float from) {
    super(adapter);
    mFrom = from;
  }

  @Override protected Animator[] getAnimators(View view) {
    ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", mFrom, 1f);
    ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", mFrom, 1f);
    return new ObjectAnimator[] { scaleX, scaleY };
  }
}
