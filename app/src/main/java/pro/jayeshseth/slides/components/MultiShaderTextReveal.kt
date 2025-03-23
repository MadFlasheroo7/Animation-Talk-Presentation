package pro.jayeshseth.slides.components

import android.graphics.ComposeShader
import android.graphics.PorterDuff
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize


val shaderTextGradient = listOf(
    Color(0xFF00BCD4),
    Color(0xFFE91E63),
    Color(0xFFFFD500)
)

@Composable
fun MultiShaderRevealText(
    modifier: Modifier = Modifier,
    text: String = "Shaders"
) {
    var time by remember { mutableFloatStateOf(0f) }
    val infiniteTransition = rememberInfiniteTransition(label = "text gradient animation")

    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val noiseShader = remember { NoiseShader() }

    val shaderBrush = remember(time) {
        object : ShaderBrush() {
            override fun createShader(size: Size): Shader {
                val widthOffset = size.width * offset
                val heightOffset = size.height * offset
                noiseShader.updateTime(time)

                val gradient = LinearGradientShader(
                    colors = shaderTextGradient,
                    from = Offset(widthOffset, heightOffset),
                    to = Offset(widthOffset + size.width, heightOffset + size.height),
                    tileMode = TileMode.Mirror
                )

                return ComposeShader(
                    noiseShader,
                    gradient,
                    PorterDuff.Mode.MULTIPLY
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        val startTime = withFrameNanos { it } / 4_000_000_000f

        while (true) {
            withFrameNanos { frameTime ->
                val currentTime = frameTime / 4_000_000_000f - startTime
                time = currentTime

                if (currentTime >= 1000 / 1000f) {
                    time = 1f
                }
            }
        }
    }

    Text(
        text = text,
        color = MaterialTheme.colorScheme.onSurface,
        textAlign = TextAlign.Center,
        style = TextStyle(
            fontSize = 200.sp,
            brush = shaderBrush,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.ExtraBold,
            textMotion = TextMotion.Animated
        ),
        modifier = modifier
            .onSizeChanged {
                noiseShader.updateResolution(it.toSize())
            }
    )
}
