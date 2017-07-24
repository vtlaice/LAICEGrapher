package edu.vt.ece.laice.grapher.data

import java.sql.*
import java.util.*

/**
 * Created by cameronearle on 6/26/17.
 */
object Database {
    lateinit var connection: Connection
    private val lock = Any()

    fun connect(database: String, username: String, password: String, server: String) {
        connection = DriverManager.getConnection("jdbc:mysql://$server:3306/$database?useSSL=false", username, password)
    }

    private fun findOrbitID(orbit: Long): Int {
        val query = "SELECT _id FROM LIIB WHERE orbitNumber = $orbit"
        val statement = connection.prepareStatement(query)
        var result: ResultSet? = null
        try {
            result = statement.executeQuery()
            if (result != null) {
                return result.getInt(0)
            } else {
                return -1
            }
        } catch (e: SQLException) {
            e.printStackTrace()
            return -1
        } finally {
            statement.close()
        }
    }

    fun request(bindingsList: List<SingleBinding>, startOrbit: Long, interval: Interval): DatabaseResponse {
        val startPoint = findOrbitID(startOrbit)
        if (startPoint == -1) {
            return DatabaseResponse(false, reason = "Invalid data range")
        }
        val endPoint = startPoint + interval.points

        return request(bindingsList, startPoint, endPoint)
    }


    @Synchronized fun request(bindingsList: List<SingleBinding>, startID: Int, endID: Int): DatabaseResponse {
        if (bindingsList.isEmpty()) {
            return DatabaseResponse(false, reason = "Empty request set")
        }

        val sorted = hashMapOf<Tables, ArrayList<SingleBinding>>()
        bindingsList.forEach {
            sorted.putIfAbsent(it.table, arrayListOf())
            sorted[it.table]!!.add(it)
        }

        val masterMap = hashMapOf<SingleBinding, List<Double>>()

        sorted.forEach {
            val table = it.key
            val bindings = it.value

            var query = "SELECT " //Initial query
            query += bindings.joinToString(postfix = " ") { it.internalName }
            query += "FROM ${table.tableName} " //Add the table string
            query += "WHERE _id " //Add the field string

            if (endID == startID) {
                query += "= $startID"
            } else {
                query += "BETWEEN $startID AND $endID"
            }

            val statement = connection.prepareStatement(query) //Build the query

            var result: ResultSet? = null //Set up a result set to store the raw results
            try {
                result = statement.executeQuery() //Execute the query
                if (result != null) {
                    val map = hashMapOf<SingleBinding, ArrayList<Double>>()

                    while (result.next()) {
                        bindings.forEach {
                            map.putIfAbsent(it, arrayListOf())
                            map[it]!!.add(result!!.getDouble(it.internalName))
                        }
                    }
                    masterMap.putAll(map)
                } else {
                    return DatabaseResponse(false, reason = "Null set")
                }
            } catch (e: SQLException) {
                e.printStackTrace()
                return DatabaseResponse(false, reason = "SQL Query failed (${e.errorCode}")
            } finally {
                result?.close()
            }

        }

        return DatabaseResponse(true, masterMap)
    }
}