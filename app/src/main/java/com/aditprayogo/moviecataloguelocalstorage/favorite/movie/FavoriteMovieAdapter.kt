package com.aditprayogo.moviecataloguelocalstorage.favorite.movie

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.aditprayogo.moviecataloguelocalstorage.R
import com.aditprayogo.moviecataloguelocalstorage.db.FavoriteMovieHelper
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail_movie.view.*
import kotlinx.android.synthetic.main.item_row_favorite.view.*
import android.widget.Toast.makeText as makeText1

class FavoriteMovieAdapter : RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteViewHolder>() {

    companion object {
        private val TAG = FavoriteMovieAdapter::class.java.simpleName
    }

    private lateinit var favoriteMovieHelper: FavoriteMovieHelper
    private var pathPoster = "https://image.tmdb.org/t/p/w154"
    private var onItemClickCallback:OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    var listMovie = ArrayList<FavoriteMovie>()
        set(listMovie) {
            if (listMovie.size > 0) {
                this.listMovie.clear()
            }
            this.listMovie.addAll(listMovie)
            Log.d(TAG, "ListMoview: $listMovie")
            notifyDataSetChanged()
        }

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: FavoriteMovie) {
            with(itemView) {
                txt_title.text = data.title
                txt_overview.text = data.overview
                txt_rating.text = data.rating.toString()

                Glide.with(itemView.context!!)
                    .load(pathPoster + data.photo)
                    .into(img_photo)

                btn_remove.setOnClickListener {
                    favoriteMovieHelper = FavoriteMovieHelper.getInstance(context)
                    favoriteMovieHelper.open()
                    favoriteMovieHelper.deleteById(data.id.toString())
                    removeItem(adapterPosition)
                    Toast.makeText(itemView.context, context.getString(R.string.item_deleted), Toast.LENGTH_SHORT).show()
                }

                itemView.setOnClickListener {
                    onItemClickCallback?.onItemClicked(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_favorite, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun getItemCount(): Int = this.listMovie.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listMovie[position])
    }

    fun removeItem(position: Int) {
        this.listMovie.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listMovie.size)
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: FavoriteMovie)
    }
}