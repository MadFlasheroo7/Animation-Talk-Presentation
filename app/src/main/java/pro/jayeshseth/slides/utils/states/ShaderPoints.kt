package pro.jayeshseth.slides.utils.states

import pro.jayeshseth.slides.R

typealias ShaderPointsImage = Pair<Int?, Int?>

sealed class ShaderPoints(val point: String, val images: ShaderPointsImage) {
    data object CpuVsGPU : ShaderPoints("CPU vs GPU", ShaderPointsImage(R.raw.cpu, R.raw.gpu))
    data object ShaderVariants :
        ShaderPoints("Vertex, Geometry, Mesh, Fragment/ Pixel", ShaderPointsImage(R.raw.simulation, R.raw.blue_circle))

    data object ShadingLanguageVariants : ShaderPoints(
        "GLSL, Metal, HLSL...", ShaderPointsImage(R.raw.shadelang, null)
    )
    data object Agsl: ShaderPoints("AGSL", ShaderPointsImage(R.raw.agsl, null))
}