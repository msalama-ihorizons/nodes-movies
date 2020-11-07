package com.nodes.movies.ui.adpater

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.leodroidcoder.genericadapter.BaseViewHolder
import com.leodroidcoder.genericadapter.GenericRecyclerViewAdapter
import com.leodroidcoder.genericadapter.OnRecyclerItemClickListener
import com.nodes.movies.R
import com.nodes.movies.network.response.Cast
import com.nodes.movies.network.response.Movie
import kotlinx.android.synthetic.main.movie_cast_item.view.*

/**
 * Created by Mohamed Salama on 11/6/2020.
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

    fun submitList(castList: List<Cast>?) {
        if (castList == null || castList.isEmpty()) {
            return
        }

        val oldList = items
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            CastItemDiffCallback(
                oldList,
                castList
            )
        )

        items = castList

        diffResult.dispatchUpdatesTo(this)
    }

    class CastItemDiffCallback(
        var oldCastList: List<Cast>,
        var newCastList: List<Cast>
    ): DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldCastList[oldItemPosition].name == newCastList[newItemPosition].name) &&
                    (oldCastList[oldItemPosition].character == newCastList[newItemPosition].character)
        }

        override fun getOldListSize(): Int {
            return oldCastList.size
        }

        override fun getNewListSize(): Int {
            return newCastList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldCastList[oldItemPosition] == newCastList[newItemPosition]
        }

    }
}