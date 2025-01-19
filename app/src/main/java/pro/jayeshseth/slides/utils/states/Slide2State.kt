package pro.jayeshseth.slides.utils.states

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf

class Slide2State {
    var showSpringInfo = mutableStateOf(false)
        private set
    var showTweenInfo = mutableStateOf(false)
        private set
    var showFirstPoint = mutableStateOf(false)
        private set
    var showSecondPoint = mutableStateOf(false)
        private set
    var showThirdPoint = mutableStateOf(false)
        private set

    var clickCounter = mutableIntStateOf(0)

    fun handleClick() {
        clickCounter.intValue++
        when (clickCounter.intValue) {
            1 -> {
                showSpringInfo.value = true
                showTweenInfo.value = true
            }

            2 -> showFirstPoint.value = true
            3 -> showSecondPoint.value = true
            4 -> showThirdPoint.value = true
        }
    }

    fun reverseClick() {
//        clickCounter.intValue--
        when (clickCounter.intValue) {
            5 -> {
                clickCounter.intValue = 4
            }

            4 -> {
                if (showSecondPoint.value) showThirdPoint.value = false
                clickCounter.intValue= 3
            }

            3 -> {
                if (showFirstPoint.value) showSecondPoint.value = false
                clickCounter.intValue = 2
            }

            2 -> {
                showFirstPoint.value = false
                clickCounter.intValue = 1
            }

            1 -> {
                showSpringInfo.value = false
                showTweenInfo.value = false
                clickCounter.intValue = 0
            }

            else -> clickCounter.intValue = 5
        }
    }
}