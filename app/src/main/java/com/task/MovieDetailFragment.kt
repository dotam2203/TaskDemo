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
import androidx.navigation.fragment.navArgs
import coil.load
import coil.size.Scale
import com.apis.Constants
import com.apis.Constants.POSTER_BASE_URL
import com.base.DataViewModel
import com.dto.MovieDetailsDTO
import com.task.databinding.FragmentMovieDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {
  private lateinit var binding: FragmentMovieDetailBinding
  val args: MovieDetailFragmentArgs by navArgs()
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View? {
    binding = FragmentMovieDetailBinding.inflate(layoutInflater)
    binding.progressBarMovies.visibility = View.VISIBLE
    getIdMovieDetail(args.movieDetail)
    return binding.root
  }

  private fun getIdMovieDetail(movieDetail: MovieDetailsDTO) {
    binding.progressBarMovies.visibility = View.GONE
    val moviePoster = POSTER_BASE_URL + movieDetail.posterPath
    binding.apply {
      movie = movieDetail
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