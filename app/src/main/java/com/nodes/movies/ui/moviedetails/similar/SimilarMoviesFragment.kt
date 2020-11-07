package com.nodes.movies.ui.moviedetails.similar

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.nodes.movies.model.Status
import com.nodes.movies.ui.MoviesListFragment
import com.nodes.movies.ui.moviedetails.MovieDetailsViewModel
import kotlinx.android.synthetic.main.movies_list_fragment.*
import kotlinx.android.synthetic.main.similar_movies_fragment.*
import kotlinx.android.synthetic.main.similar_movies_fragment.progressBar

/**
 * Created by Mohamed Salama on 11/7/2020.
 */

class SimilarMoviesFragment: MoviesListFragment()  {

    private val moviesDetailsViewModel: MovieDetailsViewModel by activityViewModels()
    override fun initRecyclerView() {
        moviesRV.adapter =  moviesAdapter
        moviesRV.layoutManager = GridLayoutManager(context, NUMBER_OF_COL)
    }

    override fun handleObservers() {
        moviesDetailsViewModel.similarMoviesLiveData?.observe(viewLifecycleOwner, Observer { resource->
            when(resource.status) {
                Status.SUCCESS -> moviesAdapter.items = resource.data
                Status.LOADING -> progressBar.visibility = View.VISIBLE
                Status.COMPLETE -> progressBar.visibility = View.GONE
            }
        })
    }

}