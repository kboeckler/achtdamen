package de.kevinboeckler.dojo

import kotlin.math.abs
import kotlin.system.measureTimeMillis

fun main() {
    val millis = measureTimeMillis {
        val n = 8
        val loesungen = solve(initFelder(n), n)
        println(loesungen.size)
        println(loesungen[0])
    }
    print("$millis ms passed")
}

fun initFelder(n: Int): Set<Feld> {
    val felder = mutableSetOf<Feld>()
    for (i in 0..<n) {
        for (j in 0..<n) {
            felder.add(Feld(i, j))
        }
    }
    return felder
}

fun solve(felder: Set<Feld>, damen: Int): List<Loesung> {
    if (damen == 0) {
        return listOf(Loesung(setOf()))
    }
    val loesungen = mutableListOf<Loesung>()
    val queue = felder.toMutableList()
    while (queue.isNotEmpty()) {
        val damenFeld = queue.removeAt(0)
        val neueFelder = streicheFelder(felder, damenFeld)
        val loesung = solve(neueFelder, damen - 1)
        for (unterLoesung in loesung) {
            val neueLoesung = unterLoesung.felder.toMutableSet()
            neueLoesung.add(damenFeld)
            loesungen.add(Loesung(neueLoesung))
            neueLoesung.forEach { queue.remove(it) }
        }
    }
    return loesungen
}

fun streicheFelder(felder: Set<Feld>, dame: Feld): Set<Feld> =
    felder.filter { dame.cannotSee(it) }.toSet()

data class Feld(val x: Int, val y: Int) {
    fun cannotSee(feld: Feld) = !(x == feld.x || y == feld.y || abs(feld.x - x) == abs(feld.y - y))
}

data class Loesung(val felder: Set<Feld>)