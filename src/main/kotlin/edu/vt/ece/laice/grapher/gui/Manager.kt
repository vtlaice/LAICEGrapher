package edu.vt.ece.laice.grapher.gui

import java.awt.Component
import java.awt.Container
import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.SwingUtilities

/**
 * Created by cameronearle on 6/23/17.
 */
abstract class Manager<out T>(protected val form: T) {
    protected val frame = JFrame()
    fun init(title: String, closeAction: Int = JFrame.DISPOSE_ON_CLOSE, relativeTo: Component? = null) {
        frame.minimumSize = Dimension(100, 100)
        frame.title = title
        frame.defaultCloseOperation = closeAction
        initTasks()
        frame.pack()
        frame.setLocationRelativeTo(relativeTo)
        postInit()
    }

    fun show() {
        frame.isVisible = true
    }

    fun hide() {
        frame.isVisible = false
    }

    fun repaint() {
        SwingUtilities.updateComponentTreeUI(frame)
    }

    protected fun setMainPanel(panel: Container) {
        frame.contentPane = panel
    }

    open protected fun initTasks() {}
    open protected fun postInit() {}

}