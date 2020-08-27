package jp.wasabeef.example.recyclerview

import android.view.animation.Interpolator
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import jp.wasabeef.recyclerview.animators.ScaleInRightAnimator

class MyAnimator(
  interpolator: Interpolator = LinearOutSlowInInterpolator()
) : ScaleInRightAnimator(interpolator) {
}
