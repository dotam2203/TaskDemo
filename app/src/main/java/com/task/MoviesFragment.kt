package com.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adapters.RecyclerAdapter
import com.base.DataViewModel
import com.task.databinding.FragmentMoviesBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MoviesFragment : Fragment() {
  private lateinit var binding: FragmentMoviesBinding
  private var currentPage = 1
  val viewModel: DataViewModel by viewModels()
  @Inject
  lateinit var recyclerAdapter: RecyclerAdapter
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View? {
    binding = FragmentMoviesBinding.inflate(layoutInflater)
    binding.progressBarMovies.visibility = View.VISIBLE
    initAdapter()
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
  }

  private fun initViewModel(page:Int) {
    viewModel.getMovieList(page)
    lifecycleScope.launchWhenStarted {
      viewModel.movies.collect {
        binding.progressBarMovies.visibility = View.GONE
        recyclerAdapter.diffUtilMovies.submitList(it?.results)
      }
    }
  }
  //khởi tạo recyclerview adapter hiển thị lên UI
  private fun initAdapter() {
    initViewModel(currentPage)
    binding.recyclerMovies.apply {
      layoutManager = LinearLayoutManager(requireContext())
      adapter = recyclerAdapter
      addOnScrollListener(object : RecyclerView.OnScrollListener(){
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
          super.onScrolled(recyclerView, dx, dy)
          val layoutManagerLoad = layoutManager as LinearLayoutManager
          val visibleItemCount = layoutManagerLoad.childCount //hiển thị toàn bộ item trong api
          val totalItemCount = layoutManagerLoad.itemCount //tổng item hiện có
          val firstVisibleItemPosition = layoutManagerLoad.findFirstVisibleItemPosition()//item vị trí đầu tiên
          if(visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0){
            currentPage++;
            initViewModel(currentPage)
          }
        }
      })
    }
  }
}