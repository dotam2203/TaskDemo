package com.task.model

import android.view.View

/**
 * Author: tamdt35@fpt.com.vn
 * Date: 02/03/2023
 */
sealed class ParentList {
  data class TitleChoose(val title: String) : ParentList()
  data class DescriptionItem(var url: String, var imageUrl: String) : ParentList()
  data class DescriptionItemChild(var description: ArrayList<String>) : ParentList()
  object EmptyString : ParentList() {
    override fun toString(): String {
      return "List Empty!"
    }
  }
}


