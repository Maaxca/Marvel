package com.example.marvel.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DatosComics(
    @SerializedName("title")
    val title:String,
    @SerializedName("description")
    val description:String,
    @SerializedName("thumbnail")
    val thumbnail:ImgRespuesta,
):Serializable
