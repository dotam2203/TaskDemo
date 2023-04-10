package com.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.task.databinding.FragmentMovieDetailesBinding

class MovieDetailFragment : Fragment() {
  private lateinit var binding: FragmentMovieDetailesBinding
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View? {
    binding = FragmentMovieDetailesBinding.inflate(layoutInflater)
    return binding.root
  }
}