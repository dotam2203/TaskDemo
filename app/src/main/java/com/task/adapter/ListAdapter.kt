package com.task.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.task.R
import com.task.databinding.ChildItemBinding
import com.task.databinding.ParentItemBinding
import com.task.model.ChildList
import com.task.model.ParentList

/**
 * Author: tamdt35@fpt.com.vn
 * Date: 03/03/2023
 */
class ListAdapter(private val listItems: List<ParentList>) :
  RecyclerView.Adapter<ListAdapter.ViewHolder>() {
  inner class ViewHolder(val binding: ParentItemBinding) : RecyclerView.ViewHolder(binding.root) {}

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val binding = ParentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ViewHolder(binding)
  }

  override fun getItemCount(): Int = listItems.size

  @SuppressLint("SetTextI18n")
  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val listItem = listItems[position]
    with(holder) {
      binding.textTitleTop.text = "IQ${position + 1}"
      val parentLayout = binding.linearParentItem
      if (parentLayout.childCount > 0) {
        parentLayout.removeViewAt(0)
      }
      for (i in listItem.childList.indices) {
        val view: View = LayoutInflater.from(holder.itemView.context).inflate(R.layout.child_item, null)
        val title = view.findViewById<TextView>(R.id.text_title)
        title.text = listItem.childList[i].title
        parentLayout.addView(view)
      }
    }
  }
}