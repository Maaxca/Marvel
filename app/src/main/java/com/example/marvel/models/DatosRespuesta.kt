package com.example.marvel.models

import com.google.gson.annotations.SerializedName

data class DatosRespuesta(
    @SerializedName("offset")
    val offset:Int,
    @SerializedName("limit")
    val limit:Int,
    @SerializedName("total")
    val total:Int,
    @SerializedName("count")
    val count:Int,
    @SerializedName("results")
    val results:ArrayList<Superheroes>
)
