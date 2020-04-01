package com.aditprayogo.moviecataloguelocalstorage.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aditprayogo.moviecataloguelocalstorage.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_row_movie.view.*

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(){

    private var onItemClickCallback: OnItemClickCallback? = null
    private var pathPoster = "https://image.tmdb.org/t/p/w154"

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){

        this.onItemClickCallback = onItemClickCallback

    }

    private val listMovie = ArrayList<Movie>()

    fun setData(items: ArrayList<Movie>) {
        listMovie.clear()
        listMovie.addAll(items)
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(pathPoster + movie.photo)
                    .into(img_photo)

                txt_title.text = movie.title
                txt_overview.text = movie.overview
                txt_rating.text = movie.rating.toString()

                itemView.setOnClickListener{
                    onItemClickCallback?.onItemClicked(movie)
                }
            }
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MovieViewHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_movie, viewGroup,false)
        return  MovieViewHolder(mView)
    }

    override fun getItemCount(): Int {
        return listMovie.size
    }

    override fun onBindViewHolder(movieViewHolder: MovieViewHolder, position: Int) {
        movieViewHolder.bind(listMovie[position])
    }

    interface OnItemClickCallback {

        fun onItemClicked(data: Movie)

    }
}