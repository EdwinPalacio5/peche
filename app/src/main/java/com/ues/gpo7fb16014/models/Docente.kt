package com.ues.gpo7fb16014.models

import com.google.gson.annotations.SerializedName

data class Docente (
    @SerializedName("cod_docente")
    var cod_docente : String = "",
    @SerializedName("nombre")
    var nombre : String = "",
)