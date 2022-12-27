import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day12Test {
    @Test
    fun testGetCharCode() {
        assertEquals(97, getCharCode("a"))
        assertEquals(97, getCharCode("S"))
        assertEquals(98, getCharCode("b"))
        assertEquals(121, getCharCode("y"))
        assertEquals(122, getCharCode("z"))
        assertEquals(123, getCharCode("E"))
    }
}