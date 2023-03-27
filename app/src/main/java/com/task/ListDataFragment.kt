package com.task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.task.databinding.ListItemFragmentBinding

class ListDataFragment : InitViewModel() {
    private lateinit var binding: ListItemFragmentBinding
    //khởi tạo adapter
    private val recyclerAdapter by lazy {
        RecyclerAdapter()
    }

    //khởi tạo GenresModel
    private val genres = ArrayList<Genres>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
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
        //gọi api
        viewModel.getItemToApi()
        //couroutines scope
        lifecycleScope.launchWhenStarted {
            //lấy biến đã khởi tạo bên viewmodel
            viewModel.list.collect {
                //kiểu tra danh sách rỗng hay không
                if (it.isNotEmpty()) {
                    //thêm danh sách vừa lấy được vào list khai báo ở adapter
                    genres.addAll(it)
                    recyclerAdapter.apply {
                        genres.addAll(it)
                        //reload lại danh sách
                        recyclerAdapter.diffGenres.submitList(it)
                    }
                } else return@collect
            }
        }
    }

    //khởi tạo recyclerview adapter hiển thị lên UI
    private fun initAdapter() {
        binding.apply {
            rvVideo.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = recyclerAdapter
            }
        }
    }
}