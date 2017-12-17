package edu.vt.ece.laice.grapher.gui.main

import com.alee.extended.date.WebDateField
import com.intellij.uiDesigner.core.GridConstraints
import edu.vt.ece.laice.grapher.chart.Chart
import edu.vt.ece.laice.grapher.chart.MainChart
import edu.vt.ece.laice.grapher.data.IntervalBindings
import edu.vt.ece.laice.grapher.data.XBindings
import edu.vt.ece.laice.grapher.data.YBindings
import edu.vt.ece.laice.grapher.gui.Manager
import javax.swing.DefaultComboBoxModel
import javax.swing.DefaultListModel
import javax.swing.ListModel
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel
import javax.swing.tree.TreeSelectionModel

/**
 * Created by cameronearle on 6/23/17.
 */

object MainFormManager: Manager<MainForm>(MainForm()) {
    fun setChart(chart: Chart) {
        form.graphingViewport.removeAll()
        form.graphingViewport.add(car)
    }

    override fun initTasks() {
        setMainPanel(form.mainPanel)

        form.graphingViewport.add(MainChart.getChartPanel(), GridConstraints().apply {
            fill = GridConstraints.FILL_BOTH
        })


        with (form.intervalComboBox) {
            model = DefaultComboBoxModel(IntervalBindings.getArray())
        }

        with (form.xAxisSelectComboBox) {
            model = DefaultComboBoxModel(XBindings.getArray())
        }

        with (form.yAxisList) {
            model = DefaultListModel<Any>()
        }

        EventBindings.register(form) //Setup the event bindings
    }
}