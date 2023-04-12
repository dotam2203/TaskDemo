package com.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adapters.RecyclerAdapter
import com.base.DataViewModel
import com.base.InjectViewModel
import com.dto.ConvertData.toMovieDetails
import com.dto.MovieDetails
import com.dto.MovieListDTO
import com.task.databinding.FragmentMoviesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MoviesFragment : InjectViewModel() {
  private lateinit var binding: FragmentMoviesBinding
  private var movieList = mutableListOf<MovieListDTO.Result?>()
  private var currentPage = 1
  private val recyclerAdapter by lazy {
    RecyclerAdapter() { itemMovie ->
      if (itemMovie.voteAverage!! >= 7.0)
        getDataMovieSend(itemMovie.id!!)
      else
        makeText(requireContext(), "Movie Detail Empty!", Toast.LENGTH_LONG).show()
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View? {
    binding = FragmentMoviesBinding.inflate(layoutInflater)
    binding.progressBarMovies.visibility = View.VISIBLE
    initAdapter()
    return binding.root
  }

  private fun getDataMovieSend(id: Int) {
    viewModel.getMovieDetails(id)
    lifecycleScope.launchWhenStarted {
      viewModel.movie.collect {
        if (it != null) {
          val movieDetails = it.toMovieDetails()
          val direction = MoviesFragmentDirections.moviesFragmentToMovieDetailFragment(movieDetails)
          findNavController().navigate(direction)
        }
      }
    }
  }

  private fun initViewModel(page: Int) {
    viewModel.getMovieList(page)
    lifecycleScope.launchWhenStarted {
      viewModel.movies.collect { it ->
        if (it?.results != null) {
          if (it.results.isNotEmpty()) {
            movieList.addAll(it.results)
            delay(1000L)
            binding.progressBarMovies.visibility = View.GONE
            recyclerAdapter.diffUtilMovies.submitList(movieList.toList())
          } else {
            makeText(requireContext(), "Movie List Empty!", Toast.LENGTH_LONG).show()
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
          val visibleItemCount = layoutManagerLoad.childCount //hiển thị toàn bộ item trong api
          val totalItemCount = layoutManagerLoad.itemCount //tổng item hiện có
          val firstVisibleItemPosition = layoutManagerLoad.findFirstVisibleItemPosition()//item vị trí đầu tiên
          if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
            currentPage++;
            initViewModel(currentPage)
          }
        }
      })
    }
  }
}