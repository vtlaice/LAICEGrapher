package edu.vt.ece.laice.grapher.data

/**
 * Created by cameronearle on 6/23/17.
 */
enum class Tables(val tableName: String, val prettyName: String, val excluded: Boolean = false) {
    X("subsystem_rpa_data", "", true),
    LIIB("subsystem_liib_data", "LIIB"),
    SNEUPI("subsystem_sneupi_data", "SNeuPI"),
    LINAS("subsystem_linas_data", "LINAS"),
    RPA("subsystem_rpa_data", "RPA");

    override fun toString() = prettyName
}