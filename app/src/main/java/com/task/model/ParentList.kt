package com.task.model

import android.view.View
import com.task.model.ParentList.EmptyString.id

/**
 * Author: tamdt35@fpt.com.vn
 * Date: 02/03/2023
 */
sealed class ParentList(var id: Int = 0) {
  data class TitleChoose(var idChoose: Int = 0,var title: String = "") : ParentList(idChoose)
  data class DescriptionItem(var idItem: Int = 0, var name: String = "") : ParentList(idItem)
  data class DescriptionItemChild(var idItemChild: Int = 0, var description: String = "") : ParentList(idItemChild)
  object EmptyString : ParentList() {
    override fun toString(): String {
      return "List Empty!"
    }
  }
}


