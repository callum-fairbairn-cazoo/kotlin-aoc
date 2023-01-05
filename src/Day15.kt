import kotlin.math.abs

internal data class Coordinate(val x: Int, val y: Int)

internal class Sensor(val position: Coordinate, val beacon: Coordinate) {

    fun getPerimeter(): MutableSet<Coordinate> {
        val perimeter = mutableSetOf<Coordinate>()
        for (i in 0..radius) {
            val offset = radius + 1
            perimeter.add(Coordinate(position.x + offset - i, position.y - i))
            perimeter.add(Coordinate(position.x + i, position.y + offset - i))
            perimeter.add(Coordinate(position.x - offset + i, position.y + i))
            perimeter.add(Coordinate(position.x - i, position.y - offset + i))
        }
        return perimeter
    }

    val radius = abs(position.x - beacon.x) + abs(position.y - beacon.y)

    fun has(point: Coordinate): Boolean {
        val distToPoint = abs(this.position.x - point.x) + abs(this.position.y - point.y)
        return distToPoint <= radius
    }
}

private fun parseInput(input: List<String>): Pair<MutableList<Sensor>, MutableList<Coordinate>> {
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
    return Pair(sensors, beacons)
}

internal fun getXRange(sensors: MutableList<Sensor>): IntRange {
    return sensors.minOf { it.position.x - it.radius }..sensors.maxOf { it.position.x + it.radius }
}

fun part1(input: List<String>, targetY: Int): Int {
    val (sensors, beacons) = parseInput(input)
    val xRange = getXRange(sensors)

    return xRange.sumOf { currentX ->
        val currentCoordinate = Coordinate(currentX, targetY)
        val inSensorArea = sensors.any { it.has(currentCoordinate) }
        val notBeacon = beacons.all { beacon -> beacon != currentCoordinate }
        if (inSensorArea && notBeacon) 1 as Int else 0
    }
}

fun part2(input: List<String>, xRange: IntRange, yRange: IntRange): Long {
    // Go around perimeter + 1 of each sensor area
    // Check if each point is in target region, not a beacon and is not in any other sensor area

    val (sensors, beacons) = parseInput(input)
    for (sensor in sensors) {
        for (point in sensor.getPerimeter()) {
            val inTargetArea = point.x in xRange && point.y in yRange
            val inSensorArea = sensors.any { it.has(point) }
            val isBeacon = beacons.any { beacon -> beacon == point }
            if (inTargetArea && !inSensorArea && !isBeacon) {
                println(point)
                return point.x.toLong() * 4_000_000 + point.y.toLong()
            }
        }
    }

    return 0
}

fun main() {
    val testInput = readInput("Day15_test")
//    part1(testInput, 10).println()
    part2(testInput, 0..20, 0..20).println()

    val input = readInput("Day15")
//    part1(input, 2_000_000).println()
    part2(input, 0..4000000, 0..4000000).println()
}
