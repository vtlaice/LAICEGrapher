package edu.vt.ece.laice.grapher.sink

import edu.vt.ece.laice.grapher.GeneralExecutor
import java.util.*
import kotlin.properties.Delegates

/**
 * Created by cameronearle on 6/30/17.
 */
object TaskSink {
    data class Task(val name: String) {
        var progress: Int by Delegates.observable(0) {
            _, _, new ->
            TaskSink.taskUpdate()
        }

        fun finish() {
            TaskSink.activeTasks.removeElement(this)
            TaskSink.taskUpdate()
        }
    }

    private val activeTasks = Vector<Task>()
    private val drains = Vector<(List<Task>) -> Unit>()

    fun createTask(name: String): Task {
        val task = Task(name)
        activeTasks.addElement(task)
        taskUpdate()
        return task
    }

    fun registerDrain(drain: (List<Task>) -> Unit) {
        drains.addElement(drain)
        taskUpdate()
    }

    @Synchronized private fun taskUpdate() {
        val listSnapshot = activeTasks.toList()
        GeneralExecutor.submit {
            drains.forEach {
                it(listSnapshot)
            }
        }
    }
}