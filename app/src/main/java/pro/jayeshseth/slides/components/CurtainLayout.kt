package pro.jayeshseth.slides.components

import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableDefaults
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.forEach
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import org.intellij.lang.annotations.Language
import pro.jayeshseth.slides.components.DragAnchors.*

@Language("AGSL")
val SHADER_SRC = """
uniform float2 iResolution;
uniform float2 iOffset;
uniform shader background;
const float PI = 3.14159265359;
const float X_SHIFT_START = 0.2;

vec4 main(vec2 fragCoord)
{
    vec2 uv = fragCoord / iResolution.x; // change coordinate system 0..1
    vec2 offset = iOffset / iResolution.x;
    float xShift = max(0, X_SHIFT_START - offset.x);
    uv.x += xShift * .5;
    vec2 a = abs(uv - offset);
    a = a * a * .2;
    float dist = offset.x + (a.y * (1. - uv.x) * (1.0 - offset.x));
    float distI = 1.0 - dist;
    float freq = 5.0;
    float intensity = distI * .5;
    float angle = (uv.x / dist) * 2.0 * PI * freq;
    float light = sin(angle) * intensity + 1.0;
    float fold = -cos(angle) * intensity;
    
    vec2 pos = vec2(uv.x / dist, uv.y + fold * .03);
    pos *= iResolution.x;
    vec3 col = background.eval(pos).rgb * light;
    col = mix(col, vec3(0.1), smoothstep(dist, dist + .005, uv.x));
    float shadow = 0.02;
    float alpha = 1.0 - smoothstep(dist, dist + shadow, uv.x);
    return vec4(col * alpha, alpha);
}
""".trimIndent()

enum class DragAnchors {
    Start,
    HalfStart,
    Center,
    HalfEnd,
    End
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CurtainLayout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    overlay: @Composable () -> Unit
) {
    val foreground = remember { movableContentOf(overlay) }
    val density = LocalDensity.current
    val shader = remember { RuntimeShader(SHADER_SRC) }
    var yPosition by remember { mutableFloatStateOf(0f) }
    val state = remember {
        AnchoredDraggableState(initialValue = Start)
    }

    Box(
        modifier = modifier
            .anchoredDraggable(
                state = state,
                orientation = Orientation.Horizontal,
                flingBehavior = AnchoredDraggableDefaults.flingBehavior(
                    state = state,
                    positionalThreshold = { distance: Float -> distance * 5f},
                    animationSpec = spring()
                )
            )
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) { // just to be able to get y position of pointer
                        yPosition = awaitPointerEvent().changes.last().position.y
                    }
                }
            }
            .onSizeChanged { size ->
                val dragEndPoint = size.width.toFloat()
                state.updateAnchors(
                    newAnchors = DraggableAnchors {
                        Start at dragEndPoint
                        HalfStart at dragEndPoint * .25f
                        Center at dragEndPoint * .5f
                        HalfEnd at dragEndPoint * .75f
                        End at 0f
                    }
                )
            }
            .visualizeDraggableAnchors(state, Orientation.Horizontal)
    ) {
        if (state.isAnimationRunning || state.progress(
                state.settledValue,
                state.targetValue
            ) < 1.0 || state.currentValue == End
        ) {
//            Box(Modifier.fillMaxSize().background(Color.Red))
            content()
        }
        if (state.isAnimationRunning || state.progress(state.settledValue, state.targetValue) < 1.0 || state.currentValue != End) {
            Box(
                modifier = Modifier
                    .then(
                        if (state.currentValue != Start) {
                            Modifier.graphicsLayer {
                                shader.setFloatUniform("iResolution", size.width, size.height)
                                shader.setFloatUniform("iOffset", state.requireOffset(), yPosition)
                                renderEffect =
                                    RenderEffect.createRuntimeShaderEffect(shader, "background")
                                        .asComposeRenderEffect()
                            }
                        } else Modifier
                    ),
                content = { foreground() }
            )
        } else if (state.currentValue == Start) {
            Box(Modifier
                .fillMaxSize()
                .background(Color.Green))
//            foreground()
        }
    }
}

private fun Modifier.visualizeDraggableAnchors(
    state: AnchoredDraggableState<*>,
    orientation: Orientation,
    lineColor: Color = Color.Transparent,
    lineStrokeWidth: Float = 10f,
    linePathEffect: PathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 30f))
) = drawWithContent {
    drawContent()
    state.anchors.forEach { _, position ->
        val startOffset =
            Offset(
                x = if (orientation == Orientation.Horizontal) position else 0f,
                y = if (orientation == Orientation.Vertical) position else 0f
            )
        val endOffset =
            Offset(
                x = if (orientation == Orientation.Horizontal) startOffset.x else size.height,
                y = if (orientation == Orientation.Vertical) startOffset.y else size.width
            )
        drawLine(
            color = lineColor,
            start = startOffset,
            end = endOffset,
            strokeWidth = lineStrokeWidth,
            pathEffect = linePathEffect
        )
    }
}
