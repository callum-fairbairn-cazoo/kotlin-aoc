import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day16Test {
    @Test
    fun testCalculatesShortestPaths() {
        val testInput =
            """
                AA;0;BB
                BB;0;AA,CC
                CC;0;BB,DD
                DD;0;CC
            """.trimIndent().split("\n")
        val rooms = parseInputDay16(testInput)
        assertEquals(null, rooms[0].shortestPaths["AA"])
        assertEquals(1, rooms[0].shortestPaths["BB"])
        assertEquals(2, rooms[0].shortestPaths["CC"])
        assertEquals(1, rooms[1].shortestPaths["CC"])
        assertEquals(3, rooms[3].shortestPaths["AA"])
    }

    @Test
    fun testPart1Returns0() {
        val testInput =
            """
                AA;0;BB
                BB;0;AA
            """.trimIndent().split("\n")
        assertEquals(0, part1(testInput))
    }

    @Test
    fun testPart1Returns29() {
        val testInput =
            """
                AA;1;BB
                BB;0;AA
            """.trimIndent().split("\n")
        assertEquals(29, part1(testInput))
    }

    @Test
    fun testPart1Returns56() {
        val testInput =
            """
                AA;1;BB
                BB;1;AA
            """.trimIndent().split("\n")
        assertEquals(56, part1(testInput))
    }

    @Test
    fun testPart1Returns318() {
        val testInput =
            """
                AA;1;BB
                BB;1;AA,CC
                CC;10;BB
            """.trimIndent().split("\n")
        assertEquals(318, part1(testInput))
    }
}