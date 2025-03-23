package pro.jayeshseth.slides.components

import android.graphics.RenderEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer

fun Modifier.customBlur(blur: Float): Modifier {
    return graphicsLayer {
        if (blur > 0f)
            renderEffect = RenderEffect
                .createBlurEffect(
                    blur,
                    blur,
                    android.graphics.Shader.TileMode.DECAL,
                )
                .asComposeRenderEffect()
    }
}