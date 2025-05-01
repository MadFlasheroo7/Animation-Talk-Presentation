package pro.jayeshseth.slides.screens

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateBounds
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import pro.jayeshseth.slides.components.ChalkBoard
import pro.jayeshseth.slides.components.RetroTV
import pro.jayeshseth.slides.ui.theme.chalk_font
import pro.jayeshseth.slides.utils.AnimationSpecs
import pro.jayeshseth.slides.utils.states.LookaheadPoints
import pro.jayeshseth.slides.utils.states.Slide3State

// text field animation not any more

// look shared, shared transistion
@OptIn(ExperimentalSharedTransitionApi::class)
private val boundsTransform = BoundsTransform { initialBounds: Rect, targetBounds: Rect ->
    spring(
        dampingRatio = 1f,
        stiffness = 20f,
        visibilityThreshold = Rect.VisibilityThreshold
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun Slide3(
    slide3State: Slide3State,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier
) {
    AnimatedContent(
        targetState = slide3State.showPoints.value,
//        transitionSpec = {
//            scaleIn() togetherWith scaleOut()
//        },
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 50.dp),
    ) { showPoint1 ->
        if (!showPoint1) {
            TitleLayout(
                slide3State = slide3State,
                sharedTransitionScope = sharedTransitionScope,
                orAnimatedVisibilityScope = animatedVisibilityScope,
                lookaheadAnimatedVisibilityScope = this@AnimatedContent
            )
        } else {
            LookaheadPointsLayout(
                slide3State = slide3State,
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = this@AnimatedContent
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TitleLayout(
    slide3State: Slide3State,
    sharedTransitionScope: SharedTransitionScope,
    orAnimatedVisibilityScope: AnimatedVisibilityScope,
    lookaheadAnimatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier
) {
    with(sharedTransitionScope) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxHeight()
                .animateContentSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .sharedBounds(
                        sharedContentState = rememberSharedContentState("or"),
                        animatedVisibilityScope = orAnimatedVisibilityScope,
                        boundsTransform = boundsTransform,
                    )
                    .animateContentSize()
            ) {
                Text(
                    "LookaheadScope",
                    style = textStyle,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .sharedBounds(
                            sharedContentState = rememberSharedContentState("lookaheadscope"),
                            animatedVisibilityScope = lookaheadAnimatedVisibilityScope,
                            boundsTransform = boundsTransform,
                        )
                )
                AnimatedVisibility(
                    modifier = Modifier.fillMaxWidth(),
                    visible = slide3State.showSharedTransition.value,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically(
                        animationSpec = tween(
                            1200,
                            easing = CubicBezierEasing(0.4f, 1.0f, 1.0f, 1.0f)
                        )
                    ),
                ) {
                    Text(
                        "& SharedTransition Layout",
                        style = textStyle,
                        textAlign = TextAlign.Center
                    )
                }
            }

            AnimatedVisibility(
                visible = !slide3State.showSharedTransition.value,
                enter = slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = AnimationSpecs.tweenIntOffset
                ) + fadeIn(),
                exit = slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = AnimationSpecs.tweenIntOffset
                ) + fadeOut(),
                modifier = Modifier.weight(0.5f)
            ) {
                Log.d("state", "${this.transition.isRunning}")
                LookaheadScopeAnimation(
                    modifier = Modifier
                        .fillMaxHeight()
                        .sharedBounds(
                            sharedContentState = rememberSharedContentState("animatedBox"),
                            animatedVisibilityScope = lookaheadAnimatedVisibilityScope,
                            resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                            boundsTransform = { _, _ ->
                                spring(
                                    dampingRatio = Spring.DampingRatioNoBouncy,
                                    stiffness = Spring.StiffnessVeryLow,
                                    visibilityThreshold = Rect.VisibilityThreshold
                                )
                            },
                            enter = slideInHorizontally(
                                initialOffsetX = { it },
                                animationSpec = AnimationSpecs.tweenIntOffset
                            )
                        )
                        .animateContentSize()
                )
            }
        }
    }

}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun LookaheadPointsLayout(
    slide3State: Slide3State,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier
) {
    with(sharedTransitionScope) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(vertical = 10.dp)
                .animateContentSize()
        ) {
            if (slide3State.clickCounter.intValue < 4) {
                Text(
                    "LookaheadScope",
                    style = textStyle,
                    modifier = Modifier
                        .sharedBounds(
                            sharedContentState = rememberSharedContentState("lookaheadscope"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = boundsTransform,
                        )
                )
            } else {
                Text(
                    "Shared Transition",
                    style = textStyle,
                    modifier = Modifier
                        .sharedBounds(
                            sharedContentState = rememberSharedContentState("lookaheadscope"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = boundsTransform,
                        )
                )
            }
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 12.dp)
                    .animateContentSize()
            ) {
                RetroTV(
                    modifier = Modifier.weight(1f)
                ) {
                    AnimatedContent(
                        targetState = slide3State.lookaheadPoints.value,
                        transitionSpec = {
                            scaleIn() togetherWith scaleOut()
                        },
                        label = "animate tv"
                    ) {
                        when (it) {
                            LookaheadPoints.WhatIsLookahead -> {
                                LookaheadScopeAnimation(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .fillMaxSize()
                                        .sharedBounds(
                                            sharedContentState = rememberSharedContentState("animatedBox"),
                                            animatedVisibilityScope = animatedVisibilityScope,
                                            resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                                            boundsTransform = { _, _ ->
                                                spring(
                                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                                    stiffness = Spring.StiffnessVeryLow,
                                                    visibilityThreshold = Rect.VisibilityThreshold
                                                )
                                            },
                                        )
                                )
                            }
                            LookaheadPoints.PreCalculateLayout -> {
                                LookaheadScopeAnimation(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .fillMaxSize()
                                        .sharedBounds(
                                            sharedContentState = rememberSharedContentState("animatedBox"),
                                            animatedVisibilityScope = animatedVisibilityScope,
                                            resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                                            boundsTransform = { _, _ ->
                                                spring(
                                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                                    stiffness = Spring.StiffnessVeryLow,
                                                    visibilityThreshold = Rect.VisibilityThreshold
                                                )
                                            },
                                        )
                                )
                            }
                            LookaheadPoints.SharedTransition -> {
                                LookaheadScopeAnimation(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .fillMaxSize()
                                        .sharedBounds(
                                            sharedContentState = rememberSharedContentState("animatedBox"),
                                            animatedVisibilityScope = animatedVisibilityScope,
                                            resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                                            boundsTransform = { _, _ ->
                                                spring(
                                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                                    stiffness = Spring.StiffnessVeryLow,
                                                    visibilityThreshold = Rect.VisibilityThreshold
                                                )
                                            },
                                        )
                                )
                            }
                        }
                    }
                }
                ChalkBoard(
                    modifier = Modifier.weight(1f)
                ) {
                    AnimatedContent(
                        modifier = Modifier.align(Alignment.Center),
                        targetState = slide3State.lookaheadPoints.value,
                        transitionSpec = {
                            scaleIn() togetherWith scaleOut()
                        },
                        label = "animate tv"
                    ) {
                        when(it) {
                            LookaheadPoints.WhatIsLookahead -> {
                                PointRevelVisibility(
                                    true,
                                    it.point
                                )
                            }
                            LookaheadPoints.PreCalculateLayout -> {
                                PointRevelVisibility(
                                    true,
                                    it.point
                                )
                            }
                            LookaheadPoints.SharedTransition -> {
                                PointRevelVisibility(
                                    true,
                                    it.point
                                )
                            }
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun PointRevelVisibility(
    visible: Boolean,
    text: String,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = fadeIn() + expandHorizontally(),
        exit = fadeOut() + shrinkHorizontally(),
    ) {
        PointReveal(text, subTextStyle.copy(fontFamily = chalk_font))
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun LookaheadScopeAnimation(modifier: Modifier = Modifier) {
    val boundsTransform = { _: Rect, _: Rect ->
        spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessLow,
            visibilityThreshold = Rect.VisibilityThreshold
        )
    }
    val switch = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (true) {
            switch.value = !switch.value
            delay(2500)
        }
    }
    LookaheadScope {
        val animateBoundsModifier = Modifier.animateBounds(
            lookaheadScope = this@LookaheadScope,
            boundsTransform = boundsTransform
        )
        val moveableContent = remember {
            movableContentOf {
                Box(
                    animateBoundsModifier
                        .size(100.dp)
                        .clip(
                            if (!switch.value)
                                RoundedCornerShape(
                                    topStart = 12.dp,
                                    topEnd = 12.dp
                                )
                            else
                                RoundedCornerShape(
                                    topStart = 12.dp,
                                    bottomStart = 12.dp
                                )
                        )
                        .background(Color.Red)
                )
                Box(
                    animateBoundsModifier
                        .size(100.dp)
                        .clip(
                            if (!switch.value)
                                RoundedCornerShape(
                                    bottomStart = 12.dp,
                                    bottomEnd = 12.dp
                                )
                            else
                                RoundedCornerShape(
                                    topEnd = 12.dp,
                                    bottomEnd = 12.dp
                                )
                        )
                        .background(Color(0xFFFFD500))
                )
            }
        }

        Column(
            modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                if (switch.value) {
                    moveableContent()
                } else {
                    Column {
                        moveableContent()
                    }
                }
            }
        }
    }
}
