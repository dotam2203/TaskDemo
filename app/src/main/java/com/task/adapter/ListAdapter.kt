package com.task.adapter

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
  RecyclerView.Adapter<RecyclerView.ViewHolder>() {
  inner class ViewHolder(private val binding: ParentItemBinding) :
    RecyclerView.ViewHolder(binding.root)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    val binding = ParentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ViewHolder(binding)
  }

  override fun getItemCount(): Int = listItems.size

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    val listItem = listItems[position]
    //holder.bind(listItem)
    val view1: View =
      LayoutInflater.from(holder.itemView.context).inflate(R.layout.child_item, null)
    val view2: View =
      LayoutInflater.from(holder.itemView.context).inflate(R.layout.child_item, null)
    val view3: View =
      LayoutInflater.from(holder.itemView.context).inflate(R.layout.child_item, null)
    val view4: View =
      LayoutInflater.from(holder.itemView.context).inflate(R.layout.child_item, null)
    val title1 = view1.findViewById<TextView>(R.id.text_title)
    val title2 = view2.findViewById<TextView>(R.id.text_title)
    val title3 = view3.findViewById<TextView>(R.id.text_title)
    val title4 = view4.findViewById<TextView>(R.id.text_title)
    title1.text = listItem.childList[0].title
    title2.text = listItem.childList[1].title
    title3.text = listItem.childList[2].title
    title4.text = listItem.childList[3].title
    val parentLayout = holder.itemView.findViewById<LinearLayout>(R.id.linear_parent_item)
    if (parentLayout.childCount > 0) {
      parentLayout.removeViewAt(0)
    }
    //parentLayout.addView(view1)
    parentLayout.apply {
      addView(view1)
      addView(view2)
      addView(view3)
      addView(view4)
    }
  }
}