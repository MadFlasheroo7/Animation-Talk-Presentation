package pro.jayeshseth.slides.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pro.jayeshseth.slides.screens.Slide1
import pro.jayeshseth.slides.screens.StartPage

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NavGraph(
    navController: NavHostController,
    sharedTransitionScope: SharedTransitionScope,
//    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = StartPage,
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
                fadeOut(
                    animationSpec = tween(2000)
                )
//                slideOutOfContainer(
//                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
//                    animationSpec = tween(400, easing = LinearEasing)
//                )
            }
        ) {
            StartPage(
                navToSlide = { navController.navigate(Slide1) },
                modifier = modifier,
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = this
            )
        }
        composable<Slide1>(
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(400, easing = LinearEasing)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(400, easing = LinearEasing)
                )
            }
        ) {
            Slide1(
                navToSlide = { navController.navigate(StartPage) },
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = this
            )
        }
    }
}