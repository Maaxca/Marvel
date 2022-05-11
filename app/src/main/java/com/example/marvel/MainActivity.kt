package com.example.marvel

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel.services.MarvelapiService
import com.example.marvel.models.CharacterAdapter
import com.example.marvel.models.DatosRespuesta
import com.example.marvel.models.MarvelRespuesta
import com.example.marvel.models.Superheroes
import com.example.marvel.services.OnClickListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), OnClickListener {
    private lateinit var mAdapter:CharacterAdapter
    private lateinit var mGridLayout:GridLayoutManager
    var retrofit:Retrofit?=null
    var todos:ArrayList<Superheroes>? =null
    var i:Int=0
    @RequiresApi(Build.VERSION_CODES.M)
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
                    findViewById<ProgressBar>(R.id.progressBar).visibility=View.VISIBLE
                    i+=100
                    obtenerDatos(i.toString())
                }

            })

}

    private fun setupRecyclerView() {
        mAdapter = CharacterAdapter(ArrayList(),this)
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
                    findViewById<ProgressBar>(R.id.progressBar).visibility=View.INVISIBLE
                        mAdapter.setStores(todos!!)
                }
                else{
                    Toast.makeText(applicationContext,"Ha habido un error",Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<MarvelRespuesta<DatosRespuesta>>, t: Throwable) {
                Toast.makeText(applicationContext,"Ha habido un error",Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onDetail(superheroes: Superheroes) {
        val detailIntent= Intent(this, DetailActivity::class.java).apply {
            putExtra("superheroe",superheroes)
        }
        startActivity(detailIntent)
    }

}