package pro.jayeshseth.slides.utils.states

sealed class SharedTransitionPoints(val point: String) {
    data object SharedTransitionScope :
        SharedTransitionPoints("SharedTransitionScope is the layer that provides the shared transition functionalities and modifiers.")

    data object SharedBounds :
        SharedTransitionPoints("Shared Bounds transition uses bounds/size for transition, for \"from\" to \"to\" animation.")

    data object SharedElements :
        SharedTransitionPoints("Shared Elements is used when shared content are same.")

    data object OverlayAndClip :
        SharedTransitionPoints("Overlay can be treated as separate pane for rendering animation and clipping even those not part of animation.")

    data object ZIndex :
        SharedTransitionPoints("ZIndex helps us manage the elevation of the animation so they don't get rendered below or above other composeables.\n'.renderInSharedTransitionScopeOverlay()':)")

    data object ScaleToBounds :
        SharedTransitionPoints("Is Default & takes enter & exit animation")

    data object RemeasureToBounds :
        SharedTransitionPoints("Remeasure the child layout of shared bounds. Remeasurement is triggered every sec might cause performance issues.")
}