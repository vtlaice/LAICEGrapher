package edu.vt.ece.laice.grapher.extensions.gui

import javax.swing.DefaultListModel
import javax.swing.JList
import javax.swing.ListModel

/**
 * Created by cameronearle on 7/7/17.
 */

fun DefaultListModel<Any>.swap(index1: Int, index2: Int) {
    val item1 = getElementAt(index1)
    val item2 = getElementAt(index2)
    this.set(index1, item2)
    this.set(index2, item1)
}

fun JList<Any>.moveSelectedUp(): Boolean {
    val index = selectedIndex
    if (index != 0) {
        (model as? DefaultListModel)?.swap(index, index - 1)
        selectedIndex = index - 1
        ensureIndexIsVisible(index - 1)
        return true
    }
    return false
}

fun JList<Any>.moveSelectedDown(): Boolean {
    val index = selectedIndex
    if (index != model.size - 1) {
        (model as? DefaultListModel)?.swap(index, index + 1)
        selectedIndex = index + 1
        ensureIndexIsVisible(index + 1)
        return true
    }
    return false
}

fun JList<Any>.removeSelected() {
    var firstSelected = selectionModel.minSelectionIndex
    val lastSelected = selectionModel.maxSelectionIndex
    val model = model as? DefaultListModel
    if (model != null && firstSelected >=0 && lastSelected >= 0) {
        model.removeRange(firstSelected, lastSelected)
        if (model.size != 0) {
            if (firstSelected == model.size) {
                firstSelected--
            }
            selectedIndex = firstSelected
        }
    }

}

fun <T> JList<Any>.getItems(): List<T> = (0 until model.size).map { model.getElementAt(it) as T }