package day1

import java.io.File

fun main() {
    part1()
    part2()
}

fun part1(){
    val sum = File("src/main/kotlin/day1/input.txt").readLines()
        .map { line ->
            line.firstOrNull { it.isDigit() } to line.lastOrNull { it.isDigit() }
        }
        .map { "" + it.first + it.second }
        .sumOf { it.toLong() }
    println(sum)
}

fun part2(){
    val validWords = wordsToNumbers.keys
    val lines = File("src/main/kotlin/day1/input.txt").readLines()
    val sum = lines
        .map { line ->
            val firstMatch = line.findAnyOf(validWords)
            val lastMatch = line.findLastAnyOf(validWords)
            wordsToNumbers[firstMatch?.second] to wordsToNumbers[lastMatch?.second]
        }
        .map {
            "" + it.first + it.second
        }
        .sumOf { it.toLong() }
    println(sum)
}

val wordsToNumbers =
    mapOf(
        "zero" to 0,
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
        "0" to 0,
        "1" to 1,
        "2" to 2,
        "3" to 3,
        "4" to 4,
        "5" to 5,
        "6" to 6,
        "7" to 7,
        "8" to 8,
        "9" to 9
    )