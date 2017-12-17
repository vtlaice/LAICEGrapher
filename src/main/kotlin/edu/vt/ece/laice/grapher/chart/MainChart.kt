package edu.vt.ece.laice.grapher.chart

import edu.vt.ece.laice.grapher.Constants
import edu.vt.ece.laice.grapher.data.ActiveDataManager
import edu.vt.ece.laice.grapher.data.Datapoint
import edu.vt.ece.laice.grapher.data.DatapointList
import edu.vt.ece.laice.grapher.data.SingleBinding
import org.jfree.chart.ChartPanel
import org.jfree.chart.JFreeChart
import org.jfree.chart.axis.NumberAxis
import org.jfree.chart.axis.ValueAxis
import org.jfree.chart.plot.CombinedDomainXYPlot
import org.jfree.chart.plot.XYPlot
import org.jfree.chart.renderer.xy.DefaultXYItemRenderer
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer
import org.jfree.chart.renderer.xy.XYSplineRenderer
import org.jfree.data.xy.XYDataItem
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection
import org.jfree.util.ShapeUtilities
import java.awt.Color
import java.awt.Shape

/**
 * Created by cameronearle on 6/23/17.
 */

object MainChart {
    private val plot = CombinedDomainXYPlot()
    private val chart = JFreeChart(plot)
    private val panel = ChartPanel(chart)

    private fun clearPlots() {
        val plots = plot.subplots.toList() as List<XYPlot>
        plots.forEach {
            plot.remove(it)
        }
    }

    private fun buildPlots(data: DatapointList): List<XYPlot> {
        if (data.isEmpty()) {
            return listOf()
        }
        val values = data.getValues()
        val datasets = LinkedHashMap<SingleBinding, XYSeriesCollection>()
        values.forEach {
            binding, values ->
            val series = XYSeries(binding.prettyName)
            values.forEach {
                series.add(it.first, it.second)
            }
            datasets.put(binding, XYSeriesCollection(series))
        }
        val plots = arrayListOf<XYPlot>()
        datasets.forEach {
            binding, dataset ->
            plots.add(XYPlot(dataset, null, NumberAxis("${binding.prettyName} (${binding.units})"), DefaultXYItemRenderer()).apply {
                renderer.setPaint(Color.BLACK)
                renderer.setShape(ShapeUtilities.createDiamond(Constants.POINT_SIZE))
            })
        }

        return plots
    }


    init {
        ActiveDataManager.addChartHook {
            clearPlots()
            plot.domainAxis = NumberAxis(it.getXBinding()!!.prettyName)
            val newPlots = buildPlots(it)
            newPlots.forEach {
                plot.add(it)
            }
        }
    }

    fun getChartPanel() = panel

    fun autoRange() = panel.restoreAutoBounds()
}