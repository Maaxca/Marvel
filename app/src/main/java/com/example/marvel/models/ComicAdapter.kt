package com.example.marvel.models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.marvel.R
import com.example.marvel.databinding.DialogComicBinding
import com.example.marvel.databinding.ItemBinding
import com.example.marvel.databinding.ItemComicsBinding
import com.example.marvel.services.OnClickListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ComicAdapter(private var comics:ArrayList<DatosComics>, private var listener: OnClickListener) :
RecyclerView.Adapter<ComicAdapter.ViewHolder>(){

    private lateinit var mContext:Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext=parent.context

        val view=LayoutInflater.from(mContext).inflate(R.layout.item_comics,parent,false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comic=comics.get(position)

        with(holder){
        setListener(comic)
            binding.tvName.text=comic.title

            var url="${comic.thumbnail.path}.${comic.thumbnail.extension}"
            var splits=url.split(":")
            var urlFinal="${splits[0]}s:${splits[1]}"

            Glide.with(mContext)
                .load(urlFinal)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.imgPhoto) }

    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding=ItemComicsBinding.bind(view)

        fun setListener(comics: DatosComics){
            with(binding.root){
                setOnClickListener(){
                    listener.onDetailComics(comics)
                }
            }
        }

    }

    override fun getItemCount(): Int=
        comics.size

    fun setStores(comics: ArrayList<DatosComics>) {
        this.comics=comics
        notifyDataSetChanged()
    }
}