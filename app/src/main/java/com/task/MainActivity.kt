package com.task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.task.adapter.ListItemAdapter
import com.task.databinding.ActivityMainBinding
import com.task.model.ParentList
import com.task.model.SubList

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding

  /* private val listItemAdapter by lazy {
       ListItemAdapter()
   }*/
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    initAdapter()
  }
  private fun initAdapter() {
    val list1 = listOf(
      SubList("Full HD video resolution"),
      SubList("3-day event-based cloud video storage"),
      SubList("Al feature (human detection)"),
      SubList("No-cost maintenance and 24/7 support services")
    )
    val list2 = listOf(
      SubList("Full HD video resolution"),
      SubList("3-day event-based cloud video storage"),
      SubList("Al feature (human detection)"),
      SubList("No-cost maintenance and 24/7 support services")
    )
    val list3 = listOf(
      SubList("Full HD video resolution"),
      SubList("3-day event-based cloud video storage"),
      SubList("Al feature (human detection)"),
      SubList("No-cost maintenance and 24/7 support services")
    )
    val list4 = listOf(
      SubList("Full HD video resolution"),
      SubList("3-day event-based cloud video storage"),
      SubList("Al feature (human detection)"),
      SubList("No-cost maintenance and 24/7 support services")
    )
    val list5 = listOf(
      SubList("Full HD video resolution"),
      SubList("3-day event-based cloud video storage"),
      SubList("Al feature (human detection)"),
      SubList("No-cost maintenance and 24/7 support services")
    )
    val parentList = listOf(ParentList(list1),
                            ParentList(list2),
                            ParentList(list3),
                            ParentList(list4),
                            ParentList(list5))
    binding.recycleList.apply {
      layoutManager = LinearLayoutManager(this@MainActivity)
      adapter = ListItemAdapter(parentList as MutableList<ParentList>)
    }
  }

}