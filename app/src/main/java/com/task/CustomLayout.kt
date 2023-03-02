package com.task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.task.databinding.ActivityCustomLayoutBinding
import com.task.databinding.ItemListBinding
import com.task.model.ItemModel

class CustomLayout : AppCompatActivity() {
    private lateinit var binding: ActivityCustomLayoutBinding
    //private lateinit var view: ItemListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val item = listOf<ItemModel>(
            ItemModel("","Full HD video resolution"),
            ItemModel("","3-day event-based cloud video storage"),
            ItemModel("","Al feature (human detection)"),
            ItemModel("","No-cost maintenance and 24/7 support services")
        )
        getViewList(item)
    }

    private fun getViewList(item: List<ItemModel>) {
        val view1: View = layoutInflater.inflate(R.layout.item_list,null)
        val view2: View = layoutInflater.inflate(R.layout.item_list,null)
        val view3: View = layoutInflater.inflate(R.layout.item_list,null)
        val view4: View = layoutInflater.inflate(R.layout.item_list,null)
       // view = ItemListBinding.inflate(layoutInflater,null)
        //val icon = view.findViewById<ImageView>(R.id.imvIcon)
        val title1 = view1.findViewById<TextView>(R.id.tvTitle)
        val title2 = view2.findViewById<TextView>(R.id.tvTitle)
        val title3 = view3.findViewById<TextView>(R.id.tvTitle)
        val title4 = view4.findViewById<TextView>(R.id.tvTitle)
        val click1 = view1.findViewById<LinearLayout>(R.id.llOnClick)
        val click2 = view2.findViewById<LinearLayout>(R.id.llOnClick)
        val click3 = view3.findViewById<LinearLayout>(R.id.llOnClick)
        val click4 = view4.findViewById<LinearLayout>(R.id.llOnClick)
        title1.text = item[0].title
        title2.text = item[1].title
        title3.text = item[2].title
        title4.text = item[3].title
        binding.llListView.apply {
            addView(view1)
            addView(view2)
            addView(view3)
            addView(view4)

            click1.setOnLongClickListener{
                removeView(view1)
                true
            }
            click2.setOnLongClickListener{
                removeView(view2)
                true
            }
            click3.setOnLongClickListener{
                removeView(view3)
                true
            }
            click4.setOnLongClickListener{
                removeView(view4)
                true
            }
        }
    }
}