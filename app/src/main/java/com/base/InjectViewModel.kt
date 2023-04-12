package com.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.task.MovieDetailFragmentArgs

/**
 * Author: tamdt35@fpt.com.vn
 * Date: 12/04/2023
 */
abstract class InjectViewModel: Fragment() {
  val viewModel: DataViewModel by viewModels()
  val args: MovieDetailFragmentArgs by navArgs()
}