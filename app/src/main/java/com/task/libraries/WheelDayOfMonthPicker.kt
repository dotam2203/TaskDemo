package com.task.libraries

import android.content.Context
import android.util.AttributeSet

class WheelDayOfMonthPicker @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null) : WheelPicker<String?>(context, attrs) {
    private var daysInMonth = 0
    private var dayOfMonthSelected: DayOfMonthSelectedListener? = null
    private var finishedLoopListener: FinishedLoopListener? = null
    override fun init() {
        // no-op here
    }

    override fun generateAdapterValues(showOnlyFutureDates: Boolean): List<String> {
        val dayList: MutableList<String> = ArrayList()
        for (i in 1..daysInMonth) {
            dayList.add(String.format("%02d", i))
        }
        return dayList
    }

    override fun initDefault(): String = dateHelper.getDay(dateHelper.today()).toString()

    fun setOnFinishedLoopListener(onFinishedLoop: (picker: WheelDayOfMonthPicker?) -> Unit) {
        this.finishedLoopListener = object : FinishedLoopListener{
            override fun onFinishedLoop(picker: WheelDayOfMonthPicker?) {
                onFinishedLoop(picker)
            }
        }
    }

    override fun onFinishedLoop() {
        super.onFinishedLoop()
        if (finishedLoopListener != null) {
            finishedLoopListener!!.onFinishedLoop(this)
        }
    }

    fun setDayOfMonthSelectedListener(onDayOfMonthSelected: (picker: WheelDayOfMonthPicker?, dayIndex: Int) -> Unit) {
        this.dayOfMonthSelected = object : DayOfMonthSelectedListener{
            override fun onDayOfMonthSelected(picker: WheelDayOfMonthPicker?, dayIndex: Int) {
                onDayOfMonthSelected(picker, dayIndex)
            }
        }
    }

    fun setDaysInMonth(daysInMonth: Int) {
        this.daysInMonth = daysInMonth
    }

    val currentDay: Int
        get() = currentItemPosition

    interface FinishedLoopListener {
        fun onFinishedLoop(picker: WheelDayOfMonthPicker?)
    }

    interface DayOfMonthSelectedListener {
        fun onDayOfMonthSelected(picker: WheelDayOfMonthPicker?, dayIndex: Int)
    }
}