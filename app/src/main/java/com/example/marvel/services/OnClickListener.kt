package com.example.marvel.services

import com.example.marvel.models.DatosComics
import com.example.marvel.models.Superheroes

interface OnClickListener {

    fun onDetail(superheroes: Superheroes)

    fun onDetailComics(superheroes: DatosComics)
}