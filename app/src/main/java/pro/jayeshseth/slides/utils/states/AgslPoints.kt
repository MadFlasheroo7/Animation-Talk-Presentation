package pro.jayeshseth.slides.utils.states

import pro.jayeshseth.slides.R

sealed class AgslPoints(val point: String, val images: ShaderPointsImage) {
    data object RuntimeShaderApi : AgslPoints("Runtime Shader API\n SKSL & GLSL.", ShaderPointsImage(R.raw.cpu, R.raw.gpu))
    data object GraphicsLayer : AgslPoints("Render Node\n alpha, scale, composition strategy.", ShaderPointsImage(R.raw.simulation, R.raw.blue_circle))
    data object WhyAGSL : AgslPoints("Modifys the visual of the component, ripple, smoother animation...", ShaderPointsImage(R.raw.simulation, R.raw.blue_circle))
    data object WhyAGSL2 : AgslPoints("why AGSL", ShaderPointsImage(R.raw.simulation, R.raw.blue_circle))
    data object Qualifiers : AgslPoints("Qualifiers: In, Out, InOut", ShaderPointsImage(R.raw.simulation, R.raw.blue_circle))

    data object ColorSpace : AgslPoints(
        "Color Space", ShaderPointsImage(R.raw.shadelang, null)
    )
    data object WhyNot: AgslPoints("a13 or OpenGL (ES),\n Requires Heavy Testing on low end hardware", ShaderPointsImage(R.raw.agsl, null))
    data object WhyNot2: AgslPoints("Hard To Debug and Optimize", ShaderPointsImage(R.raw.agsl, null))
}