package com.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import coil.size.Scale
import com.apis.Constants
import com.apis.Constants.POSTER_BASE_URL
import com.base.DataViewModel
import com.base.InjectViewModel
import com.dto.MovieDetails
import com.dto.MovieDetailsDTO
import com.task.databinding.FragmentMovieDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MovieDetailFragment : InjectViewModel() {
  private lateinit var binding: FragmentMovieDetailBinding
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View? {
    binding = FragmentMovieDetailBinding.inflate(layoutInflater)
    binding.progressBarMovies.visibility = View.VISIBLE
    binding.imageBack.setOnClickListener {
      findNavController().navigateUp()
    }
    getIdMovieDetail(args.movieDetail)
    return binding.root
  }

  private fun getIdMovieDetail(movieDetails: MovieDetails) {
    binding.progressBarMovies.visibility = View.GONE
    val genreNames = movieDetails.genres?.joinToString(" | ")
    val formatRating = String.format("%.1f",movieDetails.voteAverage)
    val moviePoster = POSTER_BASE_URL + movieDetails.posterPath
    binding.apply {
      movie = movieDetails
      textMovieRating.text = formatRating ?: ""
      textMovieGenres.text = genreNames ?: ""
      listOf(imageMovie, imageMovieBack).forEach {
        it.load(moviePoster) {
          crossfade(true)
          placeholder(R.drawable.poster_placeholder)
          scale(Scale.FILL)
        }
      }
    }
  }
}