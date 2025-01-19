package pro.jayeshseth.slides.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed
import kotlinx.coroutines.delay
import pro.jayeshseth.slides.R
import pro.jayeshseth.slides.components.CoilGif
import pro.jayeshseth.slides.utils.AnimationSpecs
import pro.jayeshseth.slides.utils.states.Slide2State

// tween vs spring

val subTextStyle = TextStyle(
    fontSize = 24.sp,
    color = Color.White,
    fontWeight = FontWeight.Medium,
    textMotion = TextMotion.Animated
)

@OptIn(ExperimentalSharedTransitionApi::class)
val boundsTransformTween = BoundsTransform { initialBounds: Rect, targetBounds: Rect ->
    spring(
        dampingRatio = 1f,
        stiffness = Spring.StiffnessVeryLow,
        visibilityThreshold = Rect.VisibilityThreshold
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun Slide2(
    slide2State: Slide2State,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier
) {
    val animateIn = remember { mutableStateOf(false) }
    LaunchedEffect(true) { animateIn.value = true }
    with(sharedTransitionScope) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
            ) {
                Row(
                    modifier = Modifier
                        .sharedBounds(
                            sharedContentState = rememberSharedContentState("spring"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = boundsTransformTween,
                            enter = slideInHorizontally(
                                initialOffsetX = { -it },
                                animationSpec = AnimationSpecs.tweenIntOffset
                            ) + scaleIn(
                                animationSpec = AnimationSpecs.tweenFloat,
                            )
                        )
                ) {
                    Text(
                        "Spring",
                        style = textStyle,

                        )
                }
                AnimatedVisibility(
                    slide2State.showSpringInfo.value,
                ) {
                    SpringInfo(slide2State)
                }
            }

            AnimatedVisibility(
                animateIn.value,
            ) {
                Row(
                    modifier = Modifier
                        .sharedBounds(
                            sharedContentState = rememberSharedContentState("or"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = { _, _ ->
                                spring(
                                    dampingRatio = 1f,
                                    stiffness = 20f,
                                    visibilityThreshold = Rect.VisibilityThreshold
                                )
                            },
                            enter = slideInHorizontally(
                                initialOffsetX = { -it },
                                animationSpec = AnimationSpecs.tweenIntOffset
                            ) + scaleIn(
                                animationSpec = AnimationSpecs.tweenFloat,
                            )
                        )

                ) {

                    Text(
                        text = " O",
                        style = textStyle,
                        modifier = Modifier
                            .sharedBounds(
                                sharedContentState = rememberSharedContentState("lookahead"),
                                animatedVisibilityScope = animatedVisibilityScope,
                                boundsTransform = boundsTransformTween,
                            )
                    )
                    Text("r ", style = textStyle)
                }
            }

            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    "Tween",
                    style = textStyle,
                    modifier = Modifier
                        .sharedBounds(
                            sharedContentState = rememberSharedContentState("tween"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = boundsTransformTween,
                            enter = slideInHorizontally(
                                initialOffsetX = { -it },
                                animationSpec = AnimationSpecs.tweenIntOffset
                            ) + scaleIn(
                                animationSpec = AnimationSpecs.tweenFloat,
                            )
                        )
                )
                AnimatedVisibility(
                    slide2State.showTweenInfo.value
                ) {
                    TweenInfo(slide2State)
                }
            }
        }
    }
}

@Composable
private fun SpringInfo(
    state: Slide2State,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CoilGif(
            R.raw.spring,
            contentDescription = "",
            modifier = Modifier.size(height = 150.dp, width = 300.dp)
        )
        AnimatedVisibility(
            state.showFirstPoint.value
        ) {
            PointReveal("- Velocity/physics based")
        }
        AnimatedVisibility(
            state.showSecondPoint.value
        ) {
            PointReveal("- dampingRatio defies bounciness, stiffness defies velocity.")
        }
        AnimatedVisibility(
            state.showThirdPoint.value
        ) {
            PointReveal("- good for animations that can be interrupted.")
        }

    }
}

@Composable
private fun calcSpaceWidth(textStyle: TextStyle): Dp {
    val textMeasurer = rememberTextMeasurer()
    val textLayoutResult = remember(textMeasurer) { textMeasurer.measure(" ", textStyle) }
    return with(LocalDensity.current) { textLayoutResult.size.width.toDp() }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PointReveal(
    text: String,
    modifier: Modifier = Modifier
) {
    val zeroSpacingTextStyle = subTextStyle.copy(letterSpacing = 0.sp)
    val initialDelay by remember { mutableLongStateOf(500) }
    val letterDelay by remember { mutableLongStateOf(100) }
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(
            space = calcSpaceWidth(zeroSpacingTextStyle),
            alignment = Alignment.CenterHorizontally,
        ),
        verticalArrangement = Arrangement.Center,
    ) {
        val splitText = text.split(" ")
        val wordStartIndices = remember(text) {
            splitText.runningFold(0) { acc, word -> acc + word.length + 1 }.dropLast(1)
        }
        splitText.fastForEachIndexed { index, word ->
            val wordStartIndex = wordStartIndices[index]
            AnimatedSubtext(
                text = word,
                initialDelay = initialDelay + wordStartIndex * letterDelay,
                letterDelay = letterDelay,
                textStyle = zeroSpacingTextStyle,
            )
        }
    }
}

@Composable
fun AnimatedSubtext(
    text: String,
    initialDelay: Long,
    letterDelay: Long,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = subTextStyle,
) {
    var isAnimating by remember { mutableStateOf(false) }
    LaunchedEffect(text) {
        delay(initialDelay)
        isAnimating = true
    }

    if (isAnimating) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.Bottom,
        ) {
            text.forEachIndexed { index, char ->
                var isVisible by remember { mutableStateOf(false) }
                val transition =
                    updateTransition(targetState = isVisible, label = "visibility transition")

                LaunchedEffect(index) {
                    delay(timeMillis = index * letterDelay)
                    isVisible = true
                }

                ScaleAnimationText(
                    text = char.toString(),
                    transition = transition,
                    modifier = Modifier
                )

            }
        }

    } else {
        Text(
            text = text,
            style = textStyle,
            modifier = Modifier
                .alpha(0f),
        )
    }

}

@Composable
fun ScaleAnimationText(
    text: String,
    transition: Transition<Boolean>,
    modifier: Modifier = Modifier
) {
    val alpha by transition.animateFloat(
        label = "alpha animation",
        transitionSpec = {
            tween(
                durationMillis = 250,
                easing = FastOutSlowInEasing,
            )
        },
    ) { visible ->
        if (visible) 1f else 0f
    }

    val scale by transition.animateFloat(
        label = "scale animation",
        transitionSpec = {
            spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessVeryLow
            )
        },
    ) { visible ->
        if (visible) 1f else 2f
    }

    Text(
        text = text,
        modifier = modifier.graphicsLayer {
            this.alpha = alpha.coerceIn(0f, 1f)
            this.scaleX = scale
            this.scaleY = scale
        },
        style = subTextStyle,
    )

}

@Composable
private fun TweenInfo(
    state: Slide2State,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CoilGif(
            R.raw.tween, contentDescription = "",
            modifier = Modifier.size(height = 150.dp, width = 300.dp)
        )
        AnimatedVisibility(
            state.showFirstPoint.value
        ) {
            PointReveal("- Duration based")
        }
        AnimatedVisibility(
            state.showSecondPoint.value
        ) {
            PointReveal("- easing - fraction or speed of the animation from start to end ")
        }
        AnimatedVisibility(
            state.showThirdPoint.value
        ) {
            PointReveal(
                "- not the best for interrupting, but a lot of customization possibility.",
            )
        }
    }
}