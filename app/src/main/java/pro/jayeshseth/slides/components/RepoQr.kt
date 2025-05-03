package pro.jayeshseth.slides.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import pro.jayeshseth.slides.R

@Composable
fun RepoQr(isVisible: Boolean) {
    var time by remember { mutableStateOf(0f) }
//    val brush = remember { CustomShaderBrush() }
//    var isVisible by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        while (true) {
            withFrameNanos { frameTimeNanos ->
                time = frameTimeNanos / 1_000_000_000f
            }
        }
    }

//    Box(modifier = Modifier.fillMaxSize()) {
    Image(
        painter = painterResource(R.drawable.repoqr),
        contentScale = ContentScale.Fit,
        contentDescription = "",
        modifier = Modifier
            .fillMaxSize()
//                .clickable {
//                    isVisible = !isVisible
//                }
            .animatedShaderVisibility(
                isVisible = isVisible,

                )
    )
//    }
}