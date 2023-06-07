package com.ues.gpo7fb16014.models

import com.google.gson.annotations.SerializedName

data class Alumno (
    @SerializedName("id")
    var id : Int = 0,
    @SerializedName("carnet")
    var carnet : String = "",
    @SerializedName("nombre")
    var nombre : String = "",
    @SerializedName("carrera")
    var carrera : String = "",
)