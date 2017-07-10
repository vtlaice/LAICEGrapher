package edu.vt.ece.laice.grapher.gui.main

import edu.vt.ece.laice.grapher.Constants
import edu.vt.ece.laice.grapher.chart.MainChart
import edu.vt.ece.laice.grapher.extensions.gui.*
import edu.vt.ece.laice.grapher.gui.fieldselector.FieldSelectorBuilder
import javax.swing.DefaultListModel

/**
 * Created by cameronearle on 7/7/17.
 */
object EventBindings {
    lateinit var form: MainForm

    fun register(form: MainForm) {
        this.form = form
        with (form) {
            fun checkAvailableButtons() {
                if (yAxisList.selectedIndex == -1) {
                    removeYAxisButton.isEnabled = false
                    upYAxisButton.isEnabled = false
                    downYAxisButton.isEnabled = false
                } else if (yAxisList.selectedIndices.size > 1) {
                    removeYAxisButton.isEnabled = true
                    upYAxisButton.isEnabled = false
                    downYAxisButton.isEnabled = false
                } else {
                    upYAxisButton.isEnabled = yAxisList.selectedIndex != 0
                    downYAxisButton.isEnabled = yAxisList.selectedIndex != yAxisList.model.size - 1
                    removeYAxisButton.isEnabled = true
                }
            }

            yAxisList.addListSelectionListener {
                if (!it.valueIsAdjusting) {
                    checkAvailableButtons()
                }
            }

            upYAxisButton.addActionListener { yAxisList.moveSelectedUp() }
            downYAxisButton.addActionListener { yAxisList.moveSelectedDown() }
            removeYAxisButton.addActionListener { yAxisList.removeSelected() }
            addYAxisButton.addActionListener { FieldSelectorBuilder().apply {
                val model = yAxisList.model as DefaultListModel
                init("Add Items", relativeTo = addYAxisButton)
                show()
                getItems().forEach {
                    if (!model.contains(it)) {
                        model.addElement(it)
                    }
                }
            } }

        }
    }
}