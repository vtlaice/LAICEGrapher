package edu.vt.ece.laice.grapher.data

/**
 * Created by cameronearle on 7/5/17.
 */

data class Interval(val name: String, val multiplier: Double) {
    val points = if (multiplier < 0) 0 else (5400 * multiplier).toInt()

    override fun toString() = name
}