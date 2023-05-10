package com.task.fragments

import NotificationHelper
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.adapters.RecyclerAdapter
import com.task.databinding.FragmentMoviesBinding
import com.task.dto.MovieListDTO
import com.task.model.toMovieDetailModel
import com.task.viewmodels.DataViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MoviesFragment : Fragment() {
  val viewModel: DataViewModel by viewModels()
  private lateinit var binding: FragmentMoviesBinding
  private var movieList = mutableListOf<MovieListDTO.Result?>()
  private var currentPage = 1
  private val recyclerAdapter by lazy {
    RecyclerAdapter() { itemMovie ->
      getDataMovieSend(itemMovie.id!!)
      /*if (itemMovie.voteAverage!! >= 7.0)
        getDataMovieSend(itemMovie.id!!)
      else
        makeText(requireContext(), "Movie Detail Empty!", Toast.LENGTH_LONG).show()*/
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View? {
    NotificationHelper(requireContext(), "Thông báo mới", "Trong Coroutines của Kotlin, GlobalScope là một đối tượng toàn cục (global object) được sử dụng để khởi tạo các coroutine.").CustomNotification()
    //MyFirebaseMessagingService(requireContext(), "Thông báo mới", "Trong Coroutines của Kotlin, GlobalScope là một đối tượng toàn cục (global object) được sử dụng để khởi tạo các coroutine.").Notification()
    binding = FragmentMoviesBinding.inflate(layoutInflater)
    initAdapter()
    return binding.root
  }

  private fun getDataMovieSend(id: Int) {
    viewModel.getMovieDetails(id)
    lifecycleScope.launchWhenStarted {
      viewModel.movie.collect {
        it?.let {
          val movieDetails = it.toMovieDetailModel()
          val direction = MoviesFragmentDirections.moviesFragmentToMovieDetailFragment(movieDetails)
          findNavController().navigate(direction)
        } ?: makeText(requireContext(), "Movie Details Empty!", Toast.LENGTH_LONG).show()
      }
    }
  }

  private fun initViewModel(page: Int) {
    viewModel.getMovieList(page)
    lifecycleScope.launchWhenStarted {
      viewModel.movies.collect { movieResponse ->
        movieResponse?.results?.let { results ->
          if (results.isNotEmpty()) {
            movieList.addAll(results)
            binding.textEmpty.isVisible = false
            delay(1000L)
            binding.progressBarMovies.isVisible = false
            recyclerAdapter.diffUtilMovies.submitList(movieList.toList())
          } else {
            delay(1000L)
            binding.apply {
              progressBarMovies.isVisible = false
              textEmpty.isVisible = true
            }
          }
        }
      }
    }
  }

  //khởi tạo recyclerview adapter hiển thị lên UI
  private fun initAdapter() {
    initViewModel(currentPage)
    binding.recyclerMovies.apply {
      layoutManager = LinearLayoutManager(requireContext())
      adapter = recyclerAdapter
      addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
          super.onScrolled(recyclerView, dx, dy)
          val layoutManagerLoad = layoutManager as LinearLayoutManager
          val visibleItemCount = layoutManagerLoad.childCount
          val totalItemCount = layoutManagerLoad.itemCount
          Log.e("TAG_CHILDCOUNT", "onScrolled: $visibleItemCount")
          Log.e("TAG_ITEMCOUNT", "onScrolled: $totalItemCount")
          val firstVisibleItemPosition = layoutManagerLoad.findFirstVisibleItemPosition()
          if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
            currentPage++;
            initViewModel(currentPage)
          }
        }
      })
    }
  }
}