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

    @Test
    fun testGeneratesPerimeter1() {
        val expectedCoordinates = mutableSetOf(
            Coordinate(0, 1),
            Coordinate(1, 0),
            Coordinate(-1, 0),
            Coordinate(0, -1),
        )

        val sensorPosition = Coordinate(0, 0)
        val beaconPosition = Coordinate(0, 0)
        val sensor = Sensor(sensorPosition, beaconPosition)

        assertEquals(expectedCoordinates, sensor.getPerimeter())
    }

    @Test
    fun testGeneratesPerimeter2() {
        val expectedCoordinates = mutableSetOf(
            Coordinate(0, 2),
            Coordinate(1, 1),
            Coordinate(2, 0),
            Coordinate(1, -1),
            Coordinate(0, -2),
            Coordinate(-1, -1),
            Coordinate(-2, 0),
            Coordinate(-1, 1),
        )

        val sensorPosition = Coordinate(0, 0)
        val beaconPosition = Coordinate(1, 0)
        val sensor = Sensor(sensorPosition, beaconPosition)

        assertEquals(expectedCoordinates, sensor.getPerimeter())
    }

    @Test
    fun testGeneratesPerimeter3() {
        val expectedCoordinates = mutableSetOf(
            Coordinate(0, 3),
            Coordinate(1, 2),
            Coordinate(2, 1),
            Coordinate(3, 0),
            Coordinate(2, -1),
            Coordinate(1, -2),
            Coordinate(0, -3),
            Coordinate(-1, -2),
            Coordinate(-2, -1),
            Coordinate(-3, 0),
            Coordinate(-2, 1),
            Coordinate(-1, 2),
        )

        val sensorPosition = Coordinate(0, 0)
        val beaconPosition = Coordinate(2, 0)
        val sensor = Sensor(sensorPosition, beaconPosition)

        assertEquals(expectedCoordinates, sensor.getPerimeter())
    }
}