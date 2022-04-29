package com.example.marvel

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.marvel.marvelapi.MarvelapiService
import com.example.marvel.models.CharacterAdapter
import com.example.marvel.models.DatosRespuesta
import com.example.marvel.models.MarvelRespuesta
import com.example.marvel.models.Superheroes
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var mAdapter:CharacterAdapter
    private lateinit var mGridLayout:GridLayoutManager
    var retrofit:Retrofit?=null
    var todos:ArrayList<Superheroes>? =null
    var i:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        retrofit=Retrofit.Builder()
            .baseUrl("https://gateway.marvel.com/v1/public/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        setupRecyclerView()

        findViewById<RecyclerView>(R.id.recyclerView).setOnScrollChangeListener(
            View.OnScrollChangeListener { v, scrollX, scrollY, _, _ ->
                if (!v.canScrollVertically(1)){
                    i+=100
                    obtenerDatos(i.toString())
                }

            })

}

    private fun setupRecyclerView() {
        mAdapter = CharacterAdapter(ArrayList())
        mGridLayout = GridLayoutManager(this, resources.getInteger(R.integer.main_columns))

        obtenerDatos(i.toString())
        findViewById<RecyclerView>(R.id.recyclerView).apply {
            setHasFixedSize(true)
            layoutManager = mGridLayout
            adapter = mAdapter
        }

    }

    private fun obtenerDatos(of:String) {
        var service:MarvelapiService=retrofit!!.create(MarvelapiService::class.java)


        var marvelRespuestaCall:Call<MarvelRespuesta<DatosRespuesta>> = service.obtenerLista(of)

        marvelRespuestaCall.enqueue(object:Callback<MarvelRespuesta<DatosRespuesta>>{
            override fun onResponse(call: Call<MarvelRespuesta<DatosRespuesta>>, response: Response<MarvelRespuesta<DatosRespuesta>>) {
                if(response.isSuccessful){
                    var marvelrespuesta :MarvelRespuesta<DatosRespuesta>?=response.body()
                    var datos:ArrayList<Superheroes> =marvelrespuesta!!.data!!.results

                    if(of=="0"){
                        todos=datos
                    }
                    else{
                        todos!!.addAll(datos)
                    }
                        mAdapter.setStores(todos!!)
                }
                else{
                    Log.i("Hola","nono2")
                }
            }
            override fun onFailure(call: Call<MarvelRespuesta<DatosRespuesta>>, t: Throwable) {
                Log.i("Hola",t.message.toString())
            }
        })
    }

}