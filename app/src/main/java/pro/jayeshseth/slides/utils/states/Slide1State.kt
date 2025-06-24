package pro.jayeshseth.slides.utils.states

import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

class Slide1State {
    var swap = mutableStateOf(false)
        private set
    var showTvLayout = mutableStateOf(false)
        private set
    var currentTvChannel = mutableStateOf<Slide1TvChannels>(Slide1TvChannels.LOADING)
        private set
    var currentChalkBoardText = mutableStateListOf(Slide1TvChannels.LOADING.title)
    var clickCounter = mutableIntStateOf(0)

    fun handleClick() {
        clickCounter.intValue++
        when (clickCounter.intValue) {
            1 -> swap.value = true
            2 -> if (swap.value) showTvLayout.value = true
            3 -> if (showTvLayout.value) updateTvContent()
        }

    }

    fun reverseClick() {
        Log.d("click counter", "${clickCounter.intValue}")
        when (clickCounter.intValue) {
            3 -> {
                if (showTvLayout.value) {
                    if (currentTvChannel.value != Slide1TvChannels.LOADING) {
                        reverseUpdateTvContent()
                    } else {
                        showTvLayout.value = false
                        clickCounter.intValue = 1
                    }
                }
            }

            2 -> {
                if (swap.value && !showTvLayout.value) {
                    showTvLayout.value = false
                    clickCounter.intValue = 1
                }

                if (showTvLayout.value && currentTvChannel.value != Slide1TvChannels.LOADING) {
                    reverseUpdateTvContent()
                } else {
                    showTvLayout.value = false
                    clickCounter.intValue = 1
                }
            }

            1 -> {
                swap.value = false
                clickCounter.intValue = 0
            }
        }
    }

    fun reset() {
        swap.value = false
        showTvLayout.value = false
        currentTvChannel.value = Slide1TvChannels.LOADING
        currentChalkBoardText.clear()
        currentChalkBoardText.add(Slide1TvChannels.LOADING.title)
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

    private fun reverseUpdateTvContent() {
        currentTvChannel.value = when (currentTvChannel.value) {
            Slide1TvChannels.EXITING -> Slide1TvChannels.PERFORMANCE
            Slide1TvChannels.PERFORMANCE -> Slide1TvChannels.UX
            Slide1TvChannels.UX -> Slide1TvChannels.FOUNDATION
            Slide1TvChannels.FOUNDATION -> Slide1TvChannels.STATIC
            Slide1TvChannels.STATIC -> Slide1TvChannels.LOADING
            Slide1TvChannels.LOADING -> Slide1TvChannels.LOADING
        }

        if (currentChalkBoardText.isNotEmpty()) currentChalkBoardText.removeAt(currentChalkBoardText.lastIndex)
    }
}
