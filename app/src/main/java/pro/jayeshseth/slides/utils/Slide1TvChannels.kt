package pro.jayeshseth.slides.utils

sealed class Slide1TvChannels(val title: String?) {
    data object LOADING : Slide1TvChannels(null)
    data object STATIC : Slide1TvChannels("Not Static")
    data object FOUNDATION : Slide1TvChannels("Foundation")
    data object UX : Slide1TvChannels("Find UX Issues")
    data object PERFORMANCE : Slide1TvChannels("Find Performance Blockers")
    data object EXITING : Slide1TvChannels(null)
}