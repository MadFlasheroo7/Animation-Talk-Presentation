package pro.jayeshseth.slides

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDestination
import pro.jayeshseth.slides.navigation.Slide1
import pro.jayeshseth.slides.navigation.Slide2
import pro.jayeshseth.slides.navigation.StartPage
import pro.jayeshseth.slides.utils.Slide1TvChannels
import pro.jayeshseth.slides.utils.states.Slide1State
import pro.jayeshseth.slides.utils.states.Slide2State

class GlobalNavigatorViewModel : ViewModel() {
    val slide1State = mutableStateOf(Slide1State())
    val slide2State = mutableStateOf(Slide2State())

    fun onGlobalForward(
        navAction: (Any) -> Unit,
        currentRoute: NavDestination,
    ) {
        when {
            currentRoute.route?.equals(routeName(StartPage.serializer())) == true -> {
                navAction(Slide1)
            }

            currentRoute.route?.equals(routeName(Slide1.serializer())) == true -> {
                if (slide1State.value.currentTvChannel.value != Slide1TvChannels.EXITING) {
                    slide1State.value.handleClick()
                } else {
                    navAction(Slide2)
                }
            }

            currentRoute.route?.equals(routeName(Slide2.serializer())) == true -> {
                slide2State.value.handleClick()
            }
        }
    }

    fun onGlobalBack(
        currentRoute: NavDestination,
        navAction: (Any) -> Unit,
    ) {
        when {
            currentRoute.route?.equals(routeName(StartPage.serializer())) == true -> {}

            currentRoute.route?.equals(routeName(Slide1.serializer())) == true -> {
                if (slide1State.value.clickCounter.intValue != 0)
                    slide1State.value.reverseClick()
                else navAction(StartPage)
            }
        }
    }
}