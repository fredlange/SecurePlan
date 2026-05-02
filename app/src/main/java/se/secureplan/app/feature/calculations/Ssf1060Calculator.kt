package se.secureplan.app.feature.calculations

object Ssf1060Calculator {

    data class CameraLoad(
        val name: String,
        val count: Int,
        val powerWattEach: Float,
        val isPoe: Boolean
    )

    data class NvrLoad(
        val name: String,
        val powerWatt: Float
    )

    data class Ssf1060Result(
        val totalCameraWatt: Float,
        val totalNvrWatt: Float,
        val totalSystemWatt: Float,
        /** UPS: 4h backup at total system load, 12V battery, 80% efficiency */
        val upsBatteryAh: Float,
        /** Sum of watts for PoE cameras only */
        val poeBudgetWatt: Float,
        val cameraLoads: List<CameraLoad>,
        val nvrLoad: NvrLoad?
    )

    fun calculate(cameras: List<CameraLoad>, nvr: NvrLoad?): Ssf1060Result {
        val camWatt  = cameras.sumOf { it.powerWattEach * it.count.toDouble() }.toFloat()
        val nvrWatt  = nvr?.powerWatt ?: 0f
        val total    = camWatt + nvrWatt

        // UPS: 4h backup, 12V system, 80% battery efficiency
        val upsAh    = (total * 4f) / (12f * 0.8f)

        val poeBudget = cameras
            .filter { it.isPoe }
            .sumOf { it.powerWattEach * it.count.toDouble() }
            .toFloat()

        return Ssf1060Result(
            totalCameraWatt = camWatt,
            totalNvrWatt    = nvrWatt,
            totalSystemWatt = total,
            upsBatteryAh    = upsAh,
            poeBudgetWatt   = poeBudget,
            cameraLoads     = cameras,
            nvrLoad         = nvr
        )
    }
}
