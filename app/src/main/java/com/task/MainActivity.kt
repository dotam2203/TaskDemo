package com.task

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.task.adapter.GridSpanSizeLookup
import com.task.adapter.RecyclerAdapter
import com.task.databinding.ActivityMainBinding
import com.task.model.ParentList

class MainActivity : AppCompatActivity(), RecyclerAdapter.OnClickItemRecyclerView {
  private lateinit var binding: ActivityMainBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    initAdapter()
  }

  private fun initAdapter() {
    val items = generateData()
    val layoutManagerGrid = GridLayoutManager(this@MainActivity, 2)
    layoutManagerGrid.spanSizeLookup = GridSpanSizeLookup(RecyclerAdapter(this@MainActivity, items, this@MainActivity))
    binding.recycleList.apply {
      adapter = RecyclerAdapter(this@MainActivity, items, this@MainActivity)
      layoutManager = layoutManagerGrid
    }
  }

  private fun generateData(): ArrayList<ParentList> {
    val list = ArrayList<ParentList>()
    //data TitleChoose
    val title = ParentList.TitleChoose("What would you \nlike to choose?")
    //data DescriptionItemChild
    val view1 = ParentList.DescriptionItemChild("Full HD video resolution")
    val view2 = ParentList.DescriptionItemChild("3-day event-based cloud video storage")
    val view3 = ParentList.DescriptionItemChild("Al feature (human detection)")
    val view4 = ParentList.DescriptionItemChild("No-cost maintenance and 24/7 support services")
    //add list
    list.add(title)
    list.add(view1)
    list.add(view2)
    list.add(view3)
    list.add(view4)
    return list
  }

  override fun itemClick(data: ParentList) {
    TODO("Not yet implemented")
  }
}