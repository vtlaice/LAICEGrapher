package edu.vt.ece.laice.grapher.data

/**
 * Created by cameronearle on 6/23/17.
 */
enum class Tables(val tableName: String, val prettyName: String, val excluded: Boolean = false) {
    X("x_lookup", "Simulated X Data"),
    Y("fake_y", "Simulated Y Data");

    override fun toString() = prettyName
}