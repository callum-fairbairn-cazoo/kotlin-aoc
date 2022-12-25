import println
import readInput
import java.io.File

fun customReadInput(name: String) = File("src", "$name.txt")
    .readText().split("\n\n")


fun main() {
    fun part1(input: List<String>): Int {
        return input.maxOf { group ->
            group.split("\n").sumOf { Integer.parseInt(it) }
        }
    }

    fun part2(input: List<String>): Int {
        var window = intArrayOf(0, 0, 0)
        input.forEach { group ->
            val total = group.split("\n").sumOf { Integer.parseInt(it) }
            if (total > window.min()) {
                window[window.indexOf(window.min())] = total
            }
        }

        return window.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = customReadInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = customReadInput("Day01")
    check(part1(input) == 69177)
    check(part2(input) == 207456)
}
