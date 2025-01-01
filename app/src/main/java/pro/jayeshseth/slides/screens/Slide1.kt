package pro.jayeshseth.slides.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateInt
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import org.intellij.lang.annotations.Language
import pro.jayeshseth.animatetextunitasstate.animateTextUnitAsState
import pro.jayeshseth.buttons.GlowingButton
import pro.jayeshseth.buttons.GlowingButtonDefaults
import pro.jayeshseth.slides.R
import pro.jayeshseth.slides.components.CenteredBox
import pro.jayeshseth.slides.components.ChalkBoard
import pro.jayeshseth.slides.components.CoilGif
import pro.jayeshseth.slides.components.RetroTV
import pro.jayeshseth.slides.components.SlideInOutHorizontal
import pro.jayeshseth.slides.ui.theme.chalk_font
import kotlin.random.Random

@Language("AGSL")
val UNIVERSE_SHADER = """
    // Star Nest by Pablo Roman Andrioli

    // This content is under the MIT License.

    const int iterations = 17;
    const float formuparam = 0.53;

    const int volsteps = 20;
    const float stepsize = 0.1;

    const float zoom  = 0.800;
    const float tile  = 0.850;
    const float speed =0.010 ;

    const float brightness =0.0015;
    const float darkmatter =0.300;
    const float distfading =0.730;
    const float saturation =0.850;


    half4 main( in vec2 fragCoord )
    {
    	//get coords and direction
    	vec2 uv=fragCoord.xy/iResolution.xy-.5;
    	uv.y*=iResolution.y/iResolution.x;
    	vec3 dir=vec3(uv*zoom,1.);
    	float time=iTime*speed+.25;

    	//mouse rotation
    	float a1=.5+iMouse.x/iResolution.x*2.;
    	float a2=.8+iMouse.y/iResolution.y*2.;
    	mat2 rot1=mat2(cos(a1),sin(a1),-sin(a1),cos(a1));
    	mat2 rot2=mat2(cos(a2),sin(a2),-sin(a2),cos(a2));
    	dir.xz*=rot1;
    	dir.xy*=rot2;
    	vec3 from=vec3(1.,.5,0.5);
    	from+=vec3(time*2.,time,-2.);
    	from.xz*=rot1;
    	from.xy*=rot2;
    	
    	//volumetric rendering
    	float s=0.1,fade=1.;
    	vec3 v=vec3(0.);
    	for (int r=0; r<volsteps; r++) {
    		vec3 p=from+s*dir*.5;
    		p = abs(vec3(tile)-mod(p,vec3(tile*2.))); // tiling fold
    		float pa,a=pa=0.;
    		for (int i=0; i<iterations; i++) { 
    			p=abs(p)/dot(p,p)-formuparam; // the magic formula
    			a+=abs(length(p)-pa); // absolute sum of average change
    			pa=length(p);
    		}
    		float dm=max(0.,darkmatter-a*a*.001); //dark matter
    		a*=a*a; // add contrast
    		if (r>6) fade*=1.-dm; // dark matter, don't render near
    		//v+=vec3(dm,dm*.5,0.);
    		v+=fade;
    		v+=vec3(s,s*s,s*s*s*s)*a*brightness*fade; // coloring based on distance
    		fade*=distfading; // distance fading
    		s+=stepsize;
    	}
    	v=mix(vec3(length(v)),v,saturation); //color adjust
    	return vec4(v*.01,1.);	
    	
    }
""".trimIndent()

val textStyle = TextStyle(
    fontSize = 50.sp,
    color = Color.White,
    fontWeight = FontWeight.ExtraBold,
)

@Preview
@Composable
private fun StaticEffect() {
    var staticPattern by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(100)
            staticPattern = Random.nextInt()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val random = Random(staticPattern)
            repeat(10_000) {
                val x = random.nextFloat() * size.width
                val y = random.nextFloat() * size.height
                drawCircle(
                    color = if (random.nextBoolean()) Color.White else Color.Black,
                    radius = 3f,
                    center = Offset(x, y)
                )
            }
        }
    }
}

/***
 * - not static
 * - better foundation
 * - find ux issues
 * - find performance blockers
 */

sealed class TvChannels(val title: String?) {
    data object LOADING : TvChannels(null)
    data object STATIC : TvChannels("Not Static")
    data object FOUNDATION : TvChannels("Foundation")
    data object UX : TvChannels("Find UX Issues")
    data object PERFORMANCE : TvChannels("Find Performance Blockers")
    data object EXITING : TvChannels(null)
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun Slide1(
    navToSlide: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier
) {
    val slide1State = remember { Slide1State() }
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.padding(30.dp)
    ) {
        Row {
            TitleLayout(
                swap = slide1State.swap.value,
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope
            )
            Button({
                slide1State.handleClick()
            }) {
                Text("Next")
            }
        }
        TvLayout(
            shouldShowTVLayout = slide1State.showTvLayout.value,
            currentTvChannel = slide1State.currentTvChannel.value,
            currentChalkBoardText = slide1State.currentChalkBoardText
        )
    }
}

class Slide1State {
    var swap = mutableStateOf(false)
        private set
    var showTvLayout = mutableStateOf(false)
        private set
    var currentTvChannel = mutableStateOf<TvChannels>(TvChannels.LOADING)
        private set
    var currentChalkBoardText = mutableStateListOf(TvChannels.LOADING.title)
    private var clickCounter = mutableIntStateOf(0)

    fun handleClick() {
        clickCounter.intValue++
        when (clickCounter.intValue) {
            1 -> swap.value = true
            2 -> if (swap.value) showTvLayout.value = true
            3 -> if (showTvLayout.value) updateTvContent()
        }
    }

    private fun updateTvContent() {
        // Update TV content
        currentTvChannel.value = when (currentTvChannel.value) {
            TvChannels.LOADING -> TvChannels.STATIC
            TvChannels.STATIC -> TvChannels.FOUNDATION
            TvChannels.FOUNDATION -> TvChannels.UX
            TvChannels.UX -> TvChannels.PERFORMANCE
            TvChannels.PERFORMANCE -> TvChannels.EXITING
            TvChannels.EXITING -> TvChannels.LOADING
        }

        val newText = when (currentTvChannel.value) {
            TvChannels.LOADING -> TvChannels.LOADING.title
            TvChannels.STATIC -> TvChannels.STATIC.title
            TvChannels.FOUNDATION -> TvChannels.FOUNDATION.title
            TvChannels.UX -> TvChannels.UX.title
            TvChannels.PERFORMANCE -> TvChannels.PERFORMANCE.title
            TvChannels.EXITING -> TvChannels.EXITING.title
        }
        currentChalkBoardText.add(newText)

        // Keep click count at 3 to continue cycling
        clickCounter.intValue = 2
    }

}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun TitleLayout(
    swap: Boolean,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier
) {
    with(sharedTransitionScope) {
        Row(modifier = modifier) {
            SlideInOutHorizontal(visible = !swap) {
                Text("First ", style = textStyle)
            }
            Text(
                "Animations ",
                modifier = Modifier
//                        .padding(bottom = 50.dp)
                    .sharedBounds(
                        sharedContentState = rememberSharedContentState("animations"),
//                            enter = slideInHorizontally(),
//                            exit = slideOutHorizontally(),

                        animatedVisibilityScope = animatedVisibilityScope,
//                            boundsTransform = BoundsTransform { initialBounds, targetBounds ->
//                                spring(
//                                    dampingRatio = Spring.DampingRatioHighBouncy,
//                                    stiffness = Spring.StiffnessLow
//                                )
//                            }
                    ),
                style = TextStyle(
                    fontSize = 50.sp,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                )
            )
            SlideInOutHorizontal(visible = !swap) {
                Text("UI ", style = textStyle)
            }
            SlideInOutHorizontal(visible = swap) {
                Text("First ", style = textStyle)
            }
            SlideInOutHorizontal(visible = swap) {
                Text("UI", style = textStyle)
            }
            AnimatedVisibility(
                visible = swap,
                enter = fadeIn(animationSpec = tween(500)),
                exit = fadeOut(animationSpec = tween(500))
            ) {
                Text("??", style = textStyle)
            }
        }

    }
}


@Composable
private fun TvLayout(
    shouldShowTVLayout: Boolean,
    currentTvChannel: TvChannels,
    currentChalkBoardText: List<String?>,
    modifier: Modifier = Modifier
) {
    var showTV by remember { mutableStateOf(false) }
    var showChalkBoard by remember { mutableStateOf(false) }
    val lazyListState = rememberLazyListState()

    LaunchedEffect(currentChalkBoardText.size) {
        Log.d("TAG", "TvLayout: ${currentChalkBoardText.lastIndex}")
        lazyListState.animateScrollToItem(currentChalkBoardText.lastIndex)
    }

    LaunchedEffect(shouldShowTVLayout) {
        if (shouldShowTVLayout) {
            showTV = true
            delay(500)
            showChalkBoard = true
        }
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(25.dp),
        modifier = modifier.padding(top = 25.dp)
    ) {
        AnimatedVisibility(
            showTV,
            enter = scaleIn(tween(5000, easing = EaseIn)),
            exit = scaleOut(),
            modifier = Modifier.weight(1f)
        ) {
            RetroTV(
//                Modifier.weight(1f)
            ) {
                AnimatedContent(
                    targetState = currentTvChannel,
                    transitionSpec = {
                        scaleIn() togetherWith scaleOut()
                    }, label = "animate tv"
                ) { tvContent ->
                    when (tvContent) {
                        TvChannels.LOADING -> StaticEffect()
                        TvChannels.STATIC -> NotStaticLayout()
                        TvChannels.FOUNDATION -> {
                            CenteredBox {
                                CoilGif(
                                    R.raw.foundation,
                                    contentDescription = "penguin worker brick walling",
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }

                        /**
                         * make enter animation longer than exit animation
                         *
                         * Transitions that exit, dismiss, or collapse an element use shorter durations. Exit transitions are faster because they require less attention than the user’s next task.
                         *
                         * Transitions that enter or remain persistent on the screen use longer durations. This helps users focus attention on what's new on screen.
                         * url: https://m3.material.io/styles/motion/easing-and-duration/applying-easing-and-duration
                         */
                        TvChannels.UX -> {
                            CenteredBox {
                                CoilGif(
                                    R.raw.app_design,
                                    contentDescription = "video of app ui",
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }

                        TvChannels.PERFORMANCE -> {
                            CenteredBox {
                                CoilGif(
                                    R.raw.app_lag,
                                    contentDescription = "video of app ui lagging",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }

                        TvChannels.EXITING -> StaticEffect()
                    }
                }
            }
        }

        AnimatedVisibility(
            showChalkBoard,
            enter = scaleIn(tween(5000, easing = EaseIn)),
            modifier = Modifier
                .weight(1f)
        ) {
            ChalkBoard(
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        state = lazyListState,
                        modifier = Modifier
                    ) {
                        itemsIndexed(currentChalkBoardText) { index, title ->
                            AnimatedChalkboardText(title, index, currentTvChannel)
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
private fun AnimatedChalkboardText(
    title: String?,
    index: Int,
    currentTvChannels: TvChannels,
    modifier: Modifier = Modifier
) {
    val transitions = updateTransition(index, label = "animate transitions")

    val textColor by transitions.animateColor(
        label = "backgroundColor",
    ) { if (currentTvChannels.title == title) Color.White else Color.White.copy(alpha = 0.7f) }

    val animatedFontWeight by transitions.animateInt(
        label = "fontWeight",
    ) { if (currentTvChannels.title == title) FontWeight.Bold.weight else FontWeight.Normal.weight }

    val animatedFontSize by animateTextUnitAsState(
        targetValue = if (currentTvChannels.title == title) 32.sp else 24.sp,
    )
    if (title != null) {
        Text(
            text = title,
            color = textColor,
            fontSize = animatedFontSize,
            fontWeight = FontWeight(animatedFontWeight),
            textAlign = TextAlign.Center,
            lineHeight = 32.sp,
            fontFamily = chalk_font,
            modifier = modifier
        )
    }

}


@Preview
@Composable
private fun NotStaticLayout() {
    val infiniteTransition = rememberInfiniteTransition()

    val colors = listOf(Color.Cyan, Color.Green, Color.Blue, Color.Yellow)
    var currentColorIndex by remember { mutableStateOf(0) }

    val animatedColor by animateColorAsState(
        targetValue = colors[currentColorIndex],
        label = "colorAnimation",
        animationSpec = tween(
            durationMillis = 6500,
            easing = LinearOutSlowInEasing
        )
    )
    LaunchedEffect(key1 = Unit) {
        while (true) {
            delay(100)
            currentColorIndex = (currentColorIndex + 1) % colors.size
        }
    }
    val animatedSpreadRadius by infiniteTransition.animateValue(
        initialValue = 17.dp,
        targetValue = 50.dp,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        typeConverter = TwoWayConverter(
            convertToVector = { AnimationVector1D(it.value) },
            convertFromVector = { it.value.dp },
        )
    )

    val animatedGlowIntensity by infiniteTransition.animateFloat(
        initialValue = -0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        GlowingButton(
            onClick = {},
            contentPadding = PaddingValues(horizontal = 30.dp, vertical = 16.dp),
            colors = GlowingButtonDefaults.glowingButtonColors(
                containerColor = Color.DarkGray,
                glowColor = animatedColor
            ),
            glowConfigurations = GlowingButtonDefaults.glowConfigurations(
                spreadRadius = animatedSpreadRadius,
                glowIntensity = animatedGlowIntensity,
                glowRadius = 82.dp,
                glowBorderRadius = 100.dp
            )
        ) {
            Text(
                text = "Madifiers",
                color = Color.White
            )

        }
    }
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(
    device = "spec:parent=pixel_5,orientation=landscape", showBackground = false,
    showSystemUi = true
)
@Composable
private fun Slide1Prev() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {
        AnimatedVisibility(true) {
            SharedTransitionLayout {
                Slide1(
                    navToSlide = {},
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedVisibility
                )
            }
        }
    }
}