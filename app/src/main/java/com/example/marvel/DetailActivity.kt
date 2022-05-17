package com.example.marvel

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.marvel.models.*
import com.example.marvel.services.MarvelapiService
import com.example.marvel.services.OnClickListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailActivity : AppCompatActivity(), OnClickListener {
    private lateinit var mAdapter:ComicAdapter
    private lateinit var mAdapter2:ComicAdapter
    private lateinit var mGridLayout:GridLayoutManager
    private lateinit var mGridLayout2:GridLayoutManager
    var retrofit:Retrofit?=null
    var retrofit2:Retrofit?=null
    var todos:ArrayList<DatosComics>? =null
    var todos2:ArrayList<DatosComics>? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val superheroes = intent.getSerializableExtra("superheroe") as? Superheroes


        findViewById<TextView>(R.id.NombreTextView).setText(superheroes!!.name)
        if(superheroes!!.description.isEmpty()){
            findViewById<TextView>(R.id.DescripcionTextView).setText("Description Not Found")
        }
        else{
            findViewById<TextView>(R.id.DescripcionTextView).setText(superheroes!!.description)
        }


        var url="${superheroes.thumbnail.path}.${superheroes.thumbnail.extension}"
        var splits=url.split(":")
        var urlFinal="${splits[0]}s:${splits[1]}"

        Glide.with(applicationContext)
            .load(urlFinal)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(findViewById(R.id.imageView))

        setupRecyclerView(superheroes)
        setupRecyclerView2(superheroes)
    }

    private fun setupRecyclerView(superheroes:Superheroes) {
        mAdapter = ComicAdapter(ArrayList(),this)
        mGridLayout = GridLayoutManager(this, resources.getInteger(R.integer.main_columns),GridLayoutManager.HORIZONTAL,false)

        val url2 =superheroes?.comics?.collectionURI.toString().split("comics")

        var splits2=url2[0].split(":")
        var urlFinal2="${splits2[0]}s:${splits2[1]}"
        retrofit= Retrofit.Builder()
            .baseUrl(urlFinal2)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        obtenerDatos()
        findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = mGridLayout
            adapter = mAdapter
        }

    }

    private fun setupRecyclerView2(superheroes:Superheroes) {
        mAdapter2 = ComicAdapter(ArrayList(),this)
        mGridLayout2 = GridLayoutManager(this, resources.getInteger(R.integer.main_columns),GridLayoutManager.HORIZONTAL,false)

        val url3 =superheroes?.events?.collectionURI.toString().split("events")

        var splits3=url3[0].split(":")
        var urlFinal3="${splits3[0]}s:${splits3[1]}"
        Log.d("hola",urlFinal3)
        retrofit2= Retrofit.Builder()
            .baseUrl(urlFinal3)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        obtenerDatos2()
        findViewById<RecyclerView>(R.id.my_recycler_view2).apply {
            setHasFixedSize(true)
            layoutManager = mGridLayout2
            adapter = mAdapter2
        }

    }

    private fun obtenerDatos() {
        var service: MarvelapiService =retrofit!!.create(MarvelapiService::class.java)


        var marvelRespuestaCall: Call<MarvelRespuesta<DatosRespuesta2>> = service.obtenerListaComics()

        marvelRespuestaCall.enqueue(object: Callback<MarvelRespuesta<DatosRespuesta2>> {
            override fun onResponse(call: Call<MarvelRespuesta<DatosRespuesta2>>, response: Response<MarvelRespuesta<DatosRespuesta2>>) {
                if(response.isSuccessful){
                    var marvelrespuesta : MarvelRespuesta<DatosRespuesta2>?=response.body()
                    var datos:ArrayList<DatosComics> =marvelrespuesta!!.data!!.results
                    todos=datos
                    if(todos!!.size==0){
                        findViewById<TextView>(R.id.comicsTextView).visibility=View.VISIBLE
                        findViewById<RecyclerView>(R.id.my_recycler_view).visibility=View.GONE
                    }
                    else{
                        mAdapter.setStores(todos!!)
                    }
                }
                else{
                    Toast.makeText(applicationContext,"Ha habido un error", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<MarvelRespuesta<DatosRespuesta2>>, t: Throwable) {
                Toast.makeText(applicationContext,"Ha habido un error", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun obtenerDatos2() {
        var service: MarvelapiService =retrofit2!!.create(MarvelapiService::class.java)


        var marvelRespuestaCall: Call<MarvelRespuesta<DatosRespuesta2>> = service.obtenerListaEventos()

        marvelRespuestaCall.enqueue(object: Callback<MarvelRespuesta<DatosRespuesta2>> {
            override fun onResponse(call: Call<MarvelRespuesta<DatosRespuesta2>>, response: Response<MarvelRespuesta<DatosRespuesta2>>) {
                if(response.isSuccessful){
                    var marvelrespuesta : MarvelRespuesta<DatosRespuesta2>?=response.body()
                    var datos:ArrayList<DatosComics> =marvelrespuesta!!.data!!.results
                    todos2=datos
                    if(todos2!!.size==0){
                        findViewById<TextView>(R.id.eventosTextView).visibility=View.VISIBLE
                        findViewById<RecyclerView>(R.id.my_recycler_view2).visibility=View.GONE
                    }
                    else{
                        mAdapter2.setStores(todos2!!)
                    }
                }
                else{
                    Toast.makeText(applicationContext,"Ha habido un error", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<MarvelRespuesta<DatosRespuesta2>>, t: Throwable) {
                Toast.makeText(applicationContext,"Ha habido un error", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onDetail(superheroes: Superheroes) {
        TODO("Not yet implemented")
    }

    override fun onDetailComics(comic: DatosComics) {
        val dialog = layoutInflater.inflate(R.layout.dialog_comic, null)

        var url="${comic.thumbnail.path}.${comic.thumbnail.extension}"
        var splits=url.split(":")
        var urlFinal="${splits[0]}s:${splits[1]}"

        Glide.with(this)
            .load(urlFinal)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(dialog.findViewById<ImageView>(R.id.imageView2))

        dialog.findViewById<TextView>(R.id.nombreTextView).text=comic.title
        dialog.findViewById<TextView>(R.id.descripcionTextView).text=comic.description

        var dialogq = MaterialAlertDialogBuilder(this).apply {
            setView(dialog)
        }.show()
    }
}