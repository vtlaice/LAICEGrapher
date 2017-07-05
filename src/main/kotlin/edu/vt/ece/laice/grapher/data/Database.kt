package edu.vt.ece.laice.grapher.data

import java.sql.*
import java.util.*

/**
 * Created by cameronearle on 6/26/17.
 */
object Database {
    lateinit var connection: Connection

    var lastBinding: SingleBinding? = null
    var lastStartOrbit: Long? = null
    var lastEndOrbit: Long? = null

    fun connect(database: String, username: String, password: String, server: String) {
        connection = DriverManager.getConnection("jdbc:mysql://$server:3306/$database?useSSL=false", username, password)
    }


    @Synchronized fun buildAndExecute(desiredBinding: SingleBinding?, desiredStartOrbit: Long?, desiredEndOrbit: Long?): DatabaseResponse {
        val binding: SingleBinding
        val startOrbit: Long
        val endOrbit: Long

        if (desiredBinding != null) {
            binding = desiredBinding
        } else {
            if (lastBinding != null) {
                binding = lastBinding!!
            } else {
                return DatabaseResponse(false, reason = "No provided or previous binding")
            }
        }

        if (desiredStartOrbit != null) {
            startOrbit  = desiredStartOrbit
        } else {
            if (lastStartOrbit != null) {
                startOrbit = lastStartOrbit!!
            } else {
                return DatabaseResponse(false, reason = "No provided or previous startOrbit")
            }
        }

        if (desiredEndOrbit != null) {
            endOrbit = desiredEndOrbit
        } else {
            if (lastEndOrbit != null) {
                endOrbit = lastEndOrbit!!
            } else {
                return DatabaseResponse(false, reason = "No provided or previous endOrbit")
            }
        }

        lastBinding = binding
        lastStartOrbit = startOrbit
        lastEndOrbit = endOrbit

        val statement = connection.prepareStatement("SELECT ${binding.internalName} " +
                                                    "FROM ${binding.table.tableName} " +
                                                    "WHERE orbitNumber BETWEEN $startOrbit AND $endOrbit")

        var result: ResultSet? = null
        try {
            result = statement.executeQuery()
            if (result != null) {
                if (result.next()) {
                    return DatabaseResponse(true, Datapoint(binding, result.getDouble(1)))
                } else {
                    return DatabaseResponse(false, reason = "Empty set")
                }
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
}