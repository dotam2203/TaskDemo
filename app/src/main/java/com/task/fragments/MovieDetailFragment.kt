package com.task.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import coil.size.Scale
import com.example.notification.Constants
import com.task.R
import com.example.notification.Constants.POSTER_BASE_URL
import com.example.notification.NotificationHelper
import com.task.databinding.FragmentMovieDetailBinding
import com.task.model.MovieDetailModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

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
    val moviePoster = POSTER_BASE_URL + movieDetails.posterPath
    val genreNames = movieDetails.genres?.joinToString(" | ")
    val formatRating = String.format("%.1f", movieDetails.voteAverage)
    //makeText(requireContext(), "url image: $moviePoster", Toast.LENGTH_SHORT).show()
    lifecycleScope.launchWhenStarted {
      delay(1000L)
      NotificationHelper(requireContext(), "Thông tin phim", movieDetails.overview.toString(), moviePoster).CustomNotification()
    }
    binding.progressBarMovies.visibility = View.GONE
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