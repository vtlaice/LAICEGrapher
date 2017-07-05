package edu.vt.ece.laice.grapher.data

/**
 * Created by cameronearle on 6/30/17.
 */
data class DatabaseResponse (val successful: Boolean, val data: Datapoint? = null, val reason: String = "")