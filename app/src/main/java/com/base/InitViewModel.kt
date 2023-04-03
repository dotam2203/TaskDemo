package com.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

abstract class InitViewModel : Fragment() {
    //khởi tạo ViewModel
    val viewModel: DataViewModel by lazy {
        ViewModelProvider(this)[DataViewModel::class.java]
    }
}