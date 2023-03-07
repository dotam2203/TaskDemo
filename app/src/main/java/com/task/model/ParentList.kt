package com.task.model

import android.view.View

/**
 * Author: tamdt35@fpt.com.vn
 * Date: 02/03/2023
 */
data class ParentList(
  val childList: List<ChildList> = emptyList(),
  var additionalViews: List<View>? = null
  )
