package pro.jayeshseth.slides.utils.states

import pro.jayeshseth.slides.R

typealias ShaderPointsImage = Pair<Int?, Int?>

sealed class ShaderPoints(val point: String, val images: ShaderPointsImage) {
    data object CpuVsGPU : ShaderPoints("CPU vs GPU", Pair(R.raw.cpu, R.raw.gpu))
    data object ShaderVariants :
        ShaderPoints("Vertex, Geometry, Mesh, Fragment/ Pixel", Pair(R.raw.simulation, R.raw.blue_circle))

    data object ShadingLanguageVariants : ShaderPoints(
        "GLSL, Metal, HLSL...", Pair(R.raw.shadelang, null)
    )
    data object Agsl: ShaderPoints("AGSL", Pair(R.raw.agsl, null))
}