package pro.jayeshseth.slides.utils.states

sealed class LookaheadPoints(val point: String) {
    data object WhatIsLookahead: LookaheadPoints("LookaheadScope provides info about child element bounds in parent layout.")
    data object PreCalculateLayout: LookaheadPoints("")
    data object SharedTransition: LookaheadPoints("Shared Transition is a \"From\" to \"To\" animation between elements and bounds api.")
}