package com.task.libraries

import android.content.Context
import android.util.AttributeSet
import com.task.R
import java.text.SimpleDateFormat
import java.util.*

class WheelYearPicker(context: Context?, attrs: AttributeSet?) : WheelPicker<String?>(context, attrs){
    private var simpleDateFormat: SimpleDateFormat? = null
    @JvmField
    var minYear = 0
    private var maxYear = 0
    private var onYearSelectedListener: OnYearSelectedListener? = null

    override fun init() {
        simpleDateFormat = SimpleDateFormat("yyyy", currentLocale)
        val instance = Calendar.getInstance()
        instance.timeZone = dateHelper.getTimeZone()
        val currentYear = instance[Calendar.YEAR]
        minYear = currentYear - SingleDateConstants.MIN_YEAR_DIFF
        maxYear = currentYear + SingleDateConstants.MAX_YEAR_DIFF
    }

    override fun initDefault(): String = todayText

    private val todayText: String = getLocalizedString(R.string.picker_today)

    fun setMaxYear(maxYear: Int) {
        this.maxYear = maxYear
        notifyDatasetChanged()
    }

    fun setMinYear(minYear: Int) {
        this.minYear = minYear
        notifyDatasetChanged()
    }

    override fun generateAdapterValues(showOnlyFutureDates: Boolean): List<String> {
        val years: MutableList<String> = ArrayList()
        val instance = Calendar.getInstance()
        instance.timeZone = dateHelper.getTimeZone()
        instance[Calendar.YEAR] = minYear - 1
        for (i in minYear..maxYear) {
            instance.add(Calendar.YEAR, 1)
            years.add(getFormattedValue(instance.time))
        }
        return years
    }

    override fun getFormattedValue(value: Any): String = simpleDateFormat!!.format(value)

    fun setOnYearSelectedListener(onYearSelected: (picker: WheelYearPicker?, position: Int, year: Int) -> Unit){
        this.onYearSelectedListener = object : OnYearSelectedListener{
            override fun onYearSelected(picker: WheelYearPicker?, position: Int, year: Int) {
                onYearSelected(picker, position, year)
            }
        }
    }

    val currentYear: Int
        get() = convertItemToYear(super.getCurrentItemPosition())

    private fun convertItemToYear(itemPosition: Int): Int =  minYear + itemPosition

    interface OnYearSelectedListener {
        fun onYearSelected(picker: WheelYearPicker?, position: Int, year: Int)
    }
}