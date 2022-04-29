package com.example.marvel.models

import com.google.gson.annotations.SerializedName

data class ImgRespuesta(@SerializedName("path")
                        val path:String,
                        @SerializedName("extension")
                        val extension:String)
