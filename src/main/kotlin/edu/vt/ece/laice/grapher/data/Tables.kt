package edu.vt.ece.laice.grapher.data

/**
 * Created by cameronearle on 6/23/17.
 */
enum class Tables(val tableName: String, val prettyName: String, val excluded: Boolean = false) {
    X("", "", true),
    LIIB("", "LIIB"),
    SNEUPI("sensor_sneupi_data", "SNeuPI"),
    LINAS("sensor_linas_data", "LINAS"),
    RPA("sensor_rpa_data", "RPA"),
}