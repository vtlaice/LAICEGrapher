package edu.vt.ece.laice.grapher

import com.alee.laf.WebLookAndFeel
import edu.vt.ece.laice.grapher.chart.MainChart
import edu.vt.ece.laice.grapher.data.Database
import edu.vt.ece.laice.grapher.data.MySQLDatabase
import edu.vt.ece.laice.grapher.gui.main.MainFormManager
import edu.vt.ece.laice.grapher.gui.splash.SplashFormManager
import edu.vt.ece.laice.grapher.sink.TaskSink
import org.jfree.data.xy.XYSeries
import java.sql.SQLException
import javax.swing.JFrame
import javax.swing.JOptionPane

/**
 * Created by cameronearle on 6/23/17.
 */

fun main(args: Array<String>) {
    GeneralExecutor //Warm up the executor

    SplashFormManager.init("")
    SplashFormManager.show()

    try {
        Thread.sleep(1000)
    } catch(e: Exception) {
    }

    val lafTask = TaskSink.createTask("Installing WebLAF")

    WebLookAndFeel.install() //Install the LAF

    lafTask.progress = 100
    lafTask.finish()

    val dbTask = TaskSink.createTask("Connecting to server")

    try {
        (Database as MySQLDatabase).connect("moc_vt_clone", "root", args[0], args[1])
        dbTask.progress = 100
        dbTask.finish()
    } catch (e: SQLException) {
        JOptionPane.showMessageDialog(JFrame(), "Error connecting to database (${e.errorCode})\n${e.localizedMessage}", "Error", JOptionPane.ERROR_MESSAGE)
        //System.exit(1)
    }

    MainFormManager.init("LAICE Grapher", JFrame.EXIT_ON_CLOSE)
    MainFormManager.show()

    //println(MySQLDatabase.buildAndExecute(YBindings.getBindingFromGroup("currentSamples", "currentSample64_Amps") as SingleBinding, 1, 3))

    println("done")
    SplashFormManager.hide()


}