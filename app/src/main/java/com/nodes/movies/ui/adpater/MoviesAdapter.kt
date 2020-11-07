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
import com.nodes.movies.network.response.Movie
import kotlinx.android.synthetic.main.movie_item.view.*

class MoviesAdapter(context: Context?, listener: OnRecyclerItemClickListener) :
    GenericRecyclerViewAdapter<Movie, OnRecyclerItemClickListener, MoviesAdapter.MovieViewHolder>(
        context,
        listener
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(inflate(R.layout.movie_item, parent), listener)
    }

    private var context: Context? = null

    init {
        this.context = context
    }


    inner class MovieViewHolder(itemView: View, listener: OnRecyclerItemClickListener?) :
        BaseViewHolder<Movie, OnRecyclerItemClickListener>(itemView, listener),
        View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onBind(item: Movie) {
            itemView.movieTitle.text = item.title
            itemView.movieReleaseDate.text = item.releaseDate
            itemView.movieVoteAverage.text = item.voteAverage.toString()

            val posterUrl = context?.getString(R.string.movie_poster_url, item.posterPath)

            context?.let {
                Glide.with(it).load(posterUrl)
                    .into(itemView.moviePoster)
            }
        }

        override fun onClick(p0: View?) {
            listener?.onItemClick(adapterPosition)
        }
    }

    fun submitList(moviesList: List<Movie>?) {
        if (moviesList == null || moviesList.isEmpty()) {
            return
        }

        val oldList = items
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            MovieItemDiffCallback(
                oldList,
                moviesList
            )
        )

        items = moviesList

        diffResult.dispatchUpdatesTo(this)
    }

    class MovieItemDiffCallback(
        var oldMovieList: List<Movie>,
        var newMovieList: List<Movie>
    ): DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldMovieList[oldItemPosition].id == newMovieList[newItemPosition].id)
        }

        override fun getOldListSize(): Int {
            return oldMovieList.size
        }

        override fun getNewListSize(): Int {
            return newMovieList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldMovieList[oldItemPosition] == newMovieList[newItemPosition]
        }

    }
}