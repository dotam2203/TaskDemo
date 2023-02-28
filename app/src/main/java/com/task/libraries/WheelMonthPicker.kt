package com.task.libraries

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import java.text.SimpleDateFormat
import java.util.*

class WheelMonthPicker (context: Context, attrs: AttributeSet?) : WheelPicker<String?>(context, attrs) {
    private var onMonthSelected: MonthSelectedListener? = null
    private var displayMonthNumbers = false
    var monthFormat: String? = null
        get() = if (TextUtils.isEmpty(field)) {
            MONTH_FORMAT
        } else {
            field
        }
    override fun init() {}
    override fun generateAdapterValues(showOnlyFutureDates: Boolean): List<String> {
        val monthList: MutableList<String> = ArrayList()
        val month_date = SimpleDateFormat(monthFormat, currentLocale)
        val cal = Calendar.getInstance(currentLocale)
        cal.timeZone = dateHelper.getTimeZone()
        cal[Calendar.DAY_OF_MONTH] = 1
        for (i in 0..11) {
            cal[Calendar.MONTH] = i
            if (displayMonthNumbers) {
                monthList.add(String.format("%02d", i + 1))
            } else {
                monthList.add(month_date.format(cal.time))
            }
        }
        return monthList
    }

    override fun initDefault(): String = dateHelper.getMonth(dateHelper.today()).toString()

    fun setOnMonthSelectedListener(onMonthSelected: (picker: WheelMonthPicker?, monthIndex: Int, monthName: String?) -> Unit) {
        this.onMonthSelected = object : MonthSelectedListener{
            override fun onMonthSelected(picker: WheelMonthPicker?, monthIndex: Int, monthName: String?) {
                onMonthSelected(picker, monthIndex, monthName)
            }
        }
    }

    fun displayMonthNumbers(): Boolean {
        return displayMonthNumbers
    }

    fun setDisplayMonthNumbers(displayMonthNumbers: Boolean) {
        this.displayMonthNumbers = displayMonthNumbers
    }

    val currentMonth: Int
        get() = currentItemPosition

    interface MonthSelectedListener {
        fun onMonthSelected(picker: WheelMonthPicker?, monthIndex: Int, monthName: String?)
    }

    companion object {
        const val MONTH_FORMAT = "MMMM"
    }
}