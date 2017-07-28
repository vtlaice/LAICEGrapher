package edu.vt.ece.laice.grapher.data

/**
 * Created by cameronearle on 6/30/17.
 */
data class DatabaseResponse (val successful: Boolean, val data: LinkedHashMap<SingleBinding, ArrayList<Double>> = LinkedHashMap(), val reason: String = "")