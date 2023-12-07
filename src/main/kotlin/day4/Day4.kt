package day4

import java.io.File
import kotlin.math.pow

fun main(){
    part1()
    part2()
}

fun part1(){
    val result = File("src/main/kotlin/day4/input.txt")
        .readLines()
        .map { toCard(it) }
        .sumOf { card ->
            when (card.matches.size) {
                0 -> 0.0
                else -> 2.0.pow(card.matches.size - 1)
            }
        }
    println(result)
}

fun part2(){
    val cards = File("src/main/kotlin/day4/input.txt")
        .readLines()
        .map { toCard(it) }
    val cardsById = cards.associateBy { it.cardId }
    val captured = mutableListOf<Card>()
    val toProcess = cards.toMutableList()
    while (toProcess.isNotEmpty()) {
        toProcess.removeLast().let {
            captured.add(it)
            toProcess.addAll(it.givesCardIds.mapNotNull { cardId -> cardsById[cardId] })
        }
    }
    println(captured.size)
}

fun toCard(str: String): Card {
    val (cardInfo, numbers) = str.split(":")
    val cardId = cardInfo.split("\\s+".toRegex())[1].toLong()
    val (winningNumbersStr, numbersWeHaveStr) = numbers.split("|")
    val winningNumbers = winningNumbersStr.trim().split("\\s+".toRegex()).map { it.toLong() }.toSet()
    val numbersWeHave = numbersWeHaveStr.trim().split("\\s+".toRegex()).map { it.toLong() }.toSet()
    return Card(cardId, winningNumbers, numbersWeHave)
}

data class Card(val cardId: Long, val winningNumbers: Set<Long>, val numbersWeHave: Set<Long>){
    val matches = winningNumbers.intersect(numbersWeHave)
    val givesCardIds = if(matches.isEmpty()) emptyList() else (1..matches.size).map { it + cardId }
}