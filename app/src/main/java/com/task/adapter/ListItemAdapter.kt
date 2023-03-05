package com.task.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.task.databinding.ParentItemBinding
import com.task.model.ParentList

class ListItemAdapter(private val parents: MutableList<ParentList> = mutableListOf()) : RecyclerView.Adapter<ListItemAdapter.ViewHolder>() {
    //reload list change
    private val diffUtilCallback = object: DiffUtil.ItemCallback<ParentList>(){
        override fun areItemsTheSame(oldItem: ParentList, newItem: ParentList): Boolean{
            return oldItem.subList == newItem.subList
        }

        override fun areContentsTheSame(oldItem: ParentList, newItem: ParentList): Boolean {
            return oldItem == newItem
        }
    }
    val diffUtil  =AsyncListDiffer(this,diffUtilCallback)

    inner class ViewHolder(val binding: ParentItemBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ParentItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    //override fun getItemCount(): Int = diffUtil.currentList.size
    override fun getItemCount(): Int = parents.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            //binding.parents = diffUtil.currentList[position]
            setIsRecyclable(false)
        }
    }
}