class Room(val name: String, var flowRate: Int, private val connectingRooms: List<String>) {
    private var connections: List<Room> = mutableListOf()
    fun linkConnections(rooms: List<Room>) {
        this.connections = connectingRooms.map { connection -> rooms.find { it.name == connection }!! }
    }

    var shortestPaths: HashMap<String, Int> = hashMapOf()
    private fun getShortestPath(target: Room): Int {
        val seenRooms: MutableList<Room> = mutableListOf(this)
        var queue: MutableSet<Room> = connections.toMutableSet()
        var counter = 1

        while (queue.size > 0) {
            val nextQueue: MutableSet<Room> = mutableSetOf()
            while (queue.size > 0) {
                val current = queue.first().also { queue.remove(it) }
                if (current == target) return counter
                seenRooms.add(current)
                current.connections.forEach { if (!seenRooms.contains(it)) nextQueue.add(it) }
            }
            counter++
            queue = nextQueue
        }
        return counter
    }

    fun calculateShortestPaths(rooms: List<Room>) {
        rooms.forEach {
            if (it != this) {
                shortestPaths[it.name] = getShortestPath(it)
            }
        }
    }
}

fun parseInputDay16(input: List<String>): List<Room> {
    val rooms = input.map {
        val info = it.split(";")
        Room(info[0], info[1].toInt(), info[2].split(","))
    }
    rooms.forEach { it.linkConnections(rooms) }
    rooms.forEach { it.calculateShortestPaths(rooms) }
    return rooms
}

const val MAX_MINUTES = 30

fun validTargetRoom(room: Room, openValves: HashMap<String, Int>): Boolean {
    return room.flowRate > 0 && !openValves.contains(room.name)
}

fun part1(input: List<String>): Int {
    val rooms = parseInputDay16(input)
    fun maximiseRelievedPressure(
        minute: Int,
        pressureRelieved: Int,
        openValves: HashMap<String, Int>,
        currentRoom: Room,
        logMessage: String,
        wait: Int,
        initial: Boolean = false,
    ): Pair<Int, String> {
        return if (minute == MAX_MINUTES - 1) {
            Pair(pressureRelieved, logMessage)
        } else {
            val newMinute = minute + 1
            val newPressureRelieved = pressureRelieved + openValves.values.sum()

            // Handle wait
            if (wait > 0) {
                return maximiseRelievedPressure(
                    newMinute,
                    newPressureRelieved,
                    openValves,
                    currentRoom,
                    logMessage,
                    wait - 1
                )
            }

            // Turn valve
            if (validTargetRoom(currentRoom, openValves) && !initial) {
                val newLogMessage = logMessage.plus(".(${minute},${currentRoom.name})")
                val newOpenValves = HashMap(openValves.toMap())
                newOpenValves[currentRoom.name] = currentRoom.flowRate
                return maximiseRelievedPressure(
                    minute,
                    pressureRelieved,
                    newOpenValves,
                    currentRoom,
                    newLogMessage,
                    1
                )
            }

            // Find next rooms
            var nextRooms = rooms.filter { validTargetRoom(it, openValves) }

            // If there are no more rooms, kill time until the end
            if (nextRooms.isEmpty()) {
                return maximiseRelievedPressure(
                    newMinute,
                    newPressureRelieved,
                    openValves,
                    currentRoom,
                    logMessage,
                    MAX_MINUTES
                )
            }

            // Return max pressure relieved for each possible room
            val max = nextRooms.maxOfWith({ a, b -> a.first - b.first }) {
                maximiseRelievedPressure(
                    if (it == currentRoom && initial) 0 else newMinute,
                    newPressureRelieved,
                    openValves,
                    it,
                    logMessage,
                    if (it == currentRoom && initial) 0 else (it.shortestPaths[currentRoom.name]!! - 1)
                )
            }

            return max
        }
    }

    val result = maximiseRelievedPressure(0, 0, hashMapOf(), rooms[0], "", 0, true)
    result.second.println()
    return result.first
}

fun part2(input: List<String>) {}

fun main() {
    val testInput = readInput("Day16_test")
    part1(testInput).println()
//    part2(testInput).println()

    val input = readInput("Day16")
    part1(input).println()
//    part2(input).println()
}
