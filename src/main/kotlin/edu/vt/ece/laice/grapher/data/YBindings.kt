package edu.vt.ece.laice.grapher.data

import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.MutableTreeNode

/**
 * Created by cameronearle on 6/23/17.
 */
object YBindings {
    private val bindings = arrayListOf<Binding>()

    init {
        with (bindings) {
            //ADD BINDINGS HERE
            add(BindingGroup(Tables.RPA, "currentSamples", "Current Samples", "Amps").apply {
                for (i in 1..64) {
                    add("currentSample${i}_Amps", "Current Sample $i")
                }
            })

            add(BindingGroup(Tables.RPA, "voltageSamples", "Voltage Samples", "Volts").apply {
                for (i in 1..64) {
                    add("voltageSample${i}_Volts", "Voltage Sample $i")
                }
            })
        }
    }

    fun getBindingsByTable(table: Tables) = bindings.filter { it.table == table }

    fun getBinding(internalName: String) = bindings.first { it.internalName == internalName }

    fun getBindingFromGroup(groupName: String, bindingName: String) = (bindings.first { it.internalName == groupName && it is BindingGroup }
                                                                                        as BindingGroup).bindings.first{ it.internalName == bindingName}

    fun buildSwingTree(): MutableTreeNode {
        val topNode = DefaultMutableTreeNode("LAICE Y Data") //Create a level 0 node for all data
        Tables.values().forEach { //Iterate the available tables
            if (!it.excluded) {
                val tableNode = DefaultMutableTreeNode(it) //Create a level 1 node for the table
                getBindingsByTable(it).forEach {
                    //Iterate the bindings for the table
                    val subNode = DefaultMutableTreeNode(it) //Create a level 2 sub node for the binding
                    if (it is BindingGroup) { //If the active binding is a group
                        it.bindings.forEach {
                            //Iterate the group
                            subNode.add(DefaultMutableTreeNode(it)) //Create and add a level 3 sub node for the binding in the group
                        }
                    }
                    tableNode.add(subNode) //Add the level 2 node to the level 1 node
                }
                topNode.add(tableNode) //Add the level 1 table node to the level 0 root node
            }
        }

        return topNode
    }
}