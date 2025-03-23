package pro.jayeshseth.slides.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ImageRenderer(
    @DrawableRes image: Int,
    modifier: Modifier = Modifier
) {
    val blur = remember { Animatable(0f) }

    LaunchedEffect(image) {
        blur.animateTo(100f, tween(easing = LinearEasing))
        blur.animateTo(0f, tween(easing = LinearEasing))
    }

    AnimatedContent(
        targetState = image,
        modifier = modifier
            .customBlur(blur.value),
        transitionSpec = {
            (fadeIn(tween(easing = LinearEasing)) + scaleIn(
                tween(
                    1_000,
                    easing = LinearEasing
                )
            )).togetherWith(
                fadeOut(
                    tween(
                        1_000,
                        easing = LinearEasing
                    )
                ) + scaleOut(
                    tween(
                        1_000,
                        easing = LinearEasing
                    )
                )
            )
        }, label = "animated Image"
    ) {
        CoilGif(
            model = it,
            modifier = Modifier
                .size(200.dp)
                .fillMaxHeight()
                .aspectRatio(1f),
            contentDescription = ""
        )
    }
}