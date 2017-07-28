package edu.vt.ece.laice.grapher.gui.main

import edu.vt.ece.laice.grapher.Constants
import edu.vt.ece.laice.grapher.chart.MainChart
import edu.vt.ece.laice.grapher.data.ActiveDataManager
import edu.vt.ece.laice.grapher.data.Interval
import edu.vt.ece.laice.grapher.data.SingleBinding
import edu.vt.ece.laice.grapher.extensions.gui.*
import edu.vt.ece.laice.grapher.gui.fieldselector.FieldSelectorBuilder
import javax.swing.DefaultListModel
import javax.swing.event.ListDataEvent
import javax.swing.event.ListDataListener

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

            upYAxisButton.addActionListener {
                yAxisList.moveSelectedUp()
                ActiveDataManager.setYBindings(yAxisList.getItems())
            }
            downYAxisButton.addActionListener {
                yAxisList.moveSelectedDown()
                ActiveDataManager.setYBindings(yAxisList.getItems())
            }
            removeYAxisButton.addActionListener {
                yAxisList.removeSelected()
                ActiveDataManager.setYBindings(yAxisList.getItems())
            }

            addYAxisButton.addActionListener {
                FieldSelectorBuilder().apply {
                    val model = yAxisList.model as DefaultListModel
                    init("Add Items", relativeTo = addYAxisButton)
                    show()
                    val items = getItems()
                    var addedItems = 0
                    items.forEach {
                        if (!model.contains(it) && model.size < Constants.MAX_STACKED_COUNT) {
                            model.addElement(it)
                            addedItems++
                        }
                    }
                    if (addedItems > 0) {
                        ActiveDataManager.setYBindings(yAxisList.getItems())
                    }
                }
            }

            xAxisSelectComboBox.addActionListener {
                ActiveDataManager.setXBinding(xAxisSelectComboBox.selectedItem as SingleBinding)
            }

            startSelectButton.addActionListener {
                if (startDateRadioButton.isSelected) {
                    println("Not supported")
                } else if (startOrbitRadioButton.isSelected) {
                    ActiveDataManager.updateOrbitRange(startOrbitSpinner.value as Int, intervalComboBox.selectedItem as Interval)
                }
            }

        }
    }
}