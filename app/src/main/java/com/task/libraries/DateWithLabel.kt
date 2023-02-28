package com.task.libraries

import java.util.*

data class DateWithLabel(
    var label: String = "",
    var date: Date? = null
) {
    init {
        date = date?: Date()
    }
   /* init {
        if(date == null)
            throw IllegalArgumentException("null value provided. label=[$label], date=[$date]")
    }
    override fun toString(): String = label
    override fun hashCode() : Int{
        throw IllegalStateException("Not implemented")
    }
    override fun equals(o : Any?): Boolean{
        if(o is DateWithLabel){
            return label == o.label
        }
        return false
    }*/
}