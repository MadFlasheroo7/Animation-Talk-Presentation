package pro.jayeshseth.slides.utils.states

import androidx.compose.runtime.mutableStateOf
import pro.jayeshseth.slides.utils.SlideStateHandler

enum class Slide4Layouts {
    NONE, TITLE, WHAT_IS_SHADER, AGSL
}

class Slide4SlideState : SlideStateHandler() {
    var showBackground = mutableStateOf(false)
        private set
    var shaderPointsLayout = mutableStateOf(Slide4Layouts.NONE)
        private set
    var shaderPoint = mutableStateOf<ShaderPoints?>(null)
        private set

    var agslPoint = mutableStateOf<AgslPoints?>(null)
        private set

    override fun onForwardClick(count: Int) {
        when (count) {
            1 -> {
                showBackground.value = true
                shaderPointsLayout.value = Slide4Layouts.TITLE
            }
            2 -> { shaderPointsLayout.value = Slide4Layouts.WHAT_IS_SHADER }
            3 -> { if (shaderPointsLayout.value == Slide4Layouts.WHAT_IS_SHADER) updateShaderPoints() }
            4 -> { shaderPointsLayout.value = Slide4Layouts.AGSL }
            5 -> { if (shaderPointsLayout.value == Slide4Layouts.AGSL) updateAgslPoints() }
        }
    }

    override fun onReverseClick(count: Int) {
        when (count) {
            2 -> {
                clickCounter.intValue = 1
            }

            1 -> {
                showBackground.value = false
                clickCounter.intValue = 0
            }
        }
    }

    private fun updateShaderPoints() {
        shaderPoint.value = when (shaderPoint.value) {
            ShaderPoints.CpuVsGPU -> ShaderPoints.ShaderVariants
            ShaderPoints.ShaderVariants -> ShaderPoints.ShadingLanguageVariants
            ShaderPoints.ShadingLanguageVariants -> ShaderPoints.Agsl
            ShaderPoints.Agsl -> null
            null -> ShaderPoints.CpuVsGPU
        }
        if (shaderPoint.value != null && shaderPoint.value != ShaderPoints.Agsl)
            clickCounter.intValue = 2
    }

    private fun updateAgslPoints() {
        agslPoint.value = when (agslPoint.value) {
            AgslPoints.RuntimeShaderApi -> AgslPoints.GraphicsLayer
            AgslPoints.GraphicsLayer -> AgslPoints.WhyAGSL
            AgslPoints.WhyAGSL -> AgslPoints.WhyAGSL2
            AgslPoints.WhyAGSL2 -> AgslPoints.Qualifiers
            AgslPoints.Qualifiers -> AgslPoints.ColorSpace
            AgslPoints.ColorSpace -> AgslPoints.WhyNot
            AgslPoints.WhyNot -> AgslPoints.WhyNot2
            AgslPoints.WhyNot2 -> null
            null -> AgslPoints.RuntimeShaderApi
        }
//        if (agslPoint.value != null && agslPoint.value != AgslPoints.WhyNot)
            clickCounter.intValue = 4
    }
}