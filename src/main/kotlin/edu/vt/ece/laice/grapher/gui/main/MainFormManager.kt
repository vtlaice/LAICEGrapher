package edu.vt.ece.laice.grapher.gui.main

import com.alee.extended.date.WebDateField
import com.intellij.uiDesigner.core.GridConstraints
import edu.vt.ece.laice.grapher.chart.MainChart
import edu.vt.ece.laice.grapher.data.IntervalBindings
import edu.vt.ece.laice.grapher.data.XBindings
import edu.vt.ece.laice.grapher.data.YBindings
import edu.vt.ece.laice.grapher.gui.Manager
import javax.swing.DefaultComboBoxModel
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel
import javax.swing.tree.TreeSelectionModel

/**
 * Created by cameronearle on 6/23/17.
 */
object MainFormManager: Manager<MainForm>(MainForm()) {
    override fun initTasks() {
        setMainPanel(form.mainPanel)

        form.yAxisSelectScrollPane.verticalScrollBar.unitIncrement = 24

        form.graphingViewport.add(MainChart.getChartPanel(), GridConstraints().apply {
            fill = GridConstraints.FILL_BOTH

        })

        with (form.intervalComboBox) {
            model = DefaultComboBoxModel(IntervalBindings.getArray())
        }

        with (form.xAxisSelectComboBox) {
            model = DefaultComboBoxModel(XBindings.getArray())
        }

        with (form.yAxisSelect) {
            model = DefaultTreeModel(YBindings.buildSwingTree())
            isRootVisible = false
            showsRootHandles = true
            selectionModel.selectionMode = TreeSelectionModel.SINGLE_TREE_SELECTION
            addTreeSelectionListener { //This disables selecting nodes directly and makes clicking the node check/uncheck the box
                try {
                    val node = lastSelectedPathComponent as DefaultMutableTreeNode //Get the selected node
                    invertCheck(node) //Switch the checkbox
                    clearSelection() //Clear the selection so it doesn't highlight the node
                } catch (e: Exception) {} //For some reason this throws an exception, so we just ignore it
            }

        }
    }
}