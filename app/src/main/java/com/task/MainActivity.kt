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
    val items = generateData() as ArrayList<ParentList>
    val _layoutManager = GridLayoutManager(this@MainActivity, 2)
    _layoutManager.spanSizeLookup = GridSpanSizeLookup(ListAdapter(items))
    binding.recycleList.apply {
      adapter = ListAdapter(items)
      layoutManager = _layoutManager
    }
  }

  private fun generateData(): List<ParentList> {
    val list = listOf(
      "What would you \nlike to choose?",
      "Full HD video resolution",
      "3-day event-based cloud video storage",
      "Al feature (human detection)",
      "No-cost maintenance and 24/7 support services"
    )
    val titles = ArrayList<ParentList>()
    for (i in list.indices) {
      titles.addAll(listOf(ParentList.TitleModel(list[i])))
    }
    return titles
  }
}