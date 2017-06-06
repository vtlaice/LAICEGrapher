package edu.vt.ece.laice.grapher.gui

import java.awt.Component
import java.awt.Container
import javax.swing.JFrame
import javax.swing.SwingUtilities

/**
 * Created by cameronearle on 6/2/17.
 */
abstract class ViewManager {
    protected val frame = JFrame()

    fun init(title: String) {
        frame.title = title
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        initTasks() //Execute custom init tasks
        frame.pack()
    }

    fun setVisible(value: Boolean) {
        frame.isVisible = value
    }

    fun repaint() {
        SwingUtilities.updateComponentTreeUI(frame)
    }

    open protected fun initTasks() {}
    protected fun setMainPanel(panel: Container) {
        frame.contentPane = panel
    }
}