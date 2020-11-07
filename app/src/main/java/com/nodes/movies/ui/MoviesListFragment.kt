package com.nodes.movies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.leodroidcoder.genericadapter.OnRecyclerItemClickListener
import com.nodes.movies.R
import com.nodes.movies.ui.adpater.MoviesAdapter
import com.nodes.movies.ui.moviedetails.MovieDetailsActivity

/**
 * Created by Mohamed Salama on 11/7/2020.
 */
abstract class MoviesListFragment: Fragment() {

    internal lateinit var moviesAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movies_list_fragment, container, false)

    }

    companion object {
        internal const val NUMBER_OF_COL = 3
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);

        moviesAdapter = MoviesAdapter(
            context,
            OnRecyclerItemClickListener { pos ->

                     startActivity(
                         MovieDetailsActivity.newIntent(
                             requireActivity(),
                             moviesAdapter.items[pos].id
                         )
                     )
            })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
        handleObservers()
    }

    abstract fun initRecyclerView()
    abstract fun handleObservers()
}