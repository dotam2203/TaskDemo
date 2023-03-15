package com.task.model

import android.view.View

/**
 * Author: tamdt35@fpt.com.vn
 * Date: 02/03/2023
 */
sealed class ParentList{
  data class TitleModel(var title: String): ParentList()
}


