package com.task.model

import android.view.View

/**
 * Author: tamdt35@fpt.com.vn
 * Date: 02/03/2023
 */
sealed class ParentList{
  object TitleChoose : ParentList(){
    override fun toString(): String {
      return "What would you \nlike to choose?"
    }
  }
  data class DescriptionItem1(var url: String, var imageUrl: String): ParentList()
  data class DescriptionItem2(var description: String): ParentList()
}


