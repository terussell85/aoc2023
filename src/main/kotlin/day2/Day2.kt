package day2

import java.io.File
import kotlin.math.max

val maxRed = 12
val maxGreen = 13
val maxBlue = 14
val maxTotal = maxRed + maxGreen + maxBlue
val RED_REGEX = Regex("""(\d*) red""")
val BLUE_REGEX = Regex("""(\d*) blue""")
val GREEN_REGEX = Regex("""(\d*) green""")

fun main() {
    part1()
    part2()
}

fun part1() {
    val lines = File("src/main/kotlin/day2/input.txt").readLines()
    val games = lines.map { toGame(it) }
    val validGames = games.filter { game ->
        game.iterations.all { it.blue <= maxBlue && it.red <= maxRed && it.green <= maxGreen }
    }
    val sum = validGames.sumOf { it.id }
    println(sum)
}

fun part2() {
    val lines = File("src/main/kotlin/day2/input.txt").readLines()
    val games = lines.map { toGame(it) }
    val possibles = games.map { game ->
        val initial = Iteration(0, 0, 0)
        val possible = game.iterations.fold(initial){ agg, it ->
            return@fold Iteration(
                red = max(agg.red, it.red),
                green = max(agg.green, it.green),
                blue = max(agg.blue, it.blue),
            )
        }
        return@map possible
    }
    val sum = possibles.sumOf { it.red * it.green * it.blue }
    println(sum)
}

fun toGame(line: String): Game {
    val pieces = line.split(":")
    val id = pieces[0].replace("Game", "").trim().toLong()
    val iterations = pieces[1].split(";").map { it.trim() }
        .map {
            val blue = BLUE_REGEX.find(it)?.groupValues?.get(1)?.toLong() ?: 0
            val red = RED_REGEX.find(it)?.groupValues?.get(1)?.toLong() ?: 0
            val green = GREEN_REGEX.find(it)?.groupValues?.get(1)?.toLong() ?: 0
            Iteration(
                red = red,
                green = green,
                blue = blue
            )
        }
    return Game(id, iterations)
}

data class Game(val id: Long, val iterations: List<Iteration>)
data class Iteration(val red: Long, val blue: Long, val green: Long)