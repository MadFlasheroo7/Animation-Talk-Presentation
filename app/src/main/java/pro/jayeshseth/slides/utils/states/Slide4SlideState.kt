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

            2 -> {
                shaderPointsLayout.value = Slide4Layouts.WHAT_IS_SHADER
            }

            3 -> {
                if (shaderPointsLayout.value == Slide4Layouts.WHAT_IS_SHADER) updateShaderPoints()
            }

            4 -> {
                shaderPointsLayout.value = Slide4Layouts.AGSL
            }

            5 -> {
                if (shaderPointsLayout.value == Slide4Layouts.AGSL) updateAgslPoints()
            }
        }
    }

    override fun onReverseClick(count: Int) {
        when (count) {
            5 -> {
                if (agslPoint.value == AgslPoints.RuntimeShaderApi) {
                    shaderPointsLayout.value = Slide4Layouts.AGSL
                    agslPoint.value = null
                    clickCounter.intValue = 4
                } else if (shaderPointsLayout.value == Slide4Layouts.AGSL) {
                    reverseAgslPoints()
                }
            }

            4 -> {
                if (shaderPoint.value == ShaderPoints.Agsl) {
                    shaderPointsLayout.value = Slide4Layouts.WHAT_IS_SHADER
                    shaderPoint.value = ShaderPoints.ShadingLanguageVariants
                }
                clickCounter.intValue = 3
            }

            3 -> {
                if (shaderPointsLayout.value == Slide4Layouts.WHAT_IS_SHADER) reverseShaderPoints()
            }

            2 -> { // back from shader points to title
                shaderPointsLayout.value = Slide4Layouts.TITLE
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
            AgslPoints.WhyNot2 -> AgslPoints.WhyNot2
            null -> AgslPoints.RuntimeShaderApi
        }
        if (agslPoint.value != null && agslPoint.value != AgslPoints.WhyNot2)
            clickCounter.intValue = 4
    }

    private fun reverseShaderPoints() {
        shaderPoint.value = when (shaderPoint.value) {
            ShaderPoints.Agsl -> ShaderPoints.ShadingLanguageVariants
            ShaderPoints.ShadingLanguageVariants -> ShaderPoints.ShaderVariants
            ShaderPoints.ShaderVariants -> ShaderPoints.CpuVsGPU
            ShaderPoints.CpuVsGPU -> null
            null -> ShaderPoints.Agsl
        }
        if (shaderPoint.value == null) clickCounter.intValue = 2
    }

    private fun reverseAgslPoints() {
        agslPoint.value = when (agslPoint.value) {
            AgslPoints.WhyNot2 -> AgslPoints.WhyNot
            AgslPoints.WhyNot -> AgslPoints.ColorSpace
            AgslPoints.ColorSpace -> AgslPoints.Qualifiers
            AgslPoints.Qualifiers -> AgslPoints.WhyAGSL2
            AgslPoints.WhyAGSL2 -> AgslPoints.WhyAGSL
            AgslPoints.WhyAGSL -> AgslPoints.GraphicsLayer
            AgslPoints.GraphicsLayer -> AgslPoints.RuntimeShaderApi
            AgslPoints.RuntimeShaderApi -> null
            null -> AgslPoints.WhyNot2
        }
        if (agslPoint.value == null) clickCounter.intValue = 4
    }
}