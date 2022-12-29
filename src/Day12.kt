class Point(val x: Int, val y: Int)
enum class Direction { UP, DOWN }

fun getLocationOf(input: List<List<String>>, target: String): List<String> {
    val foundPoints = mutableListOf<String>()
    for (i in input.indices) {
        for (j in input[i].indices) {
            if (input[i][j] == target) {
                foundPoints.add(getHash(Point(i, j)))
            }
        }
    }
    return foundPoints
}

fun getCharCode(string: String): Int {
    if (string == "S") return "a".codePointAt(0)
    if (string == "E") return "z".codePointAt(0) + 1
    return string.codePointAt(0)
}

fun getHash(point: Point): String {
    return "${point.x}.${point.y}"
}

fun decodeHash(string: String): Point {
    val xy = string.split(".")
    return Point(xy[0].toInt(), xy[1].toInt())
}

fun printGrid(grid: List<List<String>>, seen: MutableSet<String>) {
    for (i in grid.indices) {
        for (j in grid[i].indices) {
            if (getHash(Point(i, j)) in seen) {
                print("*")
            } else {
                print(grid[i][j])
            }
        }
        println("")
    }
    println("")
}


fun findMinPath(input: List<List<String>>, start: String, end: List<String>, direction: Direction): Int {
    fun getValue(point: Point): Int {
        return getCharCode(input[point.x][point.y])
    }

    fun pointInBounds(point: Point): Boolean {
        return point.x >= 0 && point.x < input.size && point.y >= 0 && point.y < input[point.x].size
    }

    fun nextPointIsValid(nextPoint: Point, currentVal: Int): Boolean {
        if (direction == Direction.UP) {
            return getValue(nextPoint) <= currentVal + 1
        }
        return currentVal - 1 <= getValue(nextPoint)
    }

    var counter = 0
    val seen = mutableSetOf<String>()
    val nextEdge: MutableList<String> = mutableListOf(start)
    while (end.intersect(seen).isEmpty()) {
//        printGrid(input, seen)
        if (nextEdge.size == 0) return counter
        counter++
        val edge = nextEdge.map { decodeHash(it) }.toMutableList()
        nextEdge.clear()
        for (current in edge) {
            seen.add(getHash(current))
            val currentVal = getValue(current)
            val neighbors = arrayOf(
                Point(current.x, current.y - 1),
                Point(current.x, current.y + 1),
                Point(current.x - 1, current.y),
                Point(current.x + 1, current.y),
            )

            neighbors.forEach {
                if (
                    getHash(it) !in nextEdge &&
                    getHash(it) !in seen &&
                    pointInBounds(it) &&
                    nextPointIsValid(it, currentVal)
                ) {
                    nextEdge.add(getHash(it))
                }
            }
        }
    }
    return counter - 1
}

private fun parseInput(input: List<String>): List<List<String>> {
    return input.map { line -> line.split("").filter { it !== "" } }
}

fun main() {
    fun part1(input: List<List<String>>): Int {
        val start = getLocationOf(input, "S")
        if (start.isEmpty()) {
            throw Exception("Start not found")
        }
        val end = getLocationOf(input, "E") ?: throw Exception("End not found")
        if (end.isEmpty()) {
            throw Exception("End not found")
        }
        return findMinPath(input, start[0], end, Direction.UP)
    }

    fun part2(input: List<List<String>>): Int {
        val start = getLocationOf(input, "E")
        if (start.isEmpty()) {
            throw Exception("Start not found")
        }
        val end = getLocationOf(input, "a") ?: throw Exception("End not found")
        if (end.isEmpty()) {
            throw Exception("End not found")
        }
        return findMinPath(input, start[0], end, Direction.DOWN)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
//    check(part1(parseInput(testInput)) == 31)
//    check(part2(parseInput(testInput)) == 29)

    val input = readInput("Day12")
    part1(parseInput(input)).println()
    part2(parseInput(input)).println()
}
