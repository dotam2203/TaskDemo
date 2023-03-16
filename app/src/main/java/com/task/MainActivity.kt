package com.task

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.task.adapter.GridSpanSizeLookup
import com.task.adapter.RecyclerAdapter
import com.task.databinding.ActivityMainBinding
import com.task.model.ParentList

class MainActivity : AppCompatActivity() {
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
    layoutManagerGrid.spanSizeLookup = GridSpanSizeLookup(RecyclerAdapter(items))
    binding.recycleList.apply {
      adapter = RecyclerAdapter(items)
      layoutManager = layoutManagerGrid
    }
  }

  private fun generateData(): ParentList.DescriptionItemChild {
    val list = ParentList.DescriptionItemChild(arrayListOf()).description.apply {
      add("Full HD video resolution")
      add("3-day event-based cloud video storage")
      add("Al feature (human detection)")
      add("No-cost maintenance and 24/7 support services")
    }
    return ParentList.DescriptionItemChild(list)
  }
}