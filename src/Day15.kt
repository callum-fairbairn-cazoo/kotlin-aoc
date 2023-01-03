import kotlin.math.abs

private data class Coordinate(val x: Int, val y: Int)

fun getTargetRow(input: List<String>): MutableMap<Int, String> {
    val beaconXValues = input.map { row ->
        row.split(":")[1].split(",")[0].toInt()
    }
    val xMin = beaconXValues.min()
    val xMax = beaconXValues.max()
    val targetRow = mutableMapOf<Int, String>()
    for (x in xMin..xMax) {
        targetRow[x] = "."
    }
    return targetRow
}

fun part1(input: List<String>, targetY: Int): Int {
    // Parse min and max X of beacons and create a target row from xMin to xMax
    // This row can be an array of "."

    // For each line of input, work out Manhattan distance from sensor to beacon
    // Then work out if sensor range overlaps with target row
    // If so, flip overlapping indices to "#"

    // Return remaining number of "."s in target row
    val targetRow = getTargetRow(input)
    val beaconLocations = mutableListOf<Coordinate>()

    // Add sensor overflows and store beacons in beaconLocations
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
        // store beacon location
        beaconLocations.add(beaconLocation)

        val manhattanDistance = abs(sensorLocation.x - beaconLocation.x) +
                abs(sensorLocation.y - beaconLocation.y)
        val overflow =
            if (sensorLocation.y > targetY) targetY - (sensorLocation.y - manhattanDistance)
            else (sensorLocation.y + manhattanDistance) - targetY
        if (overflow > -1) {
            for (x in (sensorLocation.x - overflow)..(sensorLocation.x + overflow)) {
                targetRow[x] = "#"
            }
        }
    }

    // Add beacons to row
    beaconLocations.forEach { if (it.y == targetY) targetRow[it.x] = "B" }

    return targetRow.values.fold(0) { acc, current -> if (current == "#") acc + 1 else acc }
}

fun part2(input: List<String>) {
    // Go around perimeter + 1 of each sensor area
    // Check if each point is not a beacon and is not in any other sensor area

}

fun main() {
    val testInput = readInput("Day15_test")
//    part1(testInput, 10).println()

    val input = readInput("Day15")
    part1(input, 2000000).println()
}
