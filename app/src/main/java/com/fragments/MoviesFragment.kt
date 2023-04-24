package com.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adapters.RecyclerAdapter
import com.dto.MovieListDTO
import com.model.toMovieDetailModel
import com.task.databinding.FragmentMoviesBinding
import com.viewmodels.DataViewModel
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
      itemMovie.id?.let {
        getDataMovieSend(itemMovie.id)
      }?: makeText(requireContext(), "Movie Detail Empty!", Toast.LENGTH_LONG).show()
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
    binding = FragmentMoviesBinding.inflate(layoutInflater)
    binding.progressBarMovies.visibility = View.VISIBLE
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
      viewModel.movies.collect {
        it?.results?.let { results ->
          if (results.isNotEmpty()) {
            movieList.addAll(it.results)
            delay(1000L)
            binding.progressBarMovies.visibility = View.GONE
            recyclerAdapter.diffUtilMovies.submitList(movieList.toList())
          } else {
            makeText(requireContext(), "Movie List Empty!", Toast.LENGTH_LONG).show()
            binding.progressBarMovies.visibility = View.GONE
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