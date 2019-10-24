package edu.vt.ece.laice.grapher.data

import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.MutableTreeNode

/**
 * Created by cameronearle on 7/5/17.
 */
object XBindings {
    private val bindings = arrayListOf<SingleBinding>()

    init {
        with (bindings) {
            add(SingleBinding(Tables.X, "lat", "Latitude", "deg"))
            add(SingleBinding(Tables.X, "lon", "Longitude", "deg"))
            add(SingleBinding(Tables.X, "alt", "Altitude", "km"))
            add(SingleBinding(Tables.X, "ut", "Universal Time", "Hours"))
            add(SingleBinding(Tables.X, "_id", "Packet ID", "ID"))
        }
    }

    fun getBinding(internalName: String) = bindings.first { it.internalName == internalName }

    fun getArray() = bindings.toArray()
}