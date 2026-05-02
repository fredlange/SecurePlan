package se.secureplan.app.feature.calculations

object Ssf130Calculator {

    data class ComponentLoad(
        val name: String,
        val count: Int,
        val standbyMaEach: Float,
        val alarmMaEach: Float,
        val voltageV: Float
    )

    data class Ssf130Result(
        val totalStandbyMa: Float,
        val totalAlarmMa: Float,
        /** Alt 1: 60h standby + 0.5h alarm, divided by 0.8 efficiency */
        val batteryCapacityAh_Alt1: Float,
        /** Alt 2: 12h standby + 0.05h (3 min) alarm, divided by 0.8 efficiency */
        val batteryCapacityAh_Alt2: Float,
        /** Recommended = max(Alt1, Alt2) */
        val recommendedBatteryAh: Float,
        /** I_alarm × 1.25 safety margin, in Ampere */
        val powerSupplyCurrentA: Float,
        val powerSupplyVoltage: Float,
        val componentLoads: List<ComponentLoad>
    )

    fun calculate(components: List<ComponentLoad>): Ssf130Result {
        val totalStandby = components.sumOf { it.standbyMaEach * it.count.toDouble() }.toFloat()
        val totalAlarm   = components.sumOf { it.alarmMaEach  * it.count.toDouble() }.toFloat()

        // SSF 130 Klass 2 battery dimensioning
        val bat1 = ((totalStandby * 60f) + (totalAlarm * 0.5f))  / (1000f * 0.8f)
        val bat2 = ((totalStandby * 12f) + (totalAlarm * 0.05f)) / (1000f * 0.8f)
        val recommended = maxOf(bat1, bat2)

        val psCurrentA = (totalAlarm / 1000f) * 1.25f

        return Ssf130Result(
            totalStandbyMa          = totalStandby,
            totalAlarmMa            = totalAlarm,
            batteryCapacityAh_Alt1  = bat1,
            batteryCapacityAh_Alt2  = bat2,
            recommendedBatteryAh    = recommended,
            powerSupplyCurrentA     = psCurrentA,
            powerSupplyVoltage      = 13.8f,
            componentLoads          = components
        )
    }
}
