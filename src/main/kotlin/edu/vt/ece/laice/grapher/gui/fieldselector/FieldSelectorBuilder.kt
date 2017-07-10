package edu.vt.ece.laice.grapher.gui.fieldselector

import edu.vt.ece.laice.grapher.data.Binding
import edu.vt.ece.laice.grapher.data.SingleBinding
import edu.vt.ece.laice.grapher.data.YBindings
import edu.vt.ece.laice.grapher.extensions.gui.removeSelected
import edu.vt.ece.laice.grapher.gui.Builder
import org.omg.CORBA.Current
import javax.swing.DefaultListModel
import javax.swing.JTree
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel
import javax.swing.tree.TreePath
import javax.swing.tree.TreeSelectionModel

/**
 * Created by cameronearle on 7/7/17.
 */

class FieldSelectorBuilder: Builder<FieldSelector>(FieldSelector()) {
    override fun initTasks() {
        with (dialog.allItemsTree) {
            model = DefaultTreeModel(YBindings.buildSwingTree())
            isRootVisible = false
            showsRootHandles = true
            selectionModel.selectionMode = TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION
        }

        with (dialog.selectedItemsList) {
            model = DefaultListModel()
        }

        dialog.addButton.addActionListener {
            val selected = dialog.allItemsTree.selectionPaths
            val selectedBindings = mutableListOf<SingleBinding>()
            selected.forEach {
                val userObject = (it.lastPathComponent as DefaultMutableTreeNode).userObject
                if (userObject is SingleBinding) {
                    selectedBindings.add(userObject)
                }
            }
            addToSelected(selectedBindings)
        }

        dialog.removeButton.addActionListener {
            dialog.selectedItemsList.removeSelected()
        }

        dialog.searchTextField.document.addDocumentListener(object : DocumentListener {
            override fun changedUpdate(p0: DocumentEvent?) { search(dialog.searchTextField.text) }
            override fun insertUpdate(p0: DocumentEvent?) { search(dialog.searchTextField.text) }
            override fun removeUpdate(p0: DocumentEvent?) { search(dialog.searchTextField.text) }
        })
    }

    private fun addToSelected(items: List<SingleBinding>) {
        val model = dialog.selectedItemsList.model as DefaultListModel
        items.forEach {
            if (!model.contains(it)) {
                model.addElement(it)
            }
        }
    }

    private fun search(current: String) {
        val currentLower = current.toLowerCase()
        val model = dialog.allItemsTree.model as DefaultTreeModel
        val e = (model.root as DefaultMutableTreeNode).depthFirstEnumeration()
        var node: DefaultMutableTreeNode? = null
        var userObj: Any
        while (e.hasMoreElements()) {
            node = e.nextElement() as DefaultMutableTreeNode
            userObj = node.userObject
            if (userObj is SingleBinding) {
                if (userObj.prettyName.toLowerCase().contains(currentLower)) {
                    break
                }
            }
        }
        if (node != null) {
            val nodes = model.getPathToRoot(node)
            val path = TreePath(nodes)
            with (dialog.allItemsTree) {
                scrollPathToVisible(path)
                selectionPath = path
                startEditingAtPath(path)
            }
        }
    }

    fun getItems(): List<SingleBinding> {
        val rawItems = (dialog.selectedItemsList.model as DefaultListModel).toArray()
        val list = mutableListOf<SingleBinding>()
        rawItems.forEach {
            if (it is SingleBinding) {
                list.add(it)
            }
        }
        return list
    }
}