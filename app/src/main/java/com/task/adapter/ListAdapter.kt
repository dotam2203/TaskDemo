package com.task.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.R
import com.task.databinding.ParentItem2Binding
import com.task.databinding.ParentItemBinding
import com.task.databinding.TypeChooseBinding
import com.task.model.ParentList

/**
 * Author: tamdt35@fpt.com.vn
 * Date: 03/03/2023
 */
class ListAdapter(private val listItems: ArrayList<ParentList.DescriptionItem2>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
  inner class ViewHolder(val binding: ParentItemBinding) : RecyclerView.ViewHolder(binding.root) {}
  inner class ViewHolder2(val binding: ParentItem2Binding) : RecyclerView.ViewHolder(binding.root) {}
  inner class ViewHolderChoose(val binding: TypeChooseBinding) : RecyclerView.ViewHolder(binding.root) {}

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return when (viewType) {
      TYPE_PARENT1 -> {
        val binding = ParentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        ViewHolder(binding)
      }
      TYPE_PARENT2 -> {
        val binding = ParentItem2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        ViewHolder2(binding)
      }
      TYPE_CHOOSE -> {
        val binding = TypeChooseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        ViewHolderChoose(binding)
      }
      else -> throw IllegalArgumentException("Invalid view holder")
    }
  }

  @SuppressLint("SetTextI18n")
  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    when (holder) {
      is ViewHolder -> holder.apply {
        binding.textTitleTop.text = "IQ$position"
        val parentLayout = binding.linearParentItem
        if (parentLayout.childCount > 0) {
          parentLayout.removeViewAt(0)
        }
        //tránh lặp lại view
        for (item in listItems.slice(0..3)) {
          val view: View = LayoutInflater.from(holder.itemView.context).inflate(R.layout.child_item, null)
          val title = view.findViewById<TextView>(R.id.text_title)
          title.text = item.description
          parentLayout.addView(view)
        }
      }
      is ViewHolder2 -> holder.apply {
        binding.textTitleTop.text = "IQ$position"
      }
      is ViewHolderChoose -> holder.apply {
        binding.textChoose.text = ParentList.TitleChoose.toString()
      }
    }
  }

  override fun getItemCount(): Int = 10
  override fun getItemViewType(position: Int): Int {
    return when (position) {
      0 -> TYPE_CHOOSE
      1, 2 -> TYPE_PARENT2
      else -> TYPE_PARENT1
    }
  }

  fun spanSizeLookup(position: Int): Int {
    return when (position) {
      1, 2 -> 1
      else -> 2
    }
  }

  companion object {
    private const val TYPE_PARENT1 = 1
    private const val TYPE_PARENT2 = 2
    private const val TYPE_CHOOSE = 3
  }
}

class GridSpanSizeLookup(private val adapter: ListAdapter) : GridLayoutManager.SpanSizeLookup() {
  override fun getSpanSize(position: Int): Int {
    return adapter.spanSizeLookup(position)
  }
}
