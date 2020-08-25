package jp.wasabeef.recyclerview.adapters;

import android.animation.Animator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import jp.wasabeef.recyclerview.internal.ViewHelper;

/**
 * Copyright (C) 2018 Wasabeef
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

  private RecyclerView.Adapter mAdapter;
  private int mDuration = 300;
  private Interpolator mInterpolator = new LinearInterpolator();
  private int mLastPosition = -1;

  private boolean isFirstOnly = true;

  public AnimationAdapter(RecyclerView.Adapter<? extends RecyclerView.ViewHolder> adapter) {
    mAdapter = adapter;
  }

  @NonNull @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return mAdapter.onCreateViewHolder(parent, viewType);
  }

  @Override
  public void registerAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
    super.registerAdapterDataObserver(observer);
    mAdapter.registerAdapterDataObserver(observer);
  }

  @Override
  public void unregisterAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
    super.unregisterAdapterDataObserver(observer);
    mAdapter.unregisterAdapterDataObserver(observer);
  }

  @Override public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
    super.onAttachedToRecyclerView(recyclerView);
    mAdapter.onAttachedToRecyclerView(recyclerView);
  }

  @Override public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
    super.onDetachedFromRecyclerView(recyclerView);
    mAdapter.onDetachedFromRecyclerView(recyclerView);
  }

  @SuppressWarnings("unchecked") @Override
  public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
    super.onViewAttachedToWindow(holder);
    mAdapter.onViewAttachedToWindow(holder);
  }

  @SuppressWarnings("unchecked") @Override
  public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
    super.onViewDetachedFromWindow(holder);
    mAdapter.onViewDetachedFromWindow(holder);
  }

  @SuppressWarnings("unchecked") @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    mAdapter.onBindViewHolder(holder, position);

    int adapterPosition = holder.getAdapterPosition();
    if (!isFirstOnly || adapterPosition > mLastPosition) {
      for (Animator anim : getAnimators(holder.itemView)) {
        anim.setDuration(mDuration).start();
        anim.setInterpolator(mInterpolator);
      }
      mLastPosition = adapterPosition;
    } else {
      ViewHelper.clear(holder.itemView);
    }
  }

  @SuppressWarnings("unchecked") @Override
  public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
    mAdapter.onViewRecycled(holder);
    super.onViewRecycled(holder);
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

  protected abstract Animator[] getAnimators(@NonNull View view);

  public void setFirstOnly(boolean firstOnly) {
    isFirstOnly = firstOnly;
  }

  @Override public int getItemViewType(int position) {
    return mAdapter.getItemViewType(position);
  }

  @SuppressWarnings("unchecked")
  public RecyclerView.Adapter<RecyclerView.ViewHolder> getWrappedAdapter() {
    return mAdapter;
  }

  @Override public long getItemId(int position) {
    return mAdapter.getItemId(position);
  }
}
