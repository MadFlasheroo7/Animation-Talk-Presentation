package pro.jayeshseth.slides.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.unit.sp

@Composable
fun AgslTextDisplay(
    text: String,
    modifier: Modifier = Modifier
) {
    val blur = remember { Animatable(0f) }

    LaunchedEffect(text) {
        blur.animateTo(100f, tween(500, easing = LinearEasing))
        blur.animateTo(0f, tween(1000, easing = FastOutLinearInEasing))
    }
    ShaderContainer(
        modifier = modifier.fillMaxSize(),
        shader = WhiteShader
    ) {
        AnimatedContent(
            targetState = text,
            modifier = Modifier
                .align(Alignment.Center)
                .customBlur(blur.value),
            transitionSpec = { (scaleIn() + fadeIn()).togetherWith(scaleOut() + fadeOut()) },
            label = "animated agsl text"
        ) { text ->
            Text(
                text = text,
                style = TextStyle(
                    fontSize = 58.sp,
                    brush = SolidColor(Color.White),
                    fontWeight = FontWeight.ExtraBold,
                    textMotion = TextMotion.Animated
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}