package com.example.marvel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.marvel.models.Superheroes

class DetailActivity : AppCompatActivity() {
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
    }
}