package edu.vt.ece.laice.grapher.data

import java.util.Random


/**
 * Created by cameronearle on 7/26/17.
 */

class FakeDatabase: Datasource {
    override fun request(bindingsList: List<SingleBinding>, startID: Int, endID: Int): DatabaseResponse {
        println("FAKEREQ - bL:${bindingsList.joinToString {it.internalName}}   s:$startID   e:$endID")
        println("Table: ${bindingsList[0]!!.table}")
        val map = LinkedHashMap<SingleBinding, ArrayList<Double>>()
        val random = Random()
        if (startID == endID) {
            bindingsList.forEach {
                map.put(it, arrayListOf())
                map[it]!!.add(random.nextDouble())
            }
        } else {
            bindingsList.forEach {
                map.put(it, arrayListOf())
                for (i in 0 until (endID - startID)) {
                    map[it]!!.add(random.nextDouble())
                }
            }
        }

        println("FAKEREP - m:$map")

        return DatabaseResponse(true, map)
    }

    override fun getOrbitID(orbit: Int): Int {
        return orbit * 20
    }
}