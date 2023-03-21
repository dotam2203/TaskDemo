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
import androidx.recyclerview.widget.GridLayoutManager
import com.task.adapter.GridSpanSizeLookup
import com.task.adapter.RecyclerAdapter
import com.task.adapter.RecyclerAdapter.Companion.TYPE_LAYOUT_CARD
import com.task.adapter.RecyclerAdapter.Companion.TYPE_LAYOUT_NESTED
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
    binding.recycleList.apply {
      adapter = RecyclerAdapter(items) { item, position, layoutType ->
        when (layoutType) {
          TYPE_LAYOUT_CARD -> {
            val data = item as ParentList.DescriptionItem
            dialogDataShow("${data.value}\n${data.name}")
            //Toast.makeText(this@MainActivity, "onclick Card - position ${data.value}", Toast.LENGTH_SHORT).show()
          }
          TYPE_LAYOUT_NESTED -> {
            val listData = arrayListOf<String>()
            for (data in items.slice(3..6)) {
              if (data is ParentList.DescriptionItemChild) {
                listData.addAll(listOf(data.description))
              }
            }
            //ngắt chuỗi
            dialogDataShow("$position\n${listData.joinToString("\n")}")
            //Toast.makeText(this@MainActivity, "onclick Nested - position ${listData.size}", Toast.LENGTH_SHORT).show()
          }
        }
      }
      val layoutManagerGrid = GridLayoutManager(this@MainActivity, 2)
      layoutManagerGrid.spanSizeLookup = GridSpanSizeLookup(adapter = adapter as RecyclerAdapter)
      layoutManager = layoutManagerGrid
    }
  }

  private fun generateData(): ArrayList<ParentList> {
    val list = ArrayList<ParentList>()
    //data TitleChoose
    val title = ParentList.TitleChoose("What would you \nlike to choose?")
    //data DescriptionItem
    val data1 = ParentList.DescriptionItem(1, "Đỗ Tâm")
    val data2 = ParentList.DescriptionItem(2, "Lê Đỗ")
    //data DescriptionItemChild
    val view1 = ParentList.DescriptionItemChild("Full HD video resolution")
    val view2 = ParentList.DescriptionItemChild("3-day event-based cloud video storage")
    val view3 = ParentList.DescriptionItemChild("Al feature (human detection)")
    val view4 = ParentList.DescriptionItemChild("No-cost maintenance and 24/7 support services")
    //add list
    list.add(title)
    list.add(data1)
    list.add(data2)
    list.add(view1)
    list.add(view2)
    list.add(view3)
    list.add(view4)
    return list
  }

  fun dialogDataShow(msg: String) {
    val dialog = Dialog(this)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setContentView(R.layout.dialog_data)

    val window: Window? = dialog.window
    window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    val windowAtrributes: WindowManager.LayoutParams = window!!.attributes
    windowAtrributes.gravity = Gravity.CENTER
    window.attributes = windowAtrributes
    //click ra bên ngoài để tắt dialog
    //false = no; true = yes
    dialog.setCancelable(true)
    dialog.show()
    val text: TextView = dialog.findViewById(R.id.text_data)
    text.text = msg
    //dialog.dismiss()
  }
}