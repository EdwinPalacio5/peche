package com.ues.gpo7fb16014.models

import com.google.gson.annotations.SerializedName

data class Revision(
    @SerializedName("id")
    var id : Int = 0,
    @SerializedName("test_id")
    var test_id : Int = 0,
    @SerializedName("alumno_id")
    var alumno_id : Int = 0,
    @SerializedName("location_id")
    var location_id : Int = 0,
    @SerializedName("motivo_id")
    var motivo_id : Int = 0,
    @SerializedName("asistencia")
    var asistencia : Int = 0,
    @SerializedName("nota_original")
    var nota_original : Double = 0.0,
    @SerializedName("nueva_nota")
    var nueva_nota : Double = 0.0,
    @SerializedName("observaciones")
    var observaciones : String = "",
    @SerializedName("created_at")
    var created_at : String = "",
    @SerializedName("updated_at")
    var updated_at : String = "",
    @SerializedName("cod_eva")
    var cod_eva : String = "",
    @SerializedName("cod_motivo")
    var cod_motivo : String = "",
    @SerializedName("cod_local")
    var cod_local : String = "",
    @SerializedName("carnet")
    var carnet : String = "",
    var filename : String = ""
)