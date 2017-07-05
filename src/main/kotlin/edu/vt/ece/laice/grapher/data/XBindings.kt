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
            add(SingleBinding(Tables.X, "test", "Test", "UNIT"))
        }
    }

    fun getBinding(internalName: String) = bindings.first { it.internalName == internalName }

    fun getArray() = bindings.toArray()
}