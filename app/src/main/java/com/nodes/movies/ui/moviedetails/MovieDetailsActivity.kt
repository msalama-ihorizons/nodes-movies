package com.nodes.movies.ui.moviedetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.irozon.sneaker.Sneaker
import com.like.LikeButton
import com.like.OnLikeListener
import com.nodes.movies.R
import com.nodes.movies.model.Status
import com.nodes.movies.network.response.Movie
import com.nodes.movies.ui.moviedetails.rate.RateBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.movie_details_activity.*


@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {

    private val moviesDetailsViewModel: MovieDetailsViewModel by viewModels()
    private var movie: Movie? = null

    companion object {
        const val EXTRA_MOVIE_ID = "extraMovieID"

        fun newIntent(context: Context?, movieId: Int?): Intent {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra(EXTRA_MOVIE_ID, movieId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.movie_details_activity)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        handleActions()

        handleObservers()
    }

    private fun handleActions() {

        btnAddToFav.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton?) {
                moviesDetailsViewModel.addToFavourites(true, movie)
            }

            override fun unLiked(likeButton: LikeButton?) {
                moviesDetailsViewModel.addToFavourites(false, movie)
            }

        })

    }

    private fun handleObservers() {
        moviesDetailsViewModel.movieDetailsLiveData?.observe(this, Observer { resources ->

            when (resources.status) {
                Status.SUCCESS -> {
                    movie = resources.data

                    movie?.let {
                        title = it.title

                        val posterUrl = getString(R.string.movie_poster_url, it.backdropPath)
                        Glide.with(this).load(posterUrl)
                            .into(moviePoster)

                        movieTitle.text = it.title
                        movieOverView.text = it.overview
                        movieVoteAverage.text = it.voteAverage.toString()
                        movieReleaseDate.text = it.releaseDate
                        movieGenres.text = TextUtils.join(", ", it.genres.map { genre-> genre.name })
                    }


                }
                Status.ERROR -> Sneaker.with(this)
                    .setTitle("Error")
                    .setMessage(resources.message?: "UnKnow Error")
                    .sneakError()
                Status.LOADING -> progressBar.visibility = View.VISIBLE
                Status.COMPLETE -> progressBar.visibility = View.GONE
            }
        })

        moviesDetailsViewModel.ratingMovieLiveData.observe(this, Observer { resources ->
            when (resources.status) {
                Status.SUCCESS ->  Sneaker.with(this)
                    .setTitle("Success")
                    .setMessage("Rating is submitted!")
                    .sneakSuccess()
                Status.ERROR ->  Sneaker.with(this)
                    .setTitle("Error")
                    .setMessage("There is an error, please try again later")
                    .sneakError()
                Status.LOADING -> progressBar.visibility = View.VISIBLE
                Status.COMPLETE -> progressBar.visibility = View.GONE
            }

        })

        moviesDetailsViewModel.favouriteMovieLiveDate?.observe(this, Observer {
            btnAddToFav.isLiked = it
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.movies_details_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_rate -> {
                val rateBottomSheetFragment =
                    RateBottomSheetFragment()
                rateBottomSheetFragment.rateClickCallback =
                    object :
                        RateBottomSheetFragment.RateClickCallback {
                    override fun onRateBtnClick(rateValue: Float) {
                        moviesDetailsViewModel.rateMovie(rateValue)
                    }

                }

                rateBottomSheetFragment.show(supportFragmentManager, "rateBottomSheetFragment")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}