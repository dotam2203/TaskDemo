package com.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.apis.Constants.POSTER_BASE_URL
import com.dto.MovieListDTO
import com.task.R
import com.task.databinding.ItemMovieBinding
import javax.inject.Inject

class RecyclerAdapter @Inject constructor() : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
  val diffUtilMovies = AsyncListDiffer(this, object : DiffUtil.ItemCallback<MovieListDTO.Result>() {
    override fun areItemsTheSame(oldItem: MovieListDTO.Result, newItem: MovieListDTO.Result): Boolean {
      return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieListDTO.Result, newItem: MovieListDTO.Result): Boolean {
      return oldItem == newItem
    }

  })

  inner class ViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bin(movies: MovieListDTO.Result) {
      binding.item = movies
      binding.imageMovie.load(POSTER_BASE_URL + movies.posterPath){
        crossfade(true)
        placeholder(R.drawable.poster_placeholder)
        scale(Scale.FILL)
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    //xác định giao diện  người dùng
    val view = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val item = diffUtilMovies.currentList
    if (position < item.size) {
      holder.bin(item[position])
    }
  }

  //kích thước của model
  override fun getItemCount(): Int = diffUtilMovies.currentList.size
}