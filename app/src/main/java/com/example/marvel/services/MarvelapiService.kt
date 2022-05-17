package com.example.marvel.services

import com.example.marvel.models.DatosRespuesta
import com.example.marvel.models.DatosRespuesta2
import com.example.marvel.models.MarvelRespuesta
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelapiService {
    @GET("characters?limit=100&ts=1650279504&apikey=d337692bcf63b1287030999cbab51cca&hash=b7086fa6472d382c3f7572f4b0c98dd5")
    fun obtenerLista(@Query("offset")of:String) :Call<MarvelRespuesta<DatosRespuesta>>

    @GET("comics?ts=1650279504&apikey=d337692bcf63b1287030999cbab51cca&hash=b7086fa6472d382c3f7572f4b0c98dd5")
    fun obtenerListaComics() :Call<MarvelRespuesta<DatosRespuesta2>>

    @GET("events?ts=1650279504&apikey=d337692bcf63b1287030999cbab51cca&hash=b7086fa6472d382c3f7572f4b0c98dd5")
    fun obtenerListaEventos() :Call<MarvelRespuesta<DatosRespuesta2>>

}