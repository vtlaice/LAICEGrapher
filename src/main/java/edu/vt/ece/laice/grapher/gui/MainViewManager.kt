package edu.vt.ece.laice.grapher.gui

import com.intellij.uiDesigner.core.GridConstraints
import edu.vt.ece.laice.grapher.chart.ChartContainer
import java.awt.Dimension


/**
 * Created by cameronearle on 6/2/17.
 */

object MainViewManager : ViewManager() {
    private val form = MainView()
    private val fillConstraints = GridConstraints().apply {
        fill = GridConstraints.FILL_BOTH
    }

    override fun initTasks() {
        frame.minimumSize = Dimension(100, 100) //Give the frame a minimum size so the user doesn't make it too small
        setMainPanel(form.mainPanel) //Register the main panel with the GUI

        //Add components to the panels
        form.chartPanel.add(ChartContainer.getChartPanel(), fillConstraints) //Add the chart to the interface

        //Handle Events
        form.graphNewWindowButton.addActionListener {
            FullscreenGraphDialogManager.show()
        }
    }

}