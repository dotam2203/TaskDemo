package com.task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.task.adapter.ListAdapter
import com.task.databinding.ActivityMainBinding
import com.task.model.ParentList
import com.task.model.ChildList

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    getEvent()
    //initAdapter()
  }

  private fun getEvent() {
    val items = generateData()
    binding.recycleList.apply {
        adapter = ListAdapter(items)
        layoutManager = LinearLayoutManager(this@MainActivity)
    }
  }

  private fun generateData(): List<ParentList> {
    val list = listOf(
      ChildList("Full HD video resolution"),
      ChildList("3-day event-based cloud video storage"),
      ChildList("Al feature (human detection)"),
      ChildList("No-cost maintenance and 24/7 support services")
    )
    return listOf(
      ParentList(list),
      ParentList(list),
      ParentList(list),
      ParentList(list),
      ParentList(list)
    )
  }
}