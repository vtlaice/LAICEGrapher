package edu.vt.ece.laice.grapher.chart

import org.jfree.chart.ChartPanel
import org.jfree.chart.JFreeChart
import org.jfree.chart.axis.NumberAxis
import org.jfree.chart.plot.CombinedDomainXYPlot
import org.jfree.chart.plot.XYPlot
import org.jfree.chart.renderer.xy.DefaultXYItemRenderer
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection

/**
 * Created by cameronearle on 6/23/17.
 */

object MainChart {
    private val plot = CombinedDomainXYPlot()
    private val chart = JFreeChart(plot)
    private val panel = ChartPanel(chart)

    fun getChartPanel() = panel

    fun setXAxis(name: String) {
        plot.domainAxis.label = name
    }

    fun addNewPlot(series: XYSeries, rangeAxis: String) {
        val dataset = XYSeriesCollection(series)
        val subPlot = XYPlot(dataset, null, NumberAxis(rangeAxis), DefaultXYItemRenderer())
        plot.add(subPlot)
    }

    fun autoRange() {
        panel.restoreAutoBounds()
    }
}