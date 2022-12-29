object Characters {
    val WALL = "#"
    val GRAIN = "o"
}

enum class EndPoint { START, BOTTOM }

fun simulateSand(input: List<String>, endPoint: EndPoint): Int {
    fun getYLimit(): Int {
        return input.flatMap { line -> line.split(" -> ").map { it.split(",")[1].toInt() } }.fold(0) { acc, currentY ->
            if (currentY > acc) currentY else acc
        } + 2
    }

    fun getXLimits(): Pair<Int, Int> {
        val xMin = input.flatMap { line -> line.split(" -> ").map { it.split(",")[0].toInt() } }.min()
        val xMax = input.flatMap { line -> line.split(" -> ").map { it.split(",")[0].toInt() } }.max()
        return Pair(xMin, xMax)
    }

    val yLimit = getYLimit()
    val xLimits = getXLimits()

    data class Coordinate(val x: Int, val y: Int) {
        private val nextIsValid = y + 1 < yLimit + 1
        val down: () -> Coordinate? = { if (nextIsValid) Coordinate(x, y + 1) else null }
        val downLeft: () -> Coordinate? = { if (nextIsValid) Coordinate(x - 1, y + 1) else null }
        val downRight: () -> Coordinate? = { if (nextIsValid) Coordinate(x + 1, y + 1) else null }
    }

    fun parseInput(): HashMap<Coordinate, String> {
        val hashMap: HashMap<Coordinate, String> = hashMapOf()
        for (line in input) {
            val wallCorners = line.split(" -> ")
                .map { point -> point.split(",").map { it.toInt() } }
            for (i in 1 until wallCorners.size) {
                val start = wallCorners[i - 1]
                val end = wallCorners[i]
                if (start[0] < end[0]) {
                    for (x in start[0]..end[0]) {
                        hashMap[Coordinate(x, start[1])] = Characters.WALL
                    }
                }
                if (start[0] > end[0]) {
                    for (x in end[0]..start[0]) {
                        hashMap[Coordinate(x, start[1])] = Characters.WALL
                    }
                }
                if (start[1] < end[1]) {
                    for (y in start[1]..end[1]) {
                        hashMap[Coordinate(start[0], y)] = Characters.WALL
                    }
                }
                if (start[1] > end[1]) {
                    for (y in end[1]..start[1]) {
                        hashMap[Coordinate(start[0], y)] = Characters.WALL
                    }
                }
            }
        }
        return hashMap
    }


    val space = parseInput()
    fun printSpace() {
        val grid = MutableList(yLimit + 1) { MutableList(301 + xLimits.second - xLimits.first) { "." } }
        space.keys.forEach { grid[it.y][151 + it.x - xLimits.first] = space[it].toString() }
        grid.forEach {
                row -> row.forEach { print(it) }
            println("")
        }
        println("------------")
    }

    fun addFloor() {
        for (x in (xLimits.first - 149)..(xLimits.second + 149)) {
            space[Coordinate(x, yLimit)] = Characters.WALL
        }
    }
    addFloor()

    fun getNextCoordinate(coordinate: Coordinate): Coordinate {
        val down = coordinate.down() ?: return coordinate
        val downLeft = coordinate.downLeft() ?: return coordinate
        val downRight = coordinate.downRight() ?: return coordinate
        if (space[coordinate.down()] == null) return down
        if (space[coordinate.downLeft()] == null) return downLeft
        if (space[coordinate.downRight()] == null) return downRight
        return coordinate
    }

    var addMoreSand = true
    var counter = 0
    while (addMoreSand) {
        counter++
        var grainIsMoving = true
        var currentCoordinate = Coordinate(500, 0)

        while (grainIsMoving) {
            val nextCoordinate = getNextCoordinate(currentCoordinate)

            // End conditions
            if (endPoint == EndPoint.BOTTOM && nextCoordinate.y == yLimit) {
                printSpace()
                return counter
            }
            if (endPoint == EndPoint.START && nextCoordinate.y == 0) {
                printSpace()
                return counter
            }

            if (nextCoordinate == currentCoordinate) {
                space[currentCoordinate] = Characters.GRAIN
                grainIsMoving = false
            } else {
                currentCoordinate = nextCoordinate
            }
        }
    }
    return counter
}

fun main() {

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
//    val simulateSand = simulateSand(testInput)
//    simulateSand(testInput, EndPoint.BOTTOM).println()
//    simulateSand(testInput, EndPoint.START).println()
//    check(part2(parseInput(testInput)) == 29)

    val input = readInput("Day14")
    simulateSand(input, EndPoint.START).println()
}
