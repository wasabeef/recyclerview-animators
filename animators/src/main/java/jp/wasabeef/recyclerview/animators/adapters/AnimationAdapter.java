package jp.wasabeef.recyclerview.animators.adapters;

import android.animation.Animator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import jp.wasabeef.recyclerview.animators.internal.ViewHelper;

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
public abstract class AnimationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private RecyclerView.Adapter<RecyclerView.ViewHolder> mAdapter;
  private int mDuration = 300;
  private Interpolator mInterpolator = new LinearInterpolator();
  private int mLastPosition = -1;

  private boolean isFirstOnly = true;

  public AnimationAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
    mAdapter = adapter;
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return mAdapter.onCreateViewHolder(parent, viewType);
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    mAdapter.onBindViewHolder(holder, position);

    if (!isFirstOnly || position > mLastPosition) {
      for (Animator anim : getAnimators(holder.itemView)) {
        anim.setDuration(mDuration).start();
        anim.setInterpolator(mInterpolator);
      }
      mLastPosition = position;
    } else {
      ViewHelper.clear(holder.itemView);
    }
  }

  @Override public int getItemCount() {
    return mAdapter.getItemCount();
  }

  public void setDuration(int duration) {
    mDuration = duration;
  }

  public void setInterpolator(Interpolator interpolator) {
    mInterpolator = interpolator;
  }

  public void setStartPosition(int start) {
    mLastPosition = start;
  }

  protected abstract Animator[] getAnimators(View view);

  public void setFirstOnly(boolean firstOnly) {
    isFirstOnly = firstOnly;
  }

  @Override public int getItemViewType(int position) {
    return mAdapter.getItemViewType(position);
  }

  public RecyclerView.Adapter<RecyclerView.ViewHolder> getWrappedAdapter() {
    return mAdapter;
  }
}
