package pro.jayeshseth.slides.components

import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged

val fragmentShader = """
    // Shader Input:
    uniform shader composable; // Input texture
    uniform float2 size; // View size
    uniform float progress; // Progress of the disintegration
    uniform float gridWidth; // Width of grid (UV coords)

// Constants:
const float seed = 987654.321;
const float4 transparent = float4(0, 0, 0, 0);

// Helper Function for Random Float
float randFloat(in float x, in float y) {
    return fract(sin((x * 99.9) + y) * seed);
}

half4 main(float2 fragCoord) {
    // Normalize coordinates to [0, 1] range
    float2 uv = fragCoord / size;
    
    // Get source color at the current pixel
    float4 source = composable.eval(fragCoord);
    
    // Calculate height from aspect ratio, for square grid
    float gridHeight = gridWidth * (size.x / size.y);
    
    // Scale grid so each square goes from 0..1
    uv /= float2(gridWidth, gridHeight);
    
    // Offset rows 3 and 4
    bool isSecondRow = fract(uv.y / 4.0) > 1;
    if (isSecondRow) {
        uv.x += 2.0;
    }

    // Define color
    float4 color = transparent;

    //Below the line, just draw the image
    if (fragCoord.y > progress * size.y) {
        color = source;
    } else { // Above the line, draw pattern
        // Calculate local coordinates in the block
        float2 local = fract(uv);
    
        // Flip local coordinates according to pattern
        float xp = fract(uv.x / 4);
        float yp = fract(uv.y / 2);

        // Apply pattern flipping based on coordinates
        if (yp == 0) {
            if (xp == 0 || xp == 1) local.y = 1.0 - local.y;
            if (xp == 1 || xp == 2) local.x = 1.0 - local.x;
        } else if (yp == 1) {
            if (xp == 1 || xp == 2) local.x = 1.0 - local.x;
            if (xp == 2 || xp == 3) local.y = 1.0 - local.y;
        }

        // Get random scale for each semi-circle
        float rscale = (xp < 2) ? randFloat(uv.x, uv.y/2) : randFloat(uv.x/2, uv.y);

        // Get random scale for each main semi-circle
        float nrscale = (xp < 2) ? randFloat((uv.x/2) + 1, uv.y + 1) : randFloat((uv.x/2) - 1, uv.y - 1);

        // Clip scales to range [0, 0.5]
        rscale = rscale / 2.0 + 0.5;
        nrscale = nrscale / 2.0 + 0.5;

        // Animate radius
        float radius = 1.0 - progress * rscale;
        float nradius = 1.0 - progress * nrscale;

        // Time offset for y position
        float t = uv.y * gridHeight;
        radius += t * rscale;
        nradius += t * nrscale;

        // Color fade time
        t = (progress > t) ? progress - t : 0.0;
        
        // Calculate distance and neighbor distance to the center
        float dist = length(local);
        float ndist = length(1.0 - local);

        // Draw circle
        if (dist <= radius || ndist <= nradius) {
            color = mix(source, transparent, t);
        }
    }

    return color;
}
""".trimIndent()

fun Modifier.animatedShaderVisibility(
    isVisible: Boolean,
    spec: AnimationSpec<Float> = tween(durationMillis = 2_000, easing = LinearEasing),
    gridSize: Float = 0.01f, // in uv
    onFinish: () -> Unit = {}
) = composed {
    val shader = remember { RuntimeShader(fragmentShader) }
    val progress by animateFloatAsState(
        targetValue = if (isVisible) 0f else 2f,
        animationSpec = spec,
        label = "",
        finishedListener = { onFinish() }
    )

    this
        .onSizeChanged { size ->
            shader.setFloatUniform("size", size.width.toFloat(), size.height.toFloat())
        }
        .graphicsLayer {
            // Enable clipping
            clip = true

            // Set shader uniforms
            shader.setFloatUniform("progress", progress)
            shader.setFloatUniform("gridWidth", gridSize)

            // Apply a runtime shader effect using the defined shader
            renderEffect = RenderEffect
                .createRuntimeShaderEffect(shader, "composable")
                .asComposeRenderEffect()
        }
}