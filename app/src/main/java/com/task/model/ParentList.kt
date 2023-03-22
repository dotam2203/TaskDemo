package com.task.model

import android.view.View

/**
 * Author: tamdt35@fpt.com.vn
 * Date: 02/03/2023
 */
sealed class ParentList {
  data class TitleChoose(var title: String = "") : ParentList()
  data class DescriptionItem(var id: Int = 0, var name: String = "") : ParentList()
  data class DescriptionItemChild(var id: Int = 0, var description: String = "") : ParentList()
  object EmptyString : ParentList() {
    override fun toString(): String {
      return "List Empty!"
    }
  }
}


