package com.tiendito.bmisrmovies.ui.adpater

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.leodroidcoder.genericadapter.BaseViewHolder
import com.leodroidcoder.genericadapter.GenericRecyclerViewAdapter
import com.leodroidcoder.genericadapter.OnRecyclerItemClickListener
import com.nodes.movies.R
import com.nodes.movies.network.response.Cast
import kotlinx.android.synthetic.main.movie_cast_item.view.*

/**
 * Created by Mohamed Salama on 9/5/2020.
 */
class MovieCastAdapter(context: Context?, listener: OnRecyclerItemClickListener) : GenericRecyclerViewAdapter<Cast, OnRecyclerItemClickListener, MovieCastAdapter.MovieCastViewHolder>(context, listener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCastViewHolder {
        return MovieCastViewHolder(inflate(R.layout.movie_cast_item, parent), listener)
    }

    private var context: Context? = null

    init {
        this.context = context
    }


    inner class MovieCastViewHolder(itemView: View, listener: OnRecyclerItemClickListener?) :
        BaseViewHolder<Cast, OnRecyclerItemClickListener>(itemView, listener), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onBind(item: Cast) {

            itemView.castCharacter.text = item.character
            itemView.castName.text = item.name

            val profileUrl = context?.getString(R.string.movie_poster_url, item.profilePath)

            context?.let {
                Glide.with(it).load(profileUrl)
                    .into(itemView.castProfile)
            }
        }

        override fun onClick(p0: View?) {
            listener?.onItemClick(adapterPosition)
        }
    }
}