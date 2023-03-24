package com.task.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.R
import com.task.databinding.DescriptionLayoutBinding
import com.task.databinding.LayoutItemCardBinding
import com.task.databinding.LayoutItemNestedBinding
import com.task.databinding.LayoutItemChooseBinding
import com.task.model.ParentList

/**
 * Author: tamdt35@fpt.com.vn
 * Date: 03/03/2023
 */
class RecyclerAdapter(
  private val onClickItem: ((item: ParentList, position: Int, layoutType: Int) -> Unit),
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
  val diffCallback = AsyncListDiffer(this, object : DiffUtil.ItemCallback<ParentList>() {
    override fun areItemsTheSame(oldItem: ParentList, newItem: ParentList) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ParentList, newItem: ParentList) = oldItem == newItem

  })
  inner class ViewHolderChoose(private val binding: LayoutItemChooseBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bin() {
      binding.item = diffCallback.currentList[0] as ParentList.TitleChoose
    }
  }

  inner class ViewHolderCard(private val binding: LayoutItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun bin(position: Int) {
      binding.item = diffCallback.currentList[position] as ParentList.DescriptionItem
      Log.e("DATA", "DATA:${binding.item?.id.toString()}")
      binding.onClickItemCard = {layoutType ->
        onClickItem(diffCallback.currentList[position],position,layoutType)
      }
    }
  }

  inner class ViewHolderNestedList(private val binding: LayoutItemNestedBinding) : RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun bin(position: Int) {
      binding.item = diffCallback.currentList[position] as ParentList.DescriptionItemChild
      val parentLayout = binding.linearParentItem
      if (parentLayout.childCount == 0) {
        for (i in diffCallback.currentList) {
          if (i is ParentList.DescriptionItemChild) {
            val view = DescriptionLayoutBinding.inflate(LayoutInflater.from(itemView.context),null, false)
            view.item = i
            parentLayout.addView(view.root)
          }
        }
      }
      binding.onClickItemNested = {layoutType ->
        onClickItem.invoke(diffCallback.currentList[position], position, layoutType)
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return when (viewType) {
      TYPE_LAYOUT_CHOOSE -> {
        val binding = LayoutItemChooseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        ViewHolderChoose(binding)
      }
      TYPE_LAYOUT_CARD -> {
        val binding = LayoutItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        ViewHolderCard(binding)
      }
      TYPE_LAYOUT_NESTED -> {
        val binding = LayoutItemNestedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        ViewHolderNestedList(binding)
      }
      else -> throw IllegalArgumentException("Invalid view holder")
    }
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    val layoutType = getItemViewType(position)
    when (holder) {
      is ViewHolderNestedList -> holder.bin(position)
      is ViewHolderCard -> holder.bin(position)
      is ViewHolderChoose -> holder.bin()
    }
  }

  override fun getItemCount(): Int = diffCallback.currentList.size
  override fun getItemViewType(position: Int) = when (position) {
    0 -> TYPE_LAYOUT_CHOOSE
    1, 2 -> TYPE_LAYOUT_CARD
    else -> TYPE_LAYOUT_NESTED
  }

  fun spanSizeLookup(position: Int) = when (position) {
    1, 2 -> 1
    else -> 2
  }

  companion object {
    const val TYPE_LAYOUT_CHOOSE = 1
    const val TYPE_LAYOUT_CARD = 2
    const val TYPE_LAYOUT_NESTED = 3
  }
}

class GridSpanSizeLookup(private val adapter: RecyclerAdapter) : GridLayoutManager.SpanSizeLookup() {
  override fun getSpanSize(position: Int) = adapter.spanSizeLookup(position)
}
