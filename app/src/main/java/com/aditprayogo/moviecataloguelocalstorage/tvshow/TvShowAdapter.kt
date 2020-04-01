package com.aditprayogo.moviecataloguelocalstorage.tvshow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aditprayogo.moviecataloguelocalstorage.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_row_movie.view.*

class TvShowAdapter : RecyclerView.Adapter<TvShowAdapter.ListViewHolder>(){

    private var onItemClickCallback: OnItemClickCallback? = null
    private var pathPoster = "https://image.tmdb.org/t/p/w154"

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    private val listTvShow = ArrayList<TvShow>()

    fun setData(items: ArrayList<TvShow>) {
        listTvShow.clear()
        listTvShow.addAll(items)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(tvShow: TvShow) {

            with(itemView) {
                Glide.with(itemView.context)
                    .load(pathPoster + tvShow.photo)
                    .into(img_photo)

                txt_title.text = tvShow.title
                txt_overview.text = tvShow.overview
                txt_rating.text = tvShow.rating.toString()

                itemView.setOnClickListener{
                    onItemClickCallback?.onItemClicked(tvShow)
                }
            }
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_movie, viewGroup,false)
        return  ListViewHolder(mView)
    }

    override fun getItemCount(): Int {
        return listTvShow.size
    }

    override fun onBindViewHolder(tvShowViewHolder: ListViewHolder, position: Int) {
        tvShowViewHolder.bind(listTvShow[position])
    }

    interface OnItemClickCallback {

        fun onItemClicked(data: TvShow)

    }
}