package pro.jayeshseth.slides.utils.states

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import pro.jayeshseth.slides.utils.Slide1TvChannels

class Slide1State {
    var swap = mutableStateOf(false)
        private set
    var showTvLayout = mutableStateOf(false)
        private set
    var currentTvChannel = mutableStateOf<Slide1TvChannels>(Slide1TvChannels.LOADING)
        private set
    var currentChalkBoardText = mutableStateListOf(Slide1TvChannels.LOADING.title)
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
            Slide1TvChannels.LOADING -> Slide1TvChannels.STATIC
            Slide1TvChannels.STATIC -> Slide1TvChannels.FOUNDATION
            Slide1TvChannels.FOUNDATION -> Slide1TvChannels.UX
            Slide1TvChannels.UX -> Slide1TvChannels.PERFORMANCE
            Slide1TvChannels.PERFORMANCE -> Slide1TvChannels.EXITING
            Slide1TvChannels.EXITING -> Slide1TvChannels.LOADING
        }

        val newText = when (currentTvChannel.value) {
            Slide1TvChannels.LOADING -> Slide1TvChannels.LOADING.title
            Slide1TvChannels.STATIC -> Slide1TvChannels.STATIC.title
            Slide1TvChannels.FOUNDATION -> Slide1TvChannels.FOUNDATION.title
            Slide1TvChannels.UX -> Slide1TvChannels.UX.title
            Slide1TvChannels.PERFORMANCE -> Slide1TvChannels.PERFORMANCE.title
            Slide1TvChannels.EXITING -> Slide1TvChannels.EXITING.title
        }
        currentChalkBoardText.add(newText)

        // Keep click count at 3 to continue cycling
        if (currentTvChannel.value != Slide1TvChannels.EXITING) clickCounter.intValue = 2
    }

}
