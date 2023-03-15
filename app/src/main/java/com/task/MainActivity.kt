package com.task

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.task.adapter.GridSpanSizeLookup
import com.task.adapter.ListAdapter
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
    val _layoutManager = GridLayoutManager(this@MainActivity, 2)
    _layoutManager.spanSizeLookup = GridSpanSizeLookup(ListAdapter(items))
    binding.recycleList.apply {
      adapter = ListAdapter(items)
      layoutManager = _layoutManager
    }
  }

  private fun generateData(): ArrayList<ParentList.DescriptionItem2> {
    val list = listOf(
      "Full HD video resolution",
      "3-day event-based cloud video storage",
      "Al feature (human detection)",
      "No-cost maintenance and 24/7 support services"
    )
    val items = ArrayList<ParentList.DescriptionItem2>()
    for (i in list.indices) {
      items.addAll(listOf(ParentList.DescriptionItem2(list[i])))
    }
    return items
  }
}