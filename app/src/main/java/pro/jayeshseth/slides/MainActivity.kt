package pro.jayeshseth.slides

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import pro.jayeshseth.slides.navigation.NavGraph
import pro.jayeshseth.slides.ui.theme.AnimationTalkSlidesTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val window = (LocalContext.current as Activity).window
            val view = LocalView.current
            val controller = remember(key1 = window, key2 = view) {
                WindowInsetsControllerCompat(window, view)
            }
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            controller.hide(WindowInsetsCompat.Type.systemBars())

            AnimationTalkSlidesTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
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
                                rememberNavController(),
                                this@SharedTransitionLayout
                            )
                        }
                    }
                }
            }
        }
    }
}