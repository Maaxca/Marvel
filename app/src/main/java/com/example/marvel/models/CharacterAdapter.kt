package com.example.marvel.models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.marvel.R
import com.example.marvel.databinding.ItemBinding
import com.squareup.picasso.Picasso

class CharacterAdapter(private var characters:ArrayList<Superheroes>) :
RecyclerView.Adapter<CharacterAdapter.ViewHolder>(){

    private lateinit var mContext:Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext=parent.context

        val view=LayoutInflater.from(mContext).inflate(R.layout.item,parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character=characters.get(position)

        with(holder){

            binding.tvName.text=character.name

            var url="${character.thumbnail.path}.${character.thumbnail.extension}"
            var splits=url.split(":")
            var urlFinal="${splits[0]}s:${splits[1]}"

            Glide.with(mContext)
                .load(urlFinal)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.imgPhoto) }

    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding=ItemBinding.bind(view)

    }

    override fun getItemCount(): Int=
        characters.size

    fun setStores(characters: ArrayList<Superheroes>) {
        this.characters=characters
        notifyDataSetChanged()
    }
}