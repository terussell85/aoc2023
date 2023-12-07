package day3

import java.io.File

fun main() {
    part1()
    part2()
}

fun part1() {
    val lines = File("src/main/kotlin/day3/input.txt").readLines()
    val numbers = extractNumbers(lines)
    val symbols = extractSymbols(lines)
    val sum = numbers.filter { num -> symbols.any { symbol -> symbol.loc.isAdjacent(num.loc) } }
        .sumOf { it.value }
    println(sum)
}

fun part2() {
    val lines = File("src/main/kotlin/day3/input.txt").readLines()
    val numbers = extractNumbers(lines)
    val symbols = extractSymbols(lines)
    val sum = symbols.map { symbol ->
        val gearParts = numbers.filter { num -> symbol.symbol == '*' && symbol.loc.isAdjacent(num.loc) }
        symbol to gearParts
    }.filter {
        it.second.size == 2
    }.sumOf { it.second[0].value * it.second[1].value }
    println(sum)
}

fun extractNumbers(lines: List<String>): MutableList<PartNum> {
    val numbers = mutableListOf<PartNum>()
    lines.forEachIndexed { y, line ->
        var digitStart: Int? = null
        var digitEnd: Int? = null
        line.forEachIndexed { x, char ->
            if (char.isDigit()) {
                if (digitStart == null) {
                    digitStart = x
                }
                digitEnd = x
            } else {
                if (digitEnd != null) {
                    val numberStr = line.substring(IntRange(digitStart!!, digitEnd!!))
                    val partNum = PartNum(numberStr.toInt(), Location(y, digitStart!!, digitEnd!!))
                    numbers.add(partNum)
                    digitStart = null
                    digitEnd = null
                }
            }
        }
        if (digitEnd != null) {
            val numberStr = line.substring(IntRange(digitStart!!, digitEnd!!))
            val partNum = PartNum(numberStr.toInt(), Location(y, digitStart!!, digitEnd!!))
            numbers.add(partNum)
            digitStart = null
            digitEnd = null
        }
    }
    return numbers
}

fun extractSymbols(lines: List<String>): MutableList<Symbol> {
    val symbols = mutableListOf<Symbol>()
    lines.forEachIndexed { y, line ->
        line.forEachIndexed { x, char ->
            if (char.isSymbol()) {
                symbols.add(Symbol(char, Location(y, x, x)))
            }
        }
    }
    return symbols
}

fun Char.isSymbol() = !this.isDigit() && this != '.'
data class Symbol(val symbol: Char, val loc: Location)
data class PartNum(val value: Int, val loc: Location)
data class Location(val y: Int, val minX: Int, val maxX: Int) {
    fun isAdjacent(loc: Location): Boolean {
        val yAdjacent = loc.y in (y - 1..y + 1)
        val xMinAdjacent = loc.minX in (minX - 1..maxX + 1)
        val xMaxAdjacent = loc.maxX in (minX - 1..maxX + 1)
        return (yAdjacent && xMinAdjacent) ||
                (yAdjacent && xMaxAdjacent)
    }
}