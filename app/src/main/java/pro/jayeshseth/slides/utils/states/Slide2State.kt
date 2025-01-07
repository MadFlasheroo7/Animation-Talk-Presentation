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

    //    var switchContent = mutableStateOf(false)
//        private set
    var currentContent = mutableStateOf<Slide2ContentType?>(null)

    private var clickCounter = mutableIntStateOf(0)

    fun handleClick() {
        clickCounter.intValue++
        when (clickCounter.intValue) {
            1 -> {
                showSpringInfo.value = true
                showTweenInfo.value = true
                currentContent.value = Slide2ContentType.Points
            }

            2 -> showFirstPoint.value = true
            3 -> showSecondPoint.value = true
            4 -> showThirdPoint.value = true
            5 -> currentContent.value = Slide2ContentType.List
        }
    }
}


enum class Slide2ContentType {
    Points, List
}