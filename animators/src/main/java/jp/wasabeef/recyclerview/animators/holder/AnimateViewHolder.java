package jp.wasabeef.recyclerview.animators.holder;

import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import jp.wasabeef.recyclerview.internal.ViewHelper;

public abstract class AnimateViewHolder extends RecyclerView.ViewHolder {

  public AnimateViewHolder(View itemView) {
    super(itemView);
  }

  public void clearAnimation() {
    ViewHelper.clear(itemView);
  }

  public void preAnimateAddImpl() {
  }

  public void preAnimateRemoveImpl() {
  }

  public abstract void animateAddImpl(ViewPropertyAnimatorListener listener);

  public abstract void animateRemoveImpl(ViewPropertyAnimatorListener listener);
}
