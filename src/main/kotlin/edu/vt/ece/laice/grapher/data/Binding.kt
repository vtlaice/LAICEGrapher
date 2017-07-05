package edu.vt.ece.laice.grapher.data

/**
 * Created by cameronearle on 6/27/17.
 */
abstract class Binding {
    abstract val table: Tables
    abstract val internalName: String
    abstract val prettyName: String
    abstract val units: String

    override fun toString() = prettyName
}