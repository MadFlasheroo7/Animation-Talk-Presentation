package pro.jayeshseth.slides.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SlideInOutHorizontalFromLeft(
    visible: Boolean,
    modifier: Modifier = Modifier,
    enter: EnterTransition = slideInHorizontally(
        initialOffsetX = { -it },
        animationSpec = tween(1200, easing = CubicBezierEasing(0.4f, 1.0f, 1.0f, 1.0f))
    ) + scaleIn(
        animationSpec = tween(1200, easing = CubicBezierEasing(0.4f, 1.0f, 1.0f, 1.0f)),
    ),
    exit: ExitTransition = slideOutHorizontally(
        targetOffsetX = { -it },
        animationSpec = tween(1200, easing = CubicBezierEasing(0.4f, 1.0f, 1.0f, 1.0f))
    ) + scaleOut(
        animationSpec = tween(1200, easing = CubicBezierEasing(0.4f, 1.0f, 1.0f, 1.0f))
    ),
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = enter,
        exit = exit,
        content = content
    )
}

@Composable
fun SlideInOutHorizontalFromRight(
    visible: Boolean,
    modifier: Modifier = Modifier,
    enter: EnterTransition = slideInHorizontally(
        initialOffsetX = { it },
        animationSpec = tween(1200)
    ) + scaleIn(
        animationSpec = tween(1200),
    ) + fadeIn(),
    exit: ExitTransition = slideOutHorizontally(
        targetOffsetX = { it },
        animationSpec = tween(1200)
    ) + scaleOut(
        animationSpec = tween(1200),
    ),
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = enter,
        exit = exit,
        content = content
    )
}

@Composable
fun ShrinkOutExpandIn(
    visible: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = fadeIn() + expandIn(),
        exit = shrinkOut() + fadeOut(),
        content = content
    )
}

