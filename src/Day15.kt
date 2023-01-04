import kotlin.math.abs

internal data class Coordinate(val x: Int, val y: Int)

internal class Sensor(val position: Coordinate, val beacon: Coordinate) {

    val radius = abs(position.x - beacon.x) + abs(position.y - beacon.y)

    fun has(point: Coordinate): Boolean {
        val distToPoint = abs(this.position.x - point.x) + abs(this.position.y - point.y)
        return distToPoint <= radius
    }
}

fun part1(input: List<String>, xRange: IntRange, targetY: Int): Int {
    // Parse min and max X of beacons to create an x range for target y value
    // For each x value, check if it appears in any sensor field
    // Increment each time

    val sensors = mutableListOf<Sensor>()
    val beacons = mutableListOf<Coordinate>()

    input.forEach {
        val (rawSensor, rawBeacon) = it.split(":")
        val sensorLocation = Coordinate(
            rawSensor.split(",")[0].toInt(),
            rawSensor.split(",")[1].toInt()
        )
        val beaconLocation = Coordinate(
            rawBeacon.split(",")[0].toInt(),
            rawBeacon.split(",")[1].toInt()
        )
        sensors.add(Sensor(sensorLocation, beaconLocation))
        beacons.add(beaconLocation)
    }

    for (sensor in sensors) {

    }

    return xRange.sumOf { currentX ->
        val currentCoordinate = Coordinate(currentX, targetY)
        val inSensorArea = sensors.any { it.has(currentCoordinate) }
        val notBeacon = beacons.all { beacon -> beacon != currentCoordinate }
        if (inSensorArea && notBeacon) 1 as Int else 0
    }
}

fun part2(input: List<String>) {
    // Go around perimeter + 1 of each sensor area
    // Check if each point is in target region, not a beacon and is not in any other sensor area

}

fun main() {
    val testInput = readInput("Day15_test")
    part1(testInput, -4..27, 10).println()

    val input = readInput("Day15")
    part1(input, -10_000_000..10_000_000, 2_000_000).println()
}
