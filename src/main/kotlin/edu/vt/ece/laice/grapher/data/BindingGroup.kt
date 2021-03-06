package edu.vt.ece.laice.grapher.data

/**
 * Created by cameronearle on 6/27/17.
 */
class BindingGroup(override val table: Tables, override val internalName: String, override val prettyName: String, override val units: String) : Binding() {
    val bindings = arrayListOf<SingleBinding>()
    fun add(name: String, prettyName: String, hidden: Boolean = false) {
        bindings.add(SingleBinding(table, name, prettyName, units, this, hidden))
    }
}