package edu.vt.ece.laice.grapher

import com.alee.laf.WebLookAndFeel
import edu.vt.ece.laice.grapher.chart.MainChart
import edu.vt.ece.laice.grapher.data.Database
import edu.vt.ece.laice.grapher.data.SingleBinding
import edu.vt.ece.laice.grapher.data.YBindings
import edu.vt.ece.laice.grapher.gui.main.MainFormManager
import edu.vt.ece.laice.grapher.gui.splash.SplashForm
import edu.vt.ece.laice.grapher.gui.splash.SplashFormManager
import edu.vt.ece.laice.grapher.sink.TaskSink
import org.jfree.data.xy.XYSeries
import java.sql.SQLException
import java.util.*
import javax.swing.JDialog
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
        //Database.connect("laice", "laice", "LaiceTest123*", "192.168.21.131")
        dbTask.progress = 100
        lafTask.finish()
    } catch (e: SQLException) {
        JOptionPane.showMessageDialog(JFrame(), "Error connecting to database!", "Error", JOptionPane.ERROR_MESSAGE)
        System.exit(1)
    }

    dbTask.progress = 100
    lafTask.finish()

    MainFormManager.init("LAICE Grapher", JFrame.EXIT_ON_CLOSE)
    MainFormManager.show()

    //println(Database.buildAndExecute(YBindings.getBindingFromGroup("currentSamples", "currentSample64_Amps") as SingleBinding, 1, 3))

    println("done")
    SplashFormManager.hide()


    val data = XYSeries("Series 1")
    val data2 = XYSeries("Series 2")
    val data3 = XYSeries("Series 3")


    for (i in 0..30) {
        data.add(i, Random().nextInt(100))
    }

    for (i in 0..30) {
        data2.add(i, Random().nextInt(100))
    }

    for (i in 0..30) {
        data3.add(i, Random().nextInt(100))
    }

    MainChart.setXAxis("X Test")

    MainChart.addNewPlot(data, "Y Test 1")
    MainChart.addNewPlot(data2, "Y Test 2")
    MainChart.addNewPlot(data3, "Y Test 3")


}