package com.nodes.movies.ui.favorites

import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.irozon.sneaker.Sneaker
import com.nodes.movies.model.Status
import com.nodes.movies.ui.MoviesListFragment
import com.nodes.movies.ui.search.SearchMoviesFragment
import com.nodes.movies.ui.search.SearchMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.movies_list_fragment.*

/**
 * Created by Mohamed Salama on 11/7/2020.
 */

@AndroidEntryPoint
class FavouritesMoviesFragment: MoviesListFragment() {

    private val favouritesMoviesViewModel: FavouritesMoviesViewModel by viewModels()

    override fun initRecyclerView() {
        moviesRV.adapter =  moviesAdapter
        moviesRV.layoutManager = GridLayoutManager(context, NUMBER_OF_COL)
    }

    override fun handleObservers() {
        favouritesMoviesViewModel.favMoviesListLiveData.observe(viewLifecycleOwner, Observer { resources ->
            when (resources.status) {
                Status.SUCCESS -> moviesAdapter.submitList(resources.data)
                Status.ERROR ->  Sneaker.with(this)
                    .setTitle("Error")
                    .setMessage(resources.message?: "UnKnow Error")
                    .sneakError()
                Status.LOADING -> progressBar.visibility = View.VISIBLE
                Status.COMPLETE -> progressBar.visibility = View.GONE
            }
        })
    }


}