package com.task.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.task.databinding.ItemListBinding
import com.task.model.ItemModel

class ListItemAdapter(private val item: MutableList<ItemModel> = mutableListOf()) : RecyclerView.Adapter<ListItemAdapter.ViewHolder>() {
    //reload list change
    private val diffUtilCallback = object: DiffUtil.ItemCallback<ItemModel>(){
        override fun areItemsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean{
            return oldItem.icon == newItem.icon && oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
            return oldItem == newItem
        }
    }
    val diffUtil  =AsyncListDiffer(this,diffUtilCallback)

    inner class ViewHolder(val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    //override fun getItemCount(): Int = diffUtil.currentList.size
    override fun getItemCount(): Int = item.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            //binding.item = diffUtil.currentList[position]
            binding.tvTitle.text = item[position].title
            setIsRecyclable(false)
        }
    }
}