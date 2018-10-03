package jp.wasabeef.recyclerview.animators.holder;

import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;

public interface AnimateRemoveViewHolder {

    void preAnimateRemoveImpl(final RecyclerView.ViewHolder holder);

    void animateRemoveImpl(final RecyclerView.ViewHolder holder, ViewPropertyAnimatorListener listener);

}
