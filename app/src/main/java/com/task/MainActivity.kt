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
      val _adapter = RecyclerAdapter(items)
      adapter = _adapter
      _adapter.onItemClick = { item, layoutType ->
        when(layoutType){
          R.layout.layout_item_card -> {
            Toast.makeText(this@MainActivity, "onclick Card", Toast.LENGTH_SHORT).show()
          }
          R.layout.layout_item_nested -> {
            Toast.makeText(this@MainActivity, "onclick Nested", Toast.LENGTH_SHORT).show()
          }
        }
      }
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
  fun dialogString(msg: String){
    val dialog = Dialog(this)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setContentView(R.layout.dialog_data)

    val window: Window? = dialog.window
    window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    val windowAtrributes : WindowManager.LayoutParams = window!!.attributes
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