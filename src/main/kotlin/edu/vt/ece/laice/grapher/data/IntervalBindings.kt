package edu.vt.ece.laice.grapher.data

import javax.swing.tree.DefaultMutableTreeNode

/**
 * Created by cameronearle on 7/5/17.
 */
object IntervalBindings {
    private val bindings = arrayListOf<Interval>()

    init {
        with (bindings) {
            add(Interval("Full Orbit", 1.0))
            add(Interval("Half Orbit", 0.5))
            add(Interval("Quarter Orbit", .25))
            add(Interval(".1 Orbit", .1))
            add(Interval(".05 Orbit", .05))
            add(Interval(".01 Orbit", .01))

            add(Interval("Single Packet", -1.0))
        }
    }

    fun getArray() = bindings.toArray()
}