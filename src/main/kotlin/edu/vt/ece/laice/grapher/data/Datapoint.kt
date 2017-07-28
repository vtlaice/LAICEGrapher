package edu.vt.ece.laice.grapher.data

/**
 * Created by cameronearle on 6/28/17.
 */

data class Datapoint(val xValue: Pair<SingleBinding, Double>, val yValue: LinkedHashMap<SingleBinding, Double>)