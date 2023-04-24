package com.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import coil.size.Scale
import com.constants.Constants.POSTER_BASE_URL
import com.model.MovieDetailModel
import com.task.R
import com.task.databinding.FragmentMovieDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {
  val args: MovieDetailFragmentArgs by navArgs()
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

  private fun getIdMovieDetail(movieDetails: MovieDetailModel) {
    binding.progressBarMovies.visibility = View.GONE
    val genreNames = movieDetails.genres?.joinToString(" | ")
    val formatRating = String.format("%.1f",movieDetails.voteAverage)
    val moviePoster = POSTER_BASE_URL + movieDetails.posterPath
    binding.apply {
      movie = movieDetails
      textMovieRating.text = formatRating
      textMovieGenres.text = genreNames
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