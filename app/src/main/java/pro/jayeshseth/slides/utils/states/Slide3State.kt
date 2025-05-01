package pro.jayeshseth.slides.utils.states

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf

class Slide3State {
    var showSharedTransition = mutableStateOf(true)
        private set

    var showPoints = mutableStateOf(false)
        private set

    var showPoint1 = mutableStateOf(false)
        private set
    var clickCounter = mutableIntStateOf(0)

    var lookaheadPoints =
        mutableStateOf<LookaheadPoints>(LookaheadPoints.WhatIsLookahead)

    var sharedTransitionPoints =
        mutableStateOf<SharedTransitionPoints>(SharedTransitionPoints.SharedTransitionScope)
    fun handleClick() {
        clickCounter.intValue++
        when (clickCounter.intValue) {
            1 -> showSharedTransition.value = false
            2 -> showPoints.value = true
            3 -> {
                updateLookaheadPoints()
            }
            4 -> upgradeShredTransitionPoints()
        }
    }

    fun handleReverseClick() {
//        clickCounter.intValue--
        when (clickCounter.intValue) {
            3 -> {
                showPoint1.value = false
                clickCounter.intValue = 2
            }

            2 -> {
                showPoints.value = false
                clickCounter.intValue = 1
            }

            1 -> {
                showSharedTransition.value = true
                clickCounter.intValue = 0
            }
        }
    }

    fun upgradeShredTransitionPoints() {

    }

    fun updateLookaheadPoints() {
        lookaheadPoints.value = when (lookaheadPoints.value) {
            LookaheadPoints.WhatIsLookahead ->
                LookaheadPoints.PreCalculateLayout

            LookaheadPoints.PreCalculateLayout ->
                LookaheadPoints.SharedTransition

            LookaheadPoints.SharedTransition -> LookaheadPoints.WhatIsLookahead
        }
        if (lookaheadPoints.value != LookaheadPoints.SharedTransition) clickCounter.intValue = 2

    }
}