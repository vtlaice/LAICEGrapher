package edu.vt.ece.laice.grapher.data

import edu.vt.ece.laice.grapher.GeneralExecutor
import java.util.*

/**
 * Created by cameronearle on 6/30/17.
 */
object ActiveDataManager {
    var activeStartOrbit = 1; private set
    var activeInterval = 5400; private set
    var activeXBinding = XBindings.getBinding("epoch"); private set
    val activeYBindings = LinkedList<SingleBinding>()
    var activeMode = DataMode.GENERAL; private set
    var lock = false; private set

    val activeData = DatapointList()


    @Synchronized fun updateOrbitRange(startOrbit: Int, interval: Interval) {
        if (!lock && (activeStartOrbit != startOrbit || activeInterval != interval.points)) {
            activeStartOrbit = startOrbit
            activeInterval = interval.points
            GeneralExecutor.submit { recalculate() }
            println("START: $activeStartOrbit, INT: $activeInterval")
        }
    }

    @Synchronized fun setYBindings(bindings: List<SingleBinding>) {
        if (!lock && (activeData.toList() != bindings)) {
            activeYBindings.clear()
            activeYBindings.addAll(bindings)
            GeneralExecutor.submit { recalculate() }
        }
    }


    @Synchronized fun setXBinding(binding: SingleBinding) {
        if (!lock && (binding.table == Tables.X)) {
            if (activeXBinding != binding) {
                activeXBinding = binding
                GeneralExecutor.submit { recalculate() }
            }
        }
    }

    @Synchronized fun setMode(mode: DataMode) {
        if (mode != activeMode) {
            activeMode = mode
            handleModeChange()
            GeneralExecutor.submit { recalculate() }
        }
    }

    private fun handleModeChange() {
        when (activeMode) {
            DataMode.GENERAL -> {
                lock = false
            }
            DataMode.RPA -> {
                lock = true
                activeXBinding = XBindings.getBinding("_id")
                activeYBindings.clear()
                val currentSamples = YBindings.getBinding("currentSamples") as BindingGroup
                val voltageSamples = YBindings.getBinding("voltageSamples") as BindingGroup


            }
        }
    }

    private val chartHooks = hashMapOf<DataMode, ArrayList<(DatapointList) -> Unit>>()
    fun addChartHook(mode: DataMode = DataMode.GENERAL, action: (DatapointList) -> Unit) {
        chartHooks.putIfAbsent(mode, arrayListOf())
        chartHooks[mode]?.add(action)
    }
    private fun callAllHooks() {
        chartHooks[activeMode]?.forEach {
            it(activeData)
        }
    }

    @Synchronized private fun recalculate() {
        /*
        To recalculate the data set, we are going to iterate through it and check the starting and ending
        times of each binding.  If they match the active start and end times, they do not require modification.
        If they do not, we drill in and see which field doesn't match.  We then request the needed data and fill it in.
         */
        try {
            val startID = Database.getOrbitID(activeStartOrbit)
            val endID = startID + activeInterval
            val xResponse = Database.request(listOf(activeXBinding), startID, endID)
            val yResponse = Database.request(activeYBindings, startID, endID)
            if (xResponse.successful && yResponse.successful) {
                activeData.clear()

                val xValues = xResponse.data[activeXBinding]!!
                val yValues = yResponse.data

                for (i in 0 until xValues.size) {
                    val currentYValues = LinkedHashMap<SingleBinding, Double>()
                    yValues.forEach { binding, values ->
                        currentYValues.put(binding, values[i])
                    }
                    activeData.add(Datapoint(activeXBinding to xValues[i], currentYValues))
                }
                println(activeData.getValues())
                callAllHooks()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }




}