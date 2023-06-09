package com.ues.gpo7fb16014.models

import com.google.gson.annotations.SerializedName

data class Materia (
    @SerializedName("id")
    var id : Int = 0,
    @SerializedName("cod_materia")
    var cod_materia : String = "",
    @SerializedName("nombre")
    var nombre : String = "",
    @SerializedName("area")
    var area : String = "",
)