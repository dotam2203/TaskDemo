package com.task

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.recyclerview.widget.GridLayoutManager
import com.task.adapter.GridSpanSizeLookup
import com.task.adapter.RecyclerAdapter
import com.task.adapter.RecyclerAdapter.Companion.TYPE_LAYOUT_CARD
import com.task.adapter.RecyclerAdapter.Companion.TYPE_LAYOUT_NESTED
import com.task.databinding.ActivityMainBinding
import com.task.model.ParentList

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  private var items = ArrayList<ParentList>()
  private val recyclerAdapter by lazy {
    RecyclerAdapter(items) { item, position, layoutType ->
      when (layoutType) {
        TYPE_LAYOUT_CARD -> {
          val data = item as ParentList.DescriptionItem
          dialogDataShow("${data.id}\n${data.name}")
        }
        TYPE_LAYOUT_NESTED -> {
          val listData = items.filterIsInstance<ParentList.DescriptionItemChild>().map {
            it.description
          }
          //ngắt chuỗi: joinToString
          dialogDataShow("$position\n${listData.joinToString("\n")}")
        }
      }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    initAdapter()
  }

  private fun initAdapter() {
    val layoutManagerGrid = GridLayoutManager(this@MainActivity, 2)
    items = generateData()
    binding.recycleList.apply {
      adapter = recyclerAdapter
      layoutManagerGrid.spanSizeLookup = GridSpanSizeLookup(adapter = recyclerAdapter)
      layoutManager = layoutManagerGrid
    }
  }

  private fun generateData() = arrayListOf(
    ParentList.TitleChoose("What would you \nlike to choose?"),
    ParentList.DescriptionItem(1, "Đỗ Tâm"),
    ParentList.DescriptionItem(2, "Lê Đỗ"),
    ParentList.DescriptionItemChild(3, "Full HD video resolution"),
    ParentList.DescriptionItemChild(4, "3-day event-based cloud video storage"),
    ParentList.DescriptionItemChild(5, "Al feature (human detection)"),
    ParentList.DescriptionItemChild(6, "No-cost maintenance and 24/7 support services")
  )

  private fun dialogDataShow(msg: String) {
    val dialog = Dialog(this).apply {
      requestWindowFeature(Window.FEATURE_NO_TITLE)
      setContentView(R.layout.dialog_data)
      window?.apply {
        setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        attributes.gravity = Gravity.CENTER
      }
      //click ra bên ngoài để tắt dialog
      //false = no; true = yes
      setCancelable(true)
      show()
    }
    dialog.findViewById<TextView>(R.id.text_data).text = msg
    //dialog.dismiss()
  }
}