package com.task

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.task.databinding.ChildItemBinding
import com.task.model.ChildList

class CustomLayout : AppCompatActivity() {
    private lateinit var binding: ChildItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ChildItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val item = listOf<ChildList>(
          ChildList("Full HD video resolution"),
          ChildList("3-day event-based cloud video storage"),
          ChildList("Al feature (human detection)"),
          ChildList("No-cost maintenance and 24/7 support services")
        )
        getViewList(item)
    }

    private fun getViewList(item: List<ChildList>) {
        val view1: View = layoutInflater.inflate(R.layout.child_item, null)
        val view2: View = layoutInflater.inflate(R.layout.child_item, null)
        val view3: View = layoutInflater.inflate(R.layout.child_item, null)
        val view4: View = layoutInflater.inflate(R.layout.child_item, null)
       // view = ItemListBinding.inflate(layoutInflater,null)
        //val icon = view.findViewById<ImageView>(R.id.imvIcon)
        val title1 = view1.findViewById<TextView>(R.id.text_title)
        val title2 = view2.findViewById<TextView>(R.id.text_title)
        val title3 = view3.findViewById<TextView>(R.id.text_title)
        val title4 = view4.findViewById<TextView>(R.id.text_title)
        val click1 = view1.findViewById<LinearLayout>(R.id.linear_child_item)
        val click2 = view2.findViewById<LinearLayout>(R.id.linear_child_item)
        val click3 = view3.findViewById<LinearLayout>(R.id.linear_child_item)
        val click4 = view4.findViewById<LinearLayout>(R.id.linear_child_item)
        title1.text = item[0].title
        title2.text = item[1].title
        title3.text = item[2].title
        title4.text = item[3].title
        binding.linearChildItem.apply {
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