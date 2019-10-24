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
            add(SingleBinding(Tables.X, "lat", "Latitude", "Deg"))
            add(SingleBinding(Tables.X, "lon", "Longitude", "Deg"))
            add(SingleBinding(Tables.X, "alt", "Altitude", "km"))
            add(SingleBinding(Tables.Y, "rpa_1", "RPA Current Sample 1", "nA"))
            add(SingleBinding(Tables.Y, "ng_current", "Neutral Gauge Current", "nA"))
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
                            if (!it.hidden) {
                                subNode.add(DefaultMutableTreeNode(it)) //Create and add a level 3 sub node for the binding in the group
                            }
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