package edu.vt.ece.laice.grapher.data

/**
 * Created by cameronearle on 7/28/17.
 */

class DatapointList : ArrayList<Datapoint>() {
    fun getXBinding(): SingleBinding? {
        if (isNotEmpty()) {
            return get(0).xValue.first
        } else {
            return null
        }
    }

    fun getXValues(): ArrayList<Double> {
        val values = arrayListOf<Double>()
        forEach {
            values.add(it.xValue.second)
        }
        return values
    }

    fun getYValues(): LinkedHashMap<SingleBinding, ArrayList<Double>> {
        val values = LinkedHashMap<SingleBinding, ArrayList<Double>>()
        if (isEmpty()) {
            return values
        }
        val yBindings = arrayListOf<SingleBinding>()
        yBindings.addAll(get(0).yValue.keys)
        yBindings.forEach {
            values.putIfAbsent(it, arrayListOf())
        }
        values.forEach {
            binding, list ->
            forEach {
                list.add(it.yValue[binding]!!)
            }
        }
        return values
    }

    fun getValues(): LinkedHashMap<SingleBinding, List<Pair<Double, Double>>> {
        val toReturn = LinkedHashMap<SingleBinding, List<Pair<Double, Double>>>()
        val xValues = getXValues()
        val yValues = getYValues()
        val pairs = arrayListOf<Pair<Double, Double>>()
        yValues.keys.forEach {
            toReturn.put(it, (0 until xValues.size).map { i -> Pair(xValues[i], yValues[it]!![i]) })
        }
        return toReturn
    }
}