package com.task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil.setContentView
import androidx.recyclerview.widget.LinearLayoutManager
import com.task.adapter.ListItemAdapter
import com.task.databinding.ActivityMainBinding
import com.task.model.ItemModel

class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding
   /* private val listItemAdapter by lazy {
        ListItemAdapter()
    }*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAdapter()
        //getEvent()
    }

   /* private fun getEvent() {
        listItemAdapter.apply {
            item.addAll(list)
            diffUtil.submitList(list)
        }
    }*/

    private fun initAdapter() {
        val list = listOf(
            ItemModel("","A"),
            ItemModel("","B"),
            ItemModel("","C"),
            ItemModel("","D"),
            ItemModel("","D"),
            ItemModel("","D"),
            ItemModel("","D"),
            ItemModel("","E"))
        binding.rvList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = ListItemAdapter(list as MutableList<ItemModel>)
        }
    }

}