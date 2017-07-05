package edu.vt.ece.laice.grapher.gui.splash

import javax.swing.*
import javax.swing.plaf.basic.BasicProgressBarUI
import java.awt.*
import java.awt.geom.Point2D
import java.awt.geom.Rectangle2D

/**
 * Created by cameronearle on 7/5/17.
 */
class SplashProgressBar : BasicProgressBarUI() {
    var r = Rectangle()

    override fun paintIndeterminate(g: Graphics?, c: JComponent?) {
        val g2d = g as Graphics2D
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        r = getBox(r)
        g.color = progressBar.foreground
        g.background = progressBar.background
        g.fillRect(r.x, r.y, r.width, r.height)
    }
}
