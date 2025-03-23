package pro.jayeshseth.slides.components

import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import org.intellij.lang.annotations.Language

@Language("AGSL")
const val WhiteShader = """
    uniform shader composable;

    uniform float visibility;

    half4 main(float2 cord) {
        half4 color = composable.eval(cord);
        color.rgb = step(visibility, color.rgb);
        return color;
    }
"""

@Language("AGSL")
const val ImageShader = """
    uniform shader composable;
    uniform float visibility;

    half4 main(float2 cord) {
        half4 color = composable.eval(cord);
        color.a = step(visibility, color.a);
        return color;
    }
"""


@Composable
fun ShaderContainer(
    modifier: Modifier = Modifier,
    shader: String,
    content: @Composable BoxScope.() -> Unit,
) {
    val runtimeShader = remember {
        RuntimeShader(shader)
    }
    Box(
        modifier = modifier
            .graphicsLayer {
                runtimeShader.setFloatUniform("visibility", 0.2f)
                renderEffect = RenderEffect
                    .createRuntimeShaderEffect(
                        runtimeShader, "composable"
                    )
                    .asComposeRenderEffect()
            },
        content = content
    )
}