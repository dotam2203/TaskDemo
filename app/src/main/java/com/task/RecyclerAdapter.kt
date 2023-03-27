package com.task

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.task.databinding.ItemBinding

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
  //khởi tạo GenresModel
  var genres = mutableListOf<Genres>()
  val diffGenres = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Genres>() {
    override fun areItemsTheSame(oldItem: Genres, newItem: Genres) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Genres, newItem: Genres) = oldItem == newItem
  })

  inner class ViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bin(genres: Genres){
      binding.item = genres
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    //xác định giao diện  người dùng
    val view = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bin(diffGenres.currentList[position])
  }

  //kích thước của model
  override fun getItemCount(): Int = diffGenres.currentList.size
}