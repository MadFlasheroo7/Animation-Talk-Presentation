package pro.jayeshseth.slides.utils.states

import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf

class Slide3State {
    var showSharedTransition = mutableStateOf(true)
        private set

    var showPoints = mutableStateOf(false)
        private set

    var showPoint1 = mutableStateOf(false)
        private set

    var showSharedPoints = mutableStateOf(false)
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
                if (showSharedPoints.value.not()) updateLookaheadPoints()
            }

            4 -> {
                showSharedPoints.value = true
                if (showSharedPoints.value) upgradeSharedTransitionPoints()
            }
        }
    }

    fun handleReverseClick() {
        when (clickCounter.intValue) {
            3 -> {
                if (
                    lookaheadPoints.value == LookaheadPoints.SharedTransition &&
                    sharedTransitionPoints.value == SharedTransitionPoints.SharedTransitionScope
                ) {
                    reverseLookaheadPoints()
                } else {
                    if (sharedTransitionPoints.value == SharedTransitionPoints.SharedBounds)
                        showSharedPoints.value = false

                    reverseSharedTransition()
                }
            }

            2 -> {
                if (lookaheadPoints.value == LookaheadPoints.WhatIsLookahead) {
                    showPoints.value = false
                    clickCounter.intValue = 1
                    Log.d("click counter reverse", "${clickCounter.intValue}")
                } else {
                    reverseLookaheadPoints()
                }
            }

            1 -> {
                showSharedTransition.value = true
                clickCounter.intValue = 0
            }
        }
    }

    fun upgradeSharedTransitionPoints() {
        sharedTransitionPoints.value = when (sharedTransitionPoints.value) {
            SharedTransitionPoints.SharedTransitionScope -> SharedTransitionPoints.SharedBounds
            SharedTransitionPoints.SharedBounds -> SharedTransitionPoints.SharedElements
            SharedTransitionPoints.SharedElements -> SharedTransitionPoints.OverlayAndClip
            SharedTransitionPoints.OverlayAndClip -> SharedTransitionPoints.ZIndex
            SharedTransitionPoints.ZIndex -> SharedTransitionPoints.ScaleToBounds
            SharedTransitionPoints.ScaleToBounds -> SharedTransitionPoints.RemeasureToBounds
            SharedTransitionPoints.RemeasureToBounds -> SharedTransitionPoints.SharedTransitionScope
        }
        if (sharedTransitionPoints.value != SharedTransitionPoints.RemeasureToBounds)
            clickCounter.intValue = 3
    }

    fun updateLookaheadPoints() {
        lookaheadPoints.value = when (lookaheadPoints.value) {
            LookaheadPoints.WhatIsLookahead -> LookaheadPoints.PreCalculateLayout
            LookaheadPoints.PreCalculateLayout -> LookaheadPoints.WhyLookahead
            LookaheadPoints.WhyLookahead -> LookaheadPoints.SharedTransition
            LookaheadPoints.SharedTransition -> LookaheadPoints.SharedTransition
        }
        if (lookaheadPoints.value != LookaheadPoints.SharedTransition) {
            clickCounter.intValue = 2
        }
    }

    fun reverseSharedTransition() {
        sharedTransitionPoints.value = when (sharedTransitionPoints.value) {
            SharedTransitionPoints.RemeasureToBounds -> SharedTransitionPoints.ScaleToBounds
            SharedTransitionPoints.ScaleToBounds -> SharedTransitionPoints.ZIndex
            SharedTransitionPoints.ZIndex -> SharedTransitionPoints.OverlayAndClip
            SharedTransitionPoints.OverlayAndClip -> SharedTransitionPoints.SharedElements
            SharedTransitionPoints.SharedElements -> SharedTransitionPoints.SharedBounds
            SharedTransitionPoints.SharedBounds -> SharedTransitionPoints.SharedTransitionScope
            SharedTransitionPoints.SharedTransitionScope -> SharedTransitionPoints.SharedTransitionScope
        }
        if (sharedTransitionPoints.value != SharedTransitionPoints.SharedTransitionScope) {
            clickCounter.intValue = 3
        }
    }

    fun reverseLookaheadPoints() {
        lookaheadPoints.value = when (lookaheadPoints.value) {
            LookaheadPoints.SharedTransition -> LookaheadPoints.WhyLookahead
            LookaheadPoints.WhyLookahead -> LookaheadPoints.PreCalculateLayout
            LookaheadPoints.PreCalculateLayout -> LookaheadPoints.WhatIsLookahead
            LookaheadPoints.WhatIsLookahead -> LookaheadPoints.WhatIsLookahead
        }
        if (lookaheadPoints.value != LookaheadPoints.WhatIsLookahead) {
            clickCounter.intValue = 2
        }
    }
}