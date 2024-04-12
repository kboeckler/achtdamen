package de.kevinboeckler.dojo

import kotlin.math.abs
import kotlin.system.measureTimeMillis

fun main() {
    val millis = measureTimeMillis {
        val loesungen = solve(8)
        println(loesungen.size)
        println(loesungen)
    }
    print("$millis ms passed")
}

fun solve(n: Int): List<Loesung> {
    val felder = mutableSetOf<Feld>()
    for (i in 0..<n) {
        for (j in 0..<n) {
            felder.add(Feld(i, j))
        }
    }
    return solve(felder, n)
}

data class SolveParams(val felder: Set<Feld>, val damen: Int)

val params = mutableMapOf<SolveParams, List<Loesung>>()

fun solve(felder: Set<Feld>, damen: Int): List<Loesung> {
    if (damen == 0) {
        return listOf(Loesung(setOf()))
    }
    val par = SolveParams(felder, damen)
    if (params.contains(par)) {
        return params[par]!!
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
    params[par] = loesungen
    return loesungen
}

fun streicheFelder(felder: Set<Feld>, dame: Feld): Set<Feld> {
    val neueFelder = mutableSetOf<Feld>()
    for (feld in felder) {
        if (dame.cannotSee(feld)) {
            neueFelder.add(feld)
        }
    }
    return neueFelder
}


data class Feld(val x: Int, val y: Int) {
    fun cannotSee(feld: Feld) = !(x == feld.x || y == feld.y || abs(feld.x - x) == abs(feld.y - y))

}

data class Loesung(val felder: Set<Feld>)

