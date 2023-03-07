package com.task

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
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
    val cornerRadius = 20f
    // Vẽ hình
    val path = Path()
    path.apply {
      moveTo(0f, 0f)
      lineTo(0f + width, 0f)
      lineTo(0f + width, 0f + height)
      lineTo(0f, 0f + height)
    }
    path.close()
    // Tô màu
    val fillPaint = Paint()
    fillPaint.style = Paint.Style.FILL
    fillPaint.color = color1
    canvas?.drawPath(path, fillPaint)

    // Vẽ đường viền
    val strokePaint = Paint()
    strokePaint.style = Paint.Style.STROKE
    strokePaint.strokeWidth = 1f
    strokePaint.color = color1
    canvas?.drawPath(path, strokePaint)
  }
}
