package edu.vt.ece.laice.grapher

import com.alee.laf.WebLookAndFeel
import edu.vt.ece.laice.grapher.gui.MainViewManager
import javax.swing.tree.DefaultMutableTreeNode

/**
 * Created by cameronearle on 6/2/17.
 */

fun main(args: Array<String>) {
    WebLookAndFeel.install() //Install WebLAF, which we use to render elements of the gui

    MainViewManager.init("LAICE Grapher") //Initialize the view manager, which will control the gui
    MainViewManager.setVisible(true)

}