package pro.jayeshseth.slides

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDestination
import pro.jayeshseth.slides.navigation.Slide1
import pro.jayeshseth.slides.navigation.Slide2
import pro.jayeshseth.slides.navigation.Slide3
import pro.jayeshseth.slides.navigation.Slide4
import pro.jayeshseth.slides.navigation.StartPage
import pro.jayeshseth.slides.utils.Slide1TvChannels
import pro.jayeshseth.slides.utils.states.Slide1State
import pro.jayeshseth.slides.utils.states.Slide2State
import pro.jayeshseth.slides.utils.states.Slide3State
import pro.jayeshseth.slides.utils.states.Slide4SlideState

class GlobalNavigatorViewModel : ViewModel() {
    val slide1State = mutableStateOf(Slide1State())
    val slide2State = mutableStateOf(Slide2State())
    val slide3State = mutableStateOf(Slide3State())
    val slide4State = mutableStateOf(Slide4SlideState())

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
                if (slide2State.value.showThirdPoint.value) {
                    navAction(Slide3)
                } else {
                    slide2State.value.handleClick()
                }
            }

            currentRoute.route?.equals(routeName(Slide3.serializer())) == true -> {
                slide3State.value.handleClick()
            }

            currentRoute.route?.equals(routeName(Slide4.serializer())) == true -> {
                slide4State.value.handleForwardClick()
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

            currentRoute.route?.equals(routeName(Slide2.serializer())) == true -> {
                if (slide2State.value.clickCounter.intValue != 0)
                    slide2State.value.reverseClick()
                else navAction(Slide1)
            }

            currentRoute.route?.equals(routeName(Slide3.serializer())) == true -> {
                if (slide3State.value.clickCounter.intValue != 0)
                    slide3State.value.handleReverseClick()
                else navAction(Slide2)
            }
            currentRoute.route?.equals(routeName(Slide4.serializer())) == true -> {
                if (slide4State.value.clickCounter.intValue != 0)
                    slide4State.value.handleReverseClick()
                else navAction(Slide3)
            }
        }
    }
}