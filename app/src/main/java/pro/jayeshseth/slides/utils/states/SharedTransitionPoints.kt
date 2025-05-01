package pro.jayeshseth.slides.utils.states

sealed class SharedTransitionPoints(val point: String) {
    data object SharedTransitionScope: SharedTransitionPoints("")
    data object SharedBounds: SharedTransitionPoints("")
    data object SharedElements: SharedTransitionPoints("")
    data object Overlay: SharedTransitionPoints("")
    data object ZIndex: SharedTransitionPoints("")
    data object ResizeModes: SharedTransitionPoints("")
}