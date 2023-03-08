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
  private val path = Path()
  private val rectF = RectF()
  private var radius = 50f
  private val color1 = ContextCompat.getColor(context, R.color.orange)
  override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    super.onSizeChanged(w, h, oldw, oldh)
    val rail = floatArrayOf(0f, 0f, 0f, 0f, radius, radius, radius, radius)
    rectF.set(0f, 0f + height / 12f, 0f + w, 0f + h)
    path.apply {
      moveTo(0f, 0f)
      lineTo(0f + 7.9f * width / 12f, 0f)
      lineTo(0f + 8.15f * width / 12f, 0f + height / 12f)
      lineTo(0f + 8.4f * width / 12f, 0f)
      lineTo(0f + width, 0f)
      lineTo(0f + width, 0f + height / 12f)
      lineTo(0f, 0f + height / 12f)
      close()
      addRoundRect(rectF, rail, Path.Direction.CW)
    }
  }

  override fun onDraw(canvas: Canvas?) {
    super.onDraw(canvas)
    canvas?.clipPath(path)
    val paint = Paint()
    paint.apply {
      style = Paint.Style.FILL_AND_STROKE
      color = color1
    }
    canvas?.drawPath(path, paint)
  }
}
