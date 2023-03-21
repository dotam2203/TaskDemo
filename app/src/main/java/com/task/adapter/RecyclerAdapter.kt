package com.task.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.R
import com.task.databinding.LayoutItemCardBinding
import com.task.databinding.LayoutItemNestedBinding
import com.task.databinding.LayoutItemChooseBinding
import com.task.model.ParentList

/**
 * Author: tamdt35@fpt.com.vn
 * Date: 03/03/2023
 */
class RecyclerAdapter(
  private val listItems: ArrayList<ParentList>,
  private var onItemClick: ((item: ParentList, position: Int, layoutType: Int) -> Unit),
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  inner class ViewHolderChoose(val binding: LayoutItemChooseBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bin(position: Int) {
      val title = ParentList.TitleChoose("What would you \nlike to choose?")
      binding.textChoose.text = title.title
    }
  }

  inner class ViewHolderCard(val binding: LayoutItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun bin(position: Int, layoutType: Int) {
      binding.textTitleTop.text = "IQ$position"
      binding.cardParent.setOnClickListener {
        onItemClick.invoke(listItems[position], position, layoutType)
      }
    }
  }

  inner class ViewHolderNestedList(val binding: LayoutItemNestedBinding) : RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun bin(position: Int, layoutType: Int) {
      binding.textTitleTop.text = "IQ$position"
      val parentLayout = binding.linearParentItem
      if (parentLayout.childCount == 0) {
        for (i in listItems) {
          if (i is ParentList.DescriptionItemChild) {
            val view: View = LayoutInflater.from(itemView.context).inflate(R.layout.description_layout, null)
            val title = view.findViewById<TextView>(R.id.text_title)
            title.text = i.description
            parentLayout.addView(view)
          }
        }
      }
      binding.constraintParent.setOnClickListener {
        onItemClick.invoke(listItems[position], position, layoutType)
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
    val item = listItems[position]
    val layoutType = getItemViewType(position)
    when (holder) {
      is ViewHolderNestedList -> holder.apply {
        bin(position, layoutType)
      }
      is ViewHolderCard -> holder.apply {
        bin(position, layoutType)
      }
      is ViewHolderChoose -> holder.apply {
        bin(position)
      }
    }
  }

  override fun getItemCount(): Int = listItems.size
  override fun getItemViewType(position: Int): Int {
    return when (position) {
      0 -> TYPE_LAYOUT_CHOOSE
      1, 2 -> TYPE_LAYOUT_CARD
      else -> TYPE_LAYOUT_NESTED
    }
  }

  fun spanSizeLookup(position: Int): Int {
    return when (position) {
      1, 2 -> 1
      else -> 2
    }
  }

  companion object {
    const val TYPE_LAYOUT_CHOOSE = 1
    const val TYPE_LAYOUT_CARD = 2
    const val TYPE_LAYOUT_NESTED = 3
  }
  //sử dụng high-order bắt sự kiện click item
  /*fun RecyclerView.addOnItemClickListener(onClickListener: (position: Int) -> Unit){
    this.addOnChildAttachStateChangeListener(object : RecyclerView.OnChildAttachStateChangeListener{
      override fun onChildViewAttachedToWindow(view: View) {
        view.setOnClickListener(null)
      }

      override fun onChildViewDetachedFromWindow(view: View) {
        view.setOnClickListener {
          val holder = getChildViewHolder(view)
          onClickListener.invoke(holder.adapterPosition)
        }
      }

    })
  }*/
}

class GridSpanSizeLookup(private val adapter: RecyclerAdapter) : GridLayoutManager.SpanSizeLookup() {
  override fun getSpanSize(position: Int): Int {
    return adapter.spanSizeLookup(position)
  }
}
