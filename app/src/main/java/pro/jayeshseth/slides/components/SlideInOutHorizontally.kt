package pro.jayeshseth.slides.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
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
import pro.jayeshseth.slides.utils.AnimationSpecs

@Composable
fun SlideInOutHorizontalFromLeft(
    visible: Boolean,
    modifier: Modifier = Modifier,
    enter: EnterTransition = slideInHorizontally(
        initialOffsetX = { -it },
        animationSpec = AnimationSpecs.tweenIntOffset
    ) + scaleIn(
        animationSpec = AnimationSpecs.tweenFloat,
    ),
    exit: ExitTransition = slideOutHorizontally(
        targetOffsetX = { -it },
        animationSpec = AnimationSpecs.tweenIntOffset
    ) + scaleOut(
        animationSpec = AnimationSpecs.tweenFloat
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
        animationSpec = AnimationSpecs.tweenIntOffset
    ) + scaleIn(
        animationSpec = AnimationSpecs.tweenFloat,
    ) + fadeIn(),
    exit: ExitTransition = slideOutHorizontally(
        targetOffsetX = { it },
        animationSpec = AnimationSpecs.tweenIntOffset
    ) + scaleOut(
        animationSpec = AnimationSpecs.tweenFloat,
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

