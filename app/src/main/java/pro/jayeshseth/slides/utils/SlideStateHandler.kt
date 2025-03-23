package pro.jayeshseth.slides.utils

import androidx.compose.runtime.mutableIntStateOf

abstract class SlideStateHandler {
    val clickCounter = mutableIntStateOf(0)

    open fun handleForwardClick() {
        clickCounter.intValue++
        onForwardClick(clickCounter.intValue)
    }

    open fun handleReverseClick() {
        onReverseClick(clickCounter.intValue)
    }

    abstract fun onForwardClick(count: Int)
    abstract fun onReverseClick(count: Int)
}