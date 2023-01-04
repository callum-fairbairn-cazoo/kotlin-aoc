import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day15Test {
    @Test
    fun testPointInSensorArea() {
        val point = Coordinate(0, 0)
        val sensorPosition = Coordinate(0, 0)
        val beaconPosition = Coordinate(1, 0)
        val sensor = Sensor(sensorPosition, beaconPosition)
        assertEquals(true, sensor.has(point))
    }

    @Test
    fun testPointOutsideSensorArea() {
        val point = Coordinate(2, 2)
        val sensorPosition = Coordinate(0, 0)
        val beaconPosition = Coordinate(1, 0)
        val sensor = Sensor(sensorPosition, beaconPosition)
        assertEquals(false, sensor.has(point))
    }
}