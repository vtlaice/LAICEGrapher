package edu.vt.ece.laice.grapher.gui

import com.intellij.uiDesigner.core.GridConstraints
import edu.vt.ece.laice.grapher.chart.ChartContainer
import java.awt.Dimension
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.event.ComponentListener

/**
 * Created by cameronearle on 6/6/17.
 */
object FullscreenGraphDialogManager {
    private val dialog = FullscreenGraphDialog()

    init {
        dialog.graphPanel.add(ChartContainer.getChartPanel(), GridConstraints().apply {
            fill = GridConstraints.FILL_BOTH
        })

        dialog.addComponentListener(object : ComponentAdapter() {
            override fun componentResized(e: ComponentEvent) {
                dialog.widthField.text = dialog.graphPanel.width.toString()
                dialog.heightField.text = dialog.graphPanel.height.toString()
            }
        })

        dialog.minimumSize = Dimension(100, 100)

        dialog.pack()
        dialog.isVisible = false
    }

    fun show() {
        dialog.isVisible = true
    }

    fun hide() {
        dialog.isVisible = false
    }
}