package com.aditprayogo.moviecataloguelocalstorage

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.aditprayogo.moviecataloguelocalstorage.favorite.movie.FavoriteMovieFragment
import com.aditprayogo.moviecataloguelocalstorage.favorite.tvshow.FavoriteTvShowFragment

class SectionPagerAdapter(private val mContext: Context, fm: FragmentManager) : FragmentStatePagerAdapter(fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.title_movie, R.string.title_tvshow)

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position) {
            0 -> fragment =
                FavoriteMovieFragment()
            1 -> fragment =
                FavoriteTvShowFragment()
        }
        return fragment as Fragment

    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 2
    }

}