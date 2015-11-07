package jp.wasabeef.recyclerview.animators.change;
/**
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

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import jp.wasabeef.recyclerview.animators.AnimateChange;
import jp.wasabeef.recyclerview.animators.internal.ViewHelper;

public class TinyScaleAnimate extends AnimateChange {

  public static final float SCALE_VALUE = 0.95f;

  public void animateChange(final RecyclerView.ViewHolder oldHolder, final RecyclerView.ViewHolder newHolder, int fromX, int fromY, int toX, int toY) {
    final long duration = (long) (0.5f * mDispatcher.getChangeDuration());

    if (oldHolder.itemView != null) {
      final ViewPropertyAnimatorCompat oldViewAnim = ViewCompat.animate(oldHolder.itemView);
      oldViewAnim.translationX(toX - fromX);
      oldViewAnim.translationY(toY - fromY);

      oldViewAnim.setDuration(duration);
      oldViewAnim.scaleX(SCALE_VALUE).scaleY(SCALE_VALUE);
      oldViewAnim.setListener(new ViewPropertyAnimatorListener() {
        @Override
        public void onAnimationStart(View view) {
          mDispatcher.dispatchChangeStarting(oldHolder, true);
        }

        @Override
        public void onAnimationEnd(View view) {
          oldViewAnim.setListener(null);
          ViewCompat.setAlpha(view, 1);
          ViewCompat.setTranslationX(view, 0);
          ViewCompat.setTranslationY(view, 0);
          mDispatcher.dispatchChangeFinished(oldHolder, true);

          if (newHolder.itemView != null) {
            ViewCompat.setScaleX(newHolder.itemView, SCALE_VALUE);
            ViewCompat.setScaleY(newHolder.itemView, SCALE_VALUE);
            ViewCompat.setAlpha(newHolder.itemView, 1.f);
            final ViewPropertyAnimatorCompat newViewAnimation = ViewCompat.animate(newHolder.itemView);
            newViewAnimation.translationX(0).translationY(0);
            newViewAnimation.scaleX(1.f).scaleY(1.f);
            newViewAnimation.setDuration(duration);
            newViewAnimation.setListener(new ViewPropertyAnimatorListener() {
              @Override
              public void onAnimationStart(View view) {
                mDispatcher.dispatchChangeStarting(newHolder, false);
              }

              @Override
              public void onAnimationEnd(View view) {
                newViewAnimation.setListener(null);
                ViewCompat.setAlpha(view, 1);
                ViewCompat.setTranslationX(view, 0);
                ViewCompat.setTranslationY(view, 0);
                mDispatcher.dispatchChangeFinished(newHolder, false);
              }

              @Override
              public void onAnimationCancel(View view) {
                ViewHelper.clear(view);
              }
            }).start();
          }
        }

        @Override
        public void onAnimationCancel(View view) {
          ViewHelper.clear(view);
        }
      }).start();
    }


  }
}
