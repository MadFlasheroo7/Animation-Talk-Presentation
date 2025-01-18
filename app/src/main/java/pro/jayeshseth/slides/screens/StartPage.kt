package pro.jayeshseth.slides.screens

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.animation.core.DeferredTargetAnimation
import androidx.compose.animation.core.ExperimentalAnimatableApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ApproachLayoutModifierNode
import androidx.compose.ui.layout.ApproachMeasureScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontSynthesis
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import androidx.compose.ui.unit.sp
import pro.jayeshseth.slides.R

private val gradientColors = listOf(
    Color(0xFFF700FF),
    Color(0xFF03A9F4),
    Color(0xFFFFD500)
)

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun StartPage(
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
    var cardSize by remember { mutableStateOf(IntSize.Zero) }

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
    val boundsTransform = BoundsTransform { initialBounds: Rect, targetBounds: Rect ->
        spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow,
            visibilityThreshold = Rect.VisibilityThreshold
        )
    }
    with(sharedTransitionScope) {
        Box(
            modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.Center)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(bottom = 50.dp)
                ) {
                    Text(
                        text = "Compose Your ",
                        style = TextStyle(
                            fontSize = 50.sp,
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                        )
                    )
                    Text(
                        text = "Animations",
                        modifier = Modifier
                            .sharedBounds(
                                sharedContentState = rememberSharedContentState("animations"),
                                animatedVisibilityScope = animatedVisibilityScope,
                                boundsTransform = boundsTransform
                            ),
                        style = TextStyle(
                            fontSize = 50.sp,
                            brush = brush,
                            fontWeight = FontWeight.ExtraBold,
                            textMotion = TextMotion.Animated
                        )
                    )
                }
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

/**
 * Creates a custom implementation of ApproachLayoutModifierNode to approach the placement of
 * the layout using an animation.
 */
class AnimatedPlacementModifierNode(var lookaheadScope: LookaheadScope) :
    ApproachLayoutModifierNode, Modifier.Node() {
    // Creates an offset animation, the target of which will be known during placement.
    @OptIn(ExperimentalAnimatableApi::class)
    val offsetAnimation: DeferredTargetAnimation<IntOffset, AnimationVector2D> =
        DeferredTargetAnimation(IntOffset.VectorConverter)

    override fun isMeasurementApproachInProgress(lookaheadSize: IntSize): Boolean {
        // Since we only animate the placement here, we can consider measurement approach
        // complete.
        return false
    }

    // Returns true when the offset animation is in progress, false otherwise.
    @OptIn(ExperimentalAnimatableApi::class)
    override fun Placeable.PlacementScope.isPlacementApproachInProgress(
        lookaheadCoordinates: LayoutCoordinates
    ): Boolean {
        val target =
            with(lookaheadScope) {
                lookaheadScopeCoordinates.localLookaheadPositionOf(lookaheadCoordinates).round()
            }
        offsetAnimation.updateTarget(target, coroutineScope)
        return !offsetAnimation.isIdle
    }

    @OptIn(ExperimentalAnimatableApi::class)
    override fun ApproachMeasureScope.approachMeasure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {
        val placeable = measurable.measure(constraints)
        return layout(placeable.width, placeable.height) {
            val coordinates = coordinates
            if (coordinates != null) {
                // Calculates the target offset within the lookaheadScope
                val target =
                    with(lookaheadScope) {
                        lookaheadScopeCoordinates.localLookaheadPositionOf(coordinates).round()
                    }

                // Uses the target offset to start an offset animation
                val animatedOffset = offsetAnimation.updateTarget(target, coroutineScope)
                // Calculates the *current* offset within the given LookaheadScope
                val placementOffset =
                    with(lookaheadScope) {
                        lookaheadScopeCoordinates
                            .localPositionOf(coordinates, Offset.Zero)
                            .round()
                    }
                // Calculates the delta between animated position in scope and current
                // position in scope, and places the child at the delta offset. This puts
                // the child layout at the animated position.
                val (x, y) = animatedOffset - placementOffset
                placeable.place(x, y)
            } else {
                placeable.place(0, 0)
            }
        }
    }
}

// Creates a custom node element for the AnimatedPlacementModifierNode above.
data class AnimatePlacementNodeElement(val lookaheadScope: LookaheadScope) :
    ModifierNodeElement<AnimatedPlacementModifierNode>() {

    override fun update(node: AnimatedPlacementModifierNode) {
        node.lookaheadScope = lookaheadScope
    }

    override fun create(): AnimatedPlacementModifierNode {
        return AnimatedPlacementModifierNode(lookaheadScope)
    }
}

@Composable
fun Lookahead(modifier: Modifier = Modifier) {
    val colors = listOf(Color(0xffff6f69), Color(0xffffcc5c), Color(0xff264653), Color(0xff2a9d84))
    var isInColumn by remember { mutableStateOf(true) }


    LookaheadScope {
        // Creates movable content containing 4 boxes. They will be put either in a [Row] or in a
        // [Column] depending on the state
        val items = remember {
            movableContentOf {
                colors.forEach { color ->
                    Box(
                        Modifier
                            .padding(15.dp)
                            .size(100.dp, 80.dp)
                            .then(AnimatePlacementNodeElement(this))
                            .background(color, RoundedCornerShape(20))
                    )
                }
            }
        }

        Box(modifier = Modifier
            .fillMaxSize()
            .clickable { isInColumn = !isInColumn }) {
            // As the items get moved between Column and Row, their positions in LookaheadScope
            // will change. The `animatePlacementInScope` modifier created above will
            // observe that final position change via `localLookaheadPositionOf`, and create
            // a position animation.
            if (isInColumn) {
                Column(Modifier.fillMaxSize()) { items() }
            } else {
                Row { items() }
            }
        }
    }

}

