package edu.vt.ece.laice.grapher.data

import edu.vt.ece.laice.grapher.GeneralExecutor
import java.util.*

/**
 * Created by cameronearle on 6/30/17.
 */
object ActiveDataManager {
    var activeStartOrbit = 0; private set
    var activeInterval = 0; private set

    fun updateOrbitRange(startOrbit: Int, interval: Interval) {
        if (activeStartOrbit != startOrbit || activeInterval != interval.points) {
            activeStartOrbit = startOrbit
            activeInterval = interval.points
            GeneralExecutor.submit { recalculate() }
            println("START: $activeStartOrbit, INT: $activeInterval")
        }
    }

    val activeData = LinkedHashMap<Binding, LinkedList<Pair<Long, Double>>>()

    fun updateBindings(bindings: List<Binding>) {
        val activeBindings = activeData.keys.toList()
        //First we'll remove any old bindings
        activeBindings.forEach {
            if (!bindings.contains(it)) {
                activeData.remove(it)
            }
        }
        //Now we'll add new bindings
        bindings.forEach {
            if (!activeBindings.contains(it)) {
                activeData.put(it, LinkedList())
            }
        }
        //Now that the lists are equivalent, we can sort the map
        val oldData = activeData.toMap()
        activeData.clear()
        bindings.forEach {
            activeData.put(it, oldData[it]!!)
        }
        println(activeData)
    }

    @Synchronized private fun recalculate() {
        /*
        To recalculate the data set, we are going to iterate through it and check the starting and ending
        times of each binding.  If they match the active start and end times, they do not require modification.
        If they do not, we drill in and see which field doesn't match.  We then request the needed data and fill it in.
         */
        activeData.forEach {
            binding, value ->
            //var currentStartTime = activeStartTime
            //var currentEndTime = activeEndTime
        }
    }


}