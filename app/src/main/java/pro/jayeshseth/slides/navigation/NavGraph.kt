package pro.jayeshseth.slides.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pro.jayeshseth.slides.screens.Slide1
import pro.jayeshseth.slides.screens.Slide2
import pro.jayeshseth.slides.screens.Slide3
import pro.jayeshseth.slides.screens.Slide4
import pro.jayeshseth.slides.screens.StartPage
import pro.jayeshseth.slides.utils.states.Slide1State
import pro.jayeshseth.slides.utils.states.Slide2State
import pro.jayeshseth.slides.utils.states.Slide3State
import pro.jayeshseth.slides.utils.states.Slide4SlideState

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NavGraph(
    slide1State: Slide1State,
    slide2State: Slide2State,
    slide3State: Slide3State,
    slide4State: Slide4SlideState,
    navController: NavHostController,
    sharedTransitionScope: SharedTransitionScope,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Slide4,
        modifier = modifier
    ) {
        composable<StartPage>(
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(400, easing = LinearEasing)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(400, easing = LinearEasing)
                )
            }
        ) {
            StartPage(
                modifier = modifier,
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = this
            )
        }
        composable<Slide1>() {
            Slide1(
                slide1State = slide1State,
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = this
            )
        }

        composable<Slide2> {
            Slide2(
                slide2State = slide2State,
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = this
            )
        }

        composable<Slide3> {
            Slide3(
                slide3State = slide3State,
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = this
            )
        }

        composable<Slide4> {
            Slide4(
                slide4State = slide4State,
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = this
            )
        }
    }
}