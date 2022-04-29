package com.example.marvel.marvelapi

import com.example.marvel.models.DatosRespuesta
import com.example.marvel.models.MarvelRespuesta
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface MarvelapiService {
    @GET("characters?limit=100&ts=1650279504&apikey=d337692bcf63b1287030999cbab51cca&hash=b7086fa6472d382c3f7572f4b0c98dd5")
    fun obtenerLista() :Call<MarvelRespuesta<DatosRespuesta>>

}