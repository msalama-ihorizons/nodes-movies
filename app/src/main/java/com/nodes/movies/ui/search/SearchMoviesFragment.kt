package com.nodes.movies.ui.search

import android.app.SearchManager
import android.content.Context
import android.os.IBinder
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.irozon.sneaker.Sneaker
import com.nodes.movies.R
import com.nodes.movies.model.Status
import com.nodes.movies.ui.MoviesListFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.movies_list_fragment.*


/**
 * Created by Mohamed Salama on 11/6/2020.
 */

@AndroidEntryPoint
open class SearchMoviesFragment : MoviesListFragment() {

    private val searchMoviesViewModel: SearchMoviesViewModel by activityViewModels()

    private var searchView: SearchView? = null
    private var queryTextListener: SearchView.OnQueryTextListener? = null


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_sort -> {
                moviesAdapter.clear()
                searchMoviesViewModel.sortByRating()
            }
        }

        searchView?.setOnQueryTextListener(queryTextListener)
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)
        val searchItem = menu.findItem(R.id.search)
        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        if (searchItem != null) {
            searchView = searchItem.actionView as SearchView
        }
        if (searchView != null) {
            searchView?.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            queryTextListener = object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String): Boolean {
                    // Log.i("onQueryTextChange", newText)
                    return true
                }

                override fun onQueryTextSubmit(query: String): Boolean {
                    searchItem?.collapseActionView()
                    doSearch(query, view)
                    return true
                }
            }
            searchView?.setOnQueryTextListener(queryTextListener)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun initRecyclerView() {
        moviesRV.adapter = moviesAdapter
        moviesRV.layoutManager = GridLayoutManager(context, NUMBER_OF_COL)

        moviesRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if (lastPosition == moviesAdapter.itemCount - 1) {
                    searchMoviesViewModel.loadNextPage()
                }
            }
        })
    }

    private fun doSearch(query: String, v: View?) {
        moviesAdapter.clear()
        searchMoviesViewModel.searchMovies(query)
        dismissKeyboard(v?.windowToken)
    }

    override fun handleObservers() {
        searchMoviesViewModel.allResultsLiveData.observe(viewLifecycleOwner, Observer {
            moviesAdapter.items = it
        })

        searchMoviesViewModel.searchMoviesListLiveData.observe(
            viewLifecycleOwner,
            Observer { resources ->
                when (resources.status) {
                    Status.ERROR -> Sneaker.with(this)
                        .setTitle("Error")
                        .setMessage(resources.message ?: "UnKnow Error")
                        .sneakError()
                    Status.LOADING -> progressBar.visibility = View.VISIBLE
                    Status.COMPLETE -> progressBar.visibility = View.GONE
                }
            })
    }

    private fun dismissKeyboard(windowToken: IBinder?) {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(windowToken, 0)
    }
}