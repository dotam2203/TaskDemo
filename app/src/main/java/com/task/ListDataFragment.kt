package com.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.adapters.RecyclerAdapter
import com.base.DataViewModel
import com.task.databinding.ListItemFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ListDataFragment : Fragment() {
  private lateinit var binding: ListItemFragmentBinding
  val viewModel: DataViewModel by viewModels()
  @Inject
  lateinit var recyclerAdapter: RecyclerAdapter
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View? {
    binding = ListItemFragmentBinding.inflate(layoutInflater)
    initAdapter()
    initViewModel()
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
  }

  private fun initViewModel() {
    viewModel.getItemToApi()
    lifecycleScope.launchWhenStarted {
      viewModel.list.collect {
        if (it.isNotEmpty()) {
          recyclerAdapter.diffGenres.submitList(it)
        } else return@collect
      }
    }
  }
  //khởi tạo recyclerview adapter hiển thị lên UI
  private fun initAdapter() {
    binding.rvVideo.apply {
      layoutManager = LinearLayoutManager(requireContext())
      adapter = recyclerAdapter
    }
  }
}