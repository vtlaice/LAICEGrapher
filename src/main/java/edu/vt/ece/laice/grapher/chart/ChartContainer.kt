package edu.vt.ece.laice.grapher.chart

import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.data.xy.XYSeriesCollection

/**
 * Created by cameronearle on 6/6/17.
 */
object ChartContainer {
    val dataCollection = XYSeriesCollection()
    val chart = ChartFactory.createXYLineChart("", "Time", "Value", dataCollection)!!

    fun getChartPanel() = ChartPanel(chart)
}