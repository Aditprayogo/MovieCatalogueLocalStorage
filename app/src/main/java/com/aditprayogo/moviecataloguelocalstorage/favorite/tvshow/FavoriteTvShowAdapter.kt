package com.aditprayogo.moviecataloguelocalstorage.favorite.tvshow

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.aditprayogo.moviecataloguelocalstorage.R
import com.aditprayogo.moviecataloguelocalstorage.db.FavoriteTvShowHelper
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_row_favorite.view.*

class FavoriteTvShowAdapter : RecyclerView.Adapter<FavoriteTvShowAdapter.FavoriteViewHolder>() {

    companion object {
        private val TAG = FavoriteTvShowAdapter::class.java.simpleName
    }

    private lateinit var favoriteTvShowHelper: FavoriteTvShowHelper
    private var pathPoster = "https://image.tmdb.org/t/p/w154"
    private var onItemClickCallback:OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    var listTvShow = ArrayList<FavoriteTvShow>()
        set(listMovie) {
            if (listMovie.size > 0) {
                this.listTvShow.clear()
            }
            this.listTvShow.addAll(listMovie)
            Log.d(TAG, "ListMoview: $listMovie")
            notifyDataSetChanged()
        }

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: FavoriteTvShow) {
            with(itemView) {
                txt_title.text = data.title
                txt_overview.text = data.overview
                txt_rating.text = data.rating.toString()

                Glide.with(itemView.context!!)
                    .load(pathPoster + data.photo)
                    .into(img_photo)

                btn_remove.setOnClickListener {
                    favoriteTvShowHelper = FavoriteTvShowHelper.getInstance(context)
                    favoriteTvShowHelper.open()
                    favoriteTvShowHelper.deleteById(data.id.toString())
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

    override fun getItemCount(): Int = this.listTvShow.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listTvShow[position])
    }

    fun removeItem(position: Int) {
        this.listTvShow.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listTvShow.size)
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: FavoriteTvShow)
    }
}