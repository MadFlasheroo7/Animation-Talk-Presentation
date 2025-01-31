package pro.jayeshseth.slides.utils

import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.unit.IntOffset

/**
 * common place for all animation specs used
 */
object AnimationSpecs {
    /**
     *  FiniteAnimationSpec<IntOffset>
     */
    val tweenIntOffset: FiniteAnimationSpec<IntOffset> = tween(1200, easing = CubicBezierEasing(0.4f, 1.0f, 1.0f, 1.0f))
    val tweenFloat: FiniteAnimationSpec<Float> = tween(1200, easing = CubicBezierEasing(0.4f, 1.0f, 1.0f, 1.0f))

    @OptIn(ExperimentalSharedTransitionApi::class)
    val boundsTransform = BoundsTransform { initialBounds: Rect, targetBounds: Rect ->
        spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow,
            visibilityThreshold = Rect.VisibilityThreshold
        )
    }
}