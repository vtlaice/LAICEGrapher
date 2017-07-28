package edu.vt.ece.laice.grapher.data

/**
 * Created by cameronearle on 7/26/17.
 */
interface Datasource {
    fun getOrbitID(orbit: Int): Int
    fun request(bindingsList: List<SingleBinding>, startID: Int, endID: Int): DatabaseResponse
}