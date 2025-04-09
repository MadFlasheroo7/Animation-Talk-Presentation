package pro.jayeshseth.slides.screens

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import pro.jayeshseth.slides.components.AgslTextDisplay
import pro.jayeshseth.slides.components.CurtainLayout
import pro.jayeshseth.slides.components.ImageRenderer
import pro.jayeshseth.slides.components.MultiShaderRevealText
import pro.jayeshseth.slides.components.customBlur
import pro.jayeshseth.slides.components.shaderTextGradient
import pro.jayeshseth.slides.utils.states.Slide4Layouts
import pro.jayeshseth.slides.utils.states.Slide4SlideState

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
fun Slide4(
    modifier: Modifier = Modifier,
    slide4State: Slide4SlideState = Slide4SlideState(),
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    CurtainLayout(
        modifier = modifier.fillMaxSize(),
        content = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                var show by remember { mutableStateOf(false) }
                LaunchedEffect(true) {
                    delay(1000)
                    show = true
                }
                AnimatedVisibility(
                    show
                ) {
                    Button({}) { Text("button") }
                }
            }
        }
    ) {
        Slide(
            slide4State = slide4State,
            sharedTransitionScope = sharedTransitionScope,
            animatedVisibilityScope = animatedVisibilityScope
        )

    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun Slide(
    modifier: Modifier = Modifier,
    slide4State: Slide4SlideState = Slide4SlideState(),
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val blur = remember { Animatable(0f) }

    LaunchedEffect(slide4State.shaderPointsLayout.value, blur) {
        blur.animateTo(100f, tween(500, easing = LinearEasing))
        blur.animateTo(0f, tween(1500, easing = FastOutLinearInEasing))
    }

    val backgroundColor by animateColorAsState(
        targetValue = if (slide4State.showBackground.value) Color.Black else Color.Transparent,
        animationSpec = tween(2000),
        label = ""
    )

    AnimatedContent(
        targetState = slide4State.shaderPointsLayout.value,
        transitionSpec = {
            scaleIn() togetherWith scaleOut()
        },
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor),
    ) { layout ->
        when (layout) {
            Slide4Layouts.TITLE -> {
                TitleLayout(slide4State, blur, sharedTransitionScope, this@AnimatedContent)
            }

            Slide4Layouts.WHAT_IS_SHADER -> {
                ShaderPoints(
                    slide4State = slide4State,
                    sharedTransitionScope = sharedTransitionScope,
                    animatedVisibilityScope = this@AnimatedContent,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Slide4Layouts.AGSL -> {
                AgslLayout(slide4State)
            }

            Slide4Layouts.NONE -> {}
        }
    }

}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun TitleLayout(
    slide4State: Slide4SlideState,
    blur: Animatable<Float, AnimationVector1D>,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    with(sharedTransitionScope) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .animateContentSize()
        ) {
            AnimatedVisibility(
                slide4State.shaderPointsLayout.value == Slide4Layouts.TITLE,
                enter = fadeIn() + scaleIn(),
                exit = scaleOut(tween(2000)) + fadeOut(
                    tween(2000)
                ),
                modifier = Modifier.customBlur(blur.value),
                label = ""
            ) {
                MultiShaderRevealText(
                    Modifier
                        .fillMaxWidth()
                        .sharedBounds(
                            sharedContentState = rememberSharedContentState("multishader"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = boundsTransform,
                        )
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun ShaderPoints(
    slide4State: Slide4SlideState,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "text gradient animation")
    val imgP = slide4State.shaderPoint.value?.images

    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    val brush = remember(offset) {
        object : ShaderBrush() {
            override fun createShader(size: Size): Shader {
                val widthOffset = size.width * offset
                val heightOffset = size.height * offset
                return LinearGradientShader(
                    colors = shaderTextGradient,
                    from = Offset(widthOffset, heightOffset),
                    to = Offset(widthOffset + size.width, heightOffset + size.height),
                    tileMode = TileMode.Mirror
                )
            }
        }

    }
    with(sharedTransitionScope) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = modifier
                .fillMaxSize()
                .animateContentSize()
        ) {
            Text(
                text = "Shaders",
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 80.sp,
                    brush = brush,
                    fontWeight = FontWeight.ExtraBold,
                    textMotion = TextMotion.Animated
                ), modifier = Modifier
                    .sharedBounds(
                        sharedContentState = rememberSharedContentState("multishader"),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = boundsTransform,
                    )
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(1f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    imgP?.first?.let {
                        ImageRenderer(it)
                    }
                    imgP?.second?.let {
                        ImageRenderer(it)
                    }
                }
                AgslTextDisplay(
                    slide4State.shaderPoint.value?.point ?: "",
                    Modifier.weight(0.8f)
                )
            }
        }
    }
}


@Composable
private fun AgslLayout(
    slide4State: Slide4SlideState,
    modifier: Modifier = Modifier
) {
    val imgPoint = slide4State.agslPoint.value?.images

    val textPoint = slide4State.agslPoint.value?.point ?: ""


    Log.d("slide4", "count: ${slide4State.clickCounter.intValue}, text: $textPoint")
    val infiniteTransition = rememberInfiniteTransition(label = "text gradient animation")
    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 5_000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    val brush = remember(offset) {
        object : ShaderBrush() {
            override fun createShader(size: Size): Shader {
                val widthOffset = size.width * offset
                val heightOffset = size.height * offset
                return LinearGradientShader(
                    colors = listOf(
                        Color(0xFFFFFFFF),
                        Color(0xFF8A8A8A),
                        Color(0xFF000000)
                    ),
                    from = Offset(widthOffset, heightOffset),
                    to = Offset(widthOffset + size.width, heightOffset + size.height),
                    tileMode = TileMode.Mirror
                )
            }
        }

    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
            .fillMaxSize()
            .animateContentSize()
    ) {
        Text(
            text = "AGSL",
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 80.sp,
                brush = brush,
                fontWeight = FontWeight.ExtraBold,
                textMotion = TextMotion.Animated
            ),
            modifier = Modifier
//                .sharedBounds(
//                    sharedContentState = rememberSharedContentState("multishader"),
//                    animatedVisibilityScope = animatedVisibilityScope,
//                    boundsTransform = boundsTransform,
//                )
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .weight(1f)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                imgPoint?.first?.let {
                    ImageRenderer(it)
                }
                imgPoint?.second?.let {
                    ImageRenderer(it)
                }
            }
            AgslTextDisplay(
                textPoint,
                Modifier.weight(0.8f)
            )
        }
    }

}