package pro.jayeshseth.slides

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.rememberNavController
import com.mikepenz.hypnoticcanvas.shaderBackground
import com.mikepenz.hypnoticcanvas.shaders.InkFlow
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import pro.jayeshseth.slides.components.VolumeButtonsHandler
import pro.jayeshseth.slides.navigation.NavGraph
import pro.jayeshseth.slides.ui.theme.AnimationTalkSlidesTheme

// TODO: improve enter animations overall
class MainActivity : ComponentActivity() {
    private val viewModel: GlobalNavigatorViewModel by viewModels()

    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val view = LocalView.current
            val context = LocalContext.current
            val window = (context as Activity).window

            val controller = remember(key1 = window, key2 = view) {
                WindowInsetsControllerCompat(window, view)
            }
            val navController = rememberNavController()
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            controller.hide(WindowInsetsCompat.Type.systemBars())
            VolumeButtonsHandler(
                onVolumeUp = {
                    viewModel.onGlobalForward(
                        {
                            navController.navigate(it)
                        },
                        navController.currentDestination!!
                    )
                },
                onVolumeDown = {
                    viewModel.onGlobalBack(navController.currentDestination!!) {
                        navController.navigate(it)
                    }
                }
            )
            AnimationTalkSlidesTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    SharedTransitionLayout {

                        Box {
                            Box(
                                Modifier
                                    .fillMaxSize()
                                    .blur(12.dp)
                                    .shaderBackground(InkFlow, speed = 0.3f)
                            )
                            NavGraph(
                                slide1State = viewModel.slide1State.value,
                                slide2State = viewModel.slide2State.value,
                                navController = navController,
                                sharedTransitionScope = this@SharedTransitionLayout
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalSerializationApi::class)
inline fun <reified T : Any> routeName(serializer: SerializationStrategy<T>): String {
    return serializer.descriptor.serialName
}