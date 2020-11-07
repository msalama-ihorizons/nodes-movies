package com.nodes.movies.ui.moviedetails.cast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.leodroidcoder.genericadapter.OnRecyclerItemClickListener
import com.nodes.movies.R
import com.nodes.movies.model.Status
import com.nodes.movies.ui.adpater.MovieCastAdapter
import com.nodes.movies.ui.moviedetails.MovieDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.movie_cast_fragment.*

/**
 * Created by Mohamed Salama on 11/7/2020.
 */

@AndroidEntryPoint
class MovieCastFragment: Fragment() {

    private lateinit var movieCastAdapter: MovieCastAdapter
    private val moviesDetailsViewModel: MovieDetailsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movieCastAdapter = MovieCastAdapter(
            context,
            OnRecyclerItemClickListener {

            })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_cast_fragment, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        movieCastRV.adapter = movieCastAdapter

        moviesDetailsViewModel.movieCastLiveData?.observe(viewLifecycleOwner, Observer {resource ->

            when(resource.status) {
                Status.SUCCESS -> movieCastAdapter.submitList(resource.data)
                Status.LOADING ->  progressBar.visibility = View.VISIBLE
                Status.COMPLETE ->  progressBar.visibility = View.GONE
            }
        })
    }
}