package com.ues.gpo7fb16014.models

import com.google.gson.annotations.SerializedName

data class Docente (
    @SerializedName("id")
    var id : Int = 0,
    @SerializedName("cod_docente")
    var cod_docente : String = "",
    @SerializedName("nombre")
    var nombre : String = "",
)