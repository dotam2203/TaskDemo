package com.task

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import java.lang.Math.*

/**
 * Author: tamdt35@fpt.com.vn
 * Date: 07/03/2023
 */
class DrawShape(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
  override fun onDraw(canvas: Canvas?) {
    super.onDraw(canvas)
    val color1 = ContextCompat.getColor(context,R.color.orange)
    val cornerRadius = 25f
    //round
    val radii = floatArrayOf(
      0f, 0f,
      0f,0f,
      cornerRadius, cornerRadius,
      cornerRadius, cornerRadius,
    )
    // Vẽ hình
    val path = Path()
    path.apply {
      moveTo(0f, 0f)
      lineTo(0f + 7.5f*width/12f,0f)
      lineTo(0f + 8*width/12f,0f + 1.5f*height/12f)
      lineTo(0f + 8.5f*width/12f,0f)
      lineTo(0f + width, 0f)
      lineTo(0f + width, 0f + height)
      lineTo(0f, 0f + height)
      //addRoundRect(RectF(0f + height, 0f + width, 0f + height, 0f + width),radii,Path.Direction.CW)
    }
    path.close()
    val pain = Paint()
    pain.apply {
      style = Paint.Style.FILL_AND_STROKE
      color = color1
      /*strokeJoin = Paint.Join.ROUND
      strokeCap = Paint.Cap.ROUND
      pathEffect = CornerPathEffect(cornerRadius)*/
    }
    canvas?.drawPath(path,pain)
  }
}
