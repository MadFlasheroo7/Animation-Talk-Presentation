package pro.jayeshseth.slides.screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateInt
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import pro.jayeshseth.animatetextunitasstate.animateTextUnitAsState
import pro.jayeshseth.buttons.GlowingButton
import pro.jayeshseth.buttons.GlowingButtonDefaults
import pro.jayeshseth.slides.R
import pro.jayeshseth.slides.components.CenteredBox
import pro.jayeshseth.slides.components.ChalkBoard
import pro.jayeshseth.slides.components.CoilGif
import pro.jayeshseth.slides.components.RetroTV
import pro.jayeshseth.slides.components.SlideInOutHorizontalFromLeft
import pro.jayeshseth.slides.components.SlideInOutHorizontalFromRight
import pro.jayeshseth.slides.components.StaticEffect
import pro.jayeshseth.slides.ui.theme.chalk_font
import pro.jayeshseth.slides.utils.states.Slide1TvChannels
import pro.jayeshseth.slides.utils.states.Slide1State

val textStyle = TextStyle(
    fontSize = 50.sp,
    color = Color.White,
    fontWeight = FontWeight.ExtraBold,
)

@OptIn(ExperimentalSharedTransitionApi::class)
private val animationBoundsTransform = BoundsTransform { initialBounds: Rect, targetBounds: Rect ->
    spring(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessLow,
        visibilityThreshold = Rect.VisibilityThreshold
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
private val titleBoundsTransform = BoundsTransform { initialBounds, targetBounds ->
    spring(
        dampingRatio = 1f,
        stiffness = Spring.StiffnessVeryLow,
        visibilityThreshold = Rect.VisibilityThreshold
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun Slide1(
    slide1State: Slide1State,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.padding(30.dp)
    ) {
        Row {
            TitleLayout(
                swap = slide1State.swap.value,
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope
            )
        }
        TvLayout(
            shouldShowTVLayout = slide1State.showTvLayout.value,
            currentTvChannel = slide1State.currentTvChannel.value,
            currentChalkBoardText = slide1State.currentChalkBoardText
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun TitleLayout(
    swap: Boolean,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier
) {
    with(sharedTransitionScope) {
        Row(modifier = modifier) {
            AnimatedVisibility(visible = !swap) {
                Text("First ", style = textStyle)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.sharedBounds(
                    sharedContentState = rememberSharedContentState("animations"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = animationBoundsTransform
                )
            ) {
                Text("Anima", style = textStyle)
                Text(
                    "t", style = textStyle, modifier = Modifier
                        .sharedBounds(
                            sharedContentState = rememberSharedContentState("tween"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                            boundsTransform = titleBoundsTransform                        )
                )
                Text("i", style = textStyle)
                Text(
                    "o", style = textStyle, modifier = Modifier
                        .sharedBounds(
                            sharedContentState = rememberSharedContentState("or"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                            boundsTransform = titleBoundsTransform                        )
                )
                Text("n", style = textStyle)
                Text(
                    "s ", style = textStyle, modifier = Modifier
                        .sharedBounds(
                            sharedContentState = rememberSharedContentState("spring"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                            boundsTransform = titleBoundsTransform                        )
                )
            }
            AnimatedVisibility(visible = !swap) {
                Text("UI ", style = textStyle)
            }
            AnimatedVisibility(visible = swap) {
                Text("First ", style = textStyle)
            }
            AnimatedVisibility(visible = swap) {
                Text("UI", style = textStyle)
            }
            AnimatedVisibility(
                visible = swap,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text("??", style = textStyle)
            }
        }

    }
}

@Composable
private fun TvLayout(
    shouldShowTVLayout: Boolean,
    currentTvChannel: Slide1TvChannels,
    currentChalkBoardText: List<String?>,
    modifier: Modifier = Modifier
) {
    var showTV by remember { mutableStateOf(false) }
    var showChalkBoard by remember { mutableStateOf(false) }
    val lazyListState = rememberLazyListState()

    LaunchedEffect(currentChalkBoardText.size) {
        if (currentChalkBoardText.isNotEmpty()) lazyListState.animateScrollToItem(
            currentChalkBoardText.lastIndex
        )
    }

    LaunchedEffect(shouldShowTVLayout) {
        if (shouldShowTVLayout) {
            showTV = true
            showChalkBoard = true
        }
        if (!shouldShowTVLayout) {
            showTV = false
            showChalkBoard = false
        }
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(25.dp),
        modifier = modifier.padding(top = 25.dp)
    ) {
        SlideInOutHorizontalFromLeft(showTV, Modifier.weight(1f)) {
            RetroTV {
                AnimatedContent(
                    targetState = currentTvChannel,
                    transitionSpec = {
                        scaleIn() togetherWith scaleOut()
                    },
                    label = "animate tv"
                ) { tvContent ->
                    when (tvContent) {
                        Slide1TvChannels.LOADING -> StaticEffect()
                        Slide1TvChannels.STATIC -> NotStaticLayout()
                        Slide1TvChannels.FOUNDATION -> {
                            CenteredBox {
                                CoilGif(
                                    R.raw.foundation,
                                    contentDescription = "penguin worker brick walling",
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }

                        /**
                         * make enter animation longer than exit animation
                         *
                         * Transitions that exit, dismiss, or collapse an element use shorter durations. Exit transitions are faster because they require less attention than the user’s next task.
                         *
                         * Transitions that enter or remain persistent on the screen use longer durations. This helps users focus attention on what's new on screen.
                         * url: https://m3.material.io/styles/motion/easing-and-duration/applying-easing-and-duration
                         */

                        /**
                         * make enter animation longer than exit animation
                         *
                         * Transitions that exit, dismiss, or collapse an element use shorter durations. Exit transitions are faster because they require less attention than the user’s next task.
                         *
                         * Transitions that enter or remain persistent on the screen use longer durations. This helps users focus attention on what's new on screen.
                         * url: https://m3.material.io/styles/motion/easing-and-duration/applying-easing-and-duration
                         */
                        Slide1TvChannels.UX -> {
                            CenteredBox {
                                CoilGif(
                                    R.raw.app_design,
                                    contentDescription = "video of app ui",
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }

                        Slide1TvChannels.PERFORMANCE -> {
                            CenteredBox {
                                CoilGif(
                                    R.raw.app_lag,
                                    contentDescription = "video of app ui lagging",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }

                        Slide1TvChannels.EXITING -> StaticEffect()
                    }
                }
            }
        }

        SlideInOutHorizontalFromRight(showChalkBoard, Modifier.weight(1f)) {
            ChalkBoard {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        state = lazyListState,
                        modifier = Modifier
                    ) {
                        itemsIndexed(currentChalkBoardText) { index, title ->
                            AnimatedChalkboardText(title, index, currentTvChannel)
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
private fun AnimatedChalkboardText(
    title: String?,
    index: Int,
    currentTvChannels: Slide1TvChannels,
    modifier: Modifier = Modifier
) {
    val transitions = updateTransition(index, label = "animate transitions")

    val textColor by transitions.animateColor(
        label = "backgroundColor",
    ) { if (currentTvChannels.title == title) Color.White else Color.White.copy(alpha = 0.7f) }

    val animatedFontWeight by transitions.animateInt(
        label = "fontWeight",
    ) { if (currentTvChannels.title == title) FontWeight.Bold.weight else FontWeight.Normal.weight }

    val animatedFontSize by animateTextUnitAsState(
        targetValue = if (currentTvChannels.title == title) 32.sp else 24.sp,
    )
    if (title != null) {
        Text(
            text = title,
            color = textColor,
            fontSize = animatedFontSize,
            fontWeight = FontWeight(animatedFontWeight),
            textAlign = TextAlign.Center,
            lineHeight = 32.sp,
            fontFamily = chalk_font,
            style = LocalTextStyle.current.copy(textMotion = TextMotion.Animated),
            modifier = modifier
        )
    }

}


@Preview
@Composable
private fun NotStaticLayout() {
    val infiniteTransition = rememberInfiniteTransition()

    val colors = listOf(Color.Cyan, Color.Green, Color.Blue, Color.Yellow)
    var currentColorIndex by remember { mutableIntStateOf(0) }

    val animatedColor by animateColorAsState(
        targetValue = colors[currentColorIndex],
        label = "colorAnimation",
        animationSpec = tween(
            durationMillis = 6500,
            easing = LinearOutSlowInEasing
        )
    )
    LaunchedEffect(key1 = Unit) {
        while (true) {
            delay(100)
            currentColorIndex = (currentColorIndex + 1) % colors.size
        }
    }
    val animatedSpreadRadius by infiniteTransition.animateValue(
        initialValue = 17.dp,
        targetValue = 50.dp,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        typeConverter = TwoWayConverter(
            convertToVector = { AnimationVector1D(it.value) },
            convertFromVector = { it.value.dp },
        )
    )

    val animatedGlowIntensity by infiniteTransition.animateFloat(
        initialValue = -0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        GlowingButton(
            onClick = {},
            contentPadding = PaddingValues(horizontal = 30.dp, vertical = 16.dp),
            colors = GlowingButtonDefaults.glowingButtonColors(
                containerColor = Color.DarkGray,
                glowColor = animatedColor
            ),
            glowConfigurations = GlowingButtonDefaults.glowConfigurations(
                spreadRadius = animatedSpreadRadius,
                glowIntensity = animatedGlowIntensity,
                glowRadius = 82.dp,
                glowBorderRadius = 100.dp
            )
        ) {
            Text(
                text = "Madifiers",
                color = Color.White
            )
        }
    }
}