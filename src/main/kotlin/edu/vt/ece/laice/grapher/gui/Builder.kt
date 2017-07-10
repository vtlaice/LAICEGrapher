package edu.vt.ece.laice.grapher.gui

import java.awt.Component
import java.awt.Dimension
import javax.swing.JDialog
import javax.swing.SwingUtilities

/**
 * Created by cameronearle on 7/7/17.
 */

abstract class Builder<out T: JDialog>(protected val dialog: T) {
    fun init(title: String, closeAction: Int = JDialog.DISPOSE_ON_CLOSE, relativeTo: Component? = null) {
        dialog.minimumSize = Dimension(100, 100)
        dialog.title = title
        dialog.defaultCloseOperation = closeAction
        initTasks()
        dialog.pack()
        dialog.setLocationRelativeTo(relativeTo)
        postInit()
    }

    fun show() {
        dialog.isVisible = true
    }

    fun hide() {
        dialog.isVisible = false
    }

    fun repaint() {
        SwingUtilities.updateComponentTreeUI(dialog)
    }

    fun dispose() {
        dialog.dispose()
    }

    open protected fun initTasks() {}
    open protected fun postInit() {}
}