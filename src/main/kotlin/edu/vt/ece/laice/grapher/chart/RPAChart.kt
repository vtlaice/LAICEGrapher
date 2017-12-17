package edu.vt.ece.laice.grapher.chart

import org.jfree.chart.ChartPanel
import org.jfree.chart.JFreeChart
import org.jfree.chart.plot.XYPlot

/*
 * LAICEGrapher - Created on 12/8/17
 * Author: Cameron Earle
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at project root
 */

/**
 * @author Cameron Earle
 * @version 12/8/17
 */

object RPAChart {
    private val plot = XYPlot()
    private val chart = JFreeChart(plot)
    private val panel = ChartPanel(chart)

    fun getChartPanel() = panel

    fun autoRange() = panel.restoreAutoBounds()
}