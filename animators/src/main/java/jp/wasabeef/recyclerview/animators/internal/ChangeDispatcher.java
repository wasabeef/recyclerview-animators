package jp.wasabeef.recyclerview.animators.internal;

import android.support.v7.widget.RecyclerView;

public interface ChangeDispatcher {
  void dispatchChangeStarting(RecyclerView.ViewHolder item, boolean oldItem);
  void dispatchChangeFinished(RecyclerView.ViewHolder item, boolean oldItem);
}
