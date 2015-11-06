package jp.wasabeef.recyclerview.animators;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import jp.wasabeef.recyclerview.animators.internal.ChangeDispatcher;

public class AnimateChange {
  private ChangeDispatcher mDispatcher;

  public void setAnimatorChangeDispatcher(ChangeDispatcher animatorChangeDispatcher) {
    this.mDispatcher = animatorChangeDispatcher;
  }

  public void animateChange(RecyclerView.ViewHolder oldHolder, final RecyclerView.ViewHolder newHolder, int fromX, int fromY, int toX, int toY) {
    final View newView = newHolder.itemView;
    if (newView != null) {
      final ViewPropertyAnimatorCompat newViewAnimation = ViewCompat.animate(newView);
      newViewAnimation.translationX(0).translationY(0)
              //.setDuration()
              .alpha(1).setListener(new ViewPropertyAnimatorListener() {
        @Override public void onAnimationStart(View view) {
          mDispatcher.dispatchChangeStarting(newHolder, false);
        }

        @Override public void onAnimationEnd(View view) {
          newViewAnimation.setListener(null);
          ViewCompat.setAlpha(newView, 1);
          ViewCompat.setTranslationX(newView, 0);
          ViewCompat.setTranslationY(newView, 0);
          mDispatcher.dispatchChangeFinished(newHolder, false);
        }

        @Override
        public void onAnimationCancel(View view) { }
      }).start();
    }
  }

}
