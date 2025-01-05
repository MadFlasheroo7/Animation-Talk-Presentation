package pro.jayeshseth.slides.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed
import kotlinx.coroutines.delay
import pro.jayeshseth.slides.R
import pro.jayeshseth.slides.components.CoilGif

// tween vs spring

val subTextStyle = TextStyle(
    fontSize = 20.sp,
    color = Color.White,
    fontWeight = FontWeight.Medium,
)

// TODO() add scrolling content (depends on how we nav)
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun Slide2(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        AnimatedVisibility(
            true,
            modifier = Modifier
                .weight(1f)
        ) {
            SpringInfo()
        }

        Text(" Or ", style = textStyle)

        AnimatedVisibility(
            true,
            modifier = Modifier.weight(1f)
        ) {
            TweenInfo(sharedTransitionScope, animatedVisibilityScope)
        }
    }
}

@Composable
private fun SpringInfo(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Spring", style = textStyle, modifier = Modifier)
        CoilGif(
            R.raw.spring,
            contentDescription = "",
            modifier = Modifier.size(height = 150.dp, width = 300.dp)
        )

        // TODO switchable to scroll layout
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            PointReveal("- Velocity/physics based")
            PointReveal("- dampingRatio defies bounciness, stiffness defies velocity.")
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
    val letterDelay by remember { mutableLongStateOf(50) }
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
                dampingRatio = Spring.DampingRatioNoBouncy,
                stiffness = Spring.StiffnessVeryLow
            )
        },
    ) { visible ->
        val initialScale = 2f
        if (visible) 1f else initialScale
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

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TweenInfo(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier
) {
    with(sharedTransitionScope) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                "Tween", style = textStyle, modifier = Modifier
                    .sharedBounds(
                        sharedContentState = rememberSharedContentState("tween"),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
            )
            CoilGif(
                R.raw.tween, contentDescription = "",
                modifier = Modifier.size(height = 150.dp, width = 300.dp)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                PointReveal("- Duration based")
                PointReveal("- easing - fraction or speed of the animation from start to end ")
                PointReveal(
                    "- not the best for interrupting, but a lot of customization possibility.",
                )
            }
        }
    }

}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(
    device = "spec:parent=pixel_5,orientation=landscape", showBackground = false,
    showSystemUi = true
)
@Composable
private fun Slide1Prev() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {
        AnimatedVisibility(true) {
            SharedTransitionLayout {
                Slide2(
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedVisibility
                )
            }
        }
    }
}

