package pro.jayeshseth.slides.screens

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontSynthesis
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pro.jayeshseth.slides.R

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun StartPage(
    navToSlide: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier
) {
    val textStyle = TextStyle(
        color = Color.White,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        fontSynthesis = FontSynthesis.Style
    )
    val interactionSource = remember {
        MutableInteractionSource()
    }
    var cardSize by remember { mutableStateOf(IntSize.Zero) }
    val gradientColors = listOf(
        Color(0xFFF700FF),
        Color(0xFF03A9F4),
        Color(0xFFFFD500)
    )
    val infiniteTransition = rememberInfiniteTransition(label = "text gradient animation")

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
                    colors = gradientColors,
                    from = Offset(widthOffset, heightOffset),
                    to = Offset(widthOffset + size.width, heightOffset + size.height),
                    tileMode = TileMode.Mirror
                )
            }
        }
    }

    with(sharedTransitionScope) {
        Box(
            modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = navToSlide
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.Center)
            ) {
                Text(
                    buildAnnotatedString {
                        append("Compose Your ")
                        withStyle(
                            style = SpanStyle(
                                fontSize = 50.sp,
                                brush = brush,
                                fontWeight = FontWeight.ExtraBold,
                            )
                        ) {
                            append("Animations")
                        }
                    },
                    modifier = Modifier
                        .padding(bottom = 50.dp)
                        .sharedBounds(
                            sharedContentState = rememberSharedContentState("animations"),
                            animatedVisibilityScope = animatedVisibilityScope
                        ),
                    style = TextStyle(
                        fontSize = 50.sp,
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                    )
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.me),
                        contentDescription = "image of mad flasher",
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .size(with(LocalDensity.current) { cardSize.height.toDp() })
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color.DarkGray.copy(alpha = 0.5f)
                        ),
                        modifier = Modifier
                            .onSizeChanged {
                                cardSize = it
                            }
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier
                                .padding(horizontal = 18.dp, vertical = 20.dp)

                        ) {
                            Text("😎  Jayesh Seth", style = textStyle)
                            Text(
                                "\uD83D\uDC68\u200D\uD83D\uDCBB  Mobile Application Developer",
                                style = textStyle
                            )
                            Text("\uD83C\uDFE2  1Pharmacy Network", style = textStyle)
                        }
                    }
                }
            }
        }
    }
}