package edu.vt.ece.laice.grapher.gui.splash

import com.intellij.uiDesigner.core.GridConstraints
import edu.vt.ece.laice.grapher.gui.Manager
import edu.vt.ece.laice.grapher.sink.TaskSink
import java.awt.Color
import javax.swing.ImageIcon

/**
 * Created by cameronearle on 6/30/17.
 */
object SplashFormManager: Manager<SplashForm>(SplashForm()) {
    override fun initTasks() {
        setMainPanel(form.mainPanel)

        form.mainPanel.add(ImagePanel(), GridConstraints())
        form.progressBar.foreground = Color(46, 154, 254)
        form.progressBar.background = Color.white
        form.progressBar.ui = SplashProgressBar()

        frame.isUndecorated = true


        TaskSink.registerDrain {
            if (it.size > 1) {
                form.progressBar.string = "${it.size} tasks running"
                form.progressBar.isIndeterminate = true
            } else if (it.size == 1) {
                form.progressBar.string = it[0].name
                form.progressBar.isIndeterminate = false
                form.progressBar.value = it[0].progress
            } else {
                form.progressBar.string = "No tasks running"
                form.progressBar.isIndeterminate = false
                form.progressBar.value = 0
            }
        }
    }

    override fun postInit() {
        frame.setLocationRelativeTo(null)
    }
}