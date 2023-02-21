package com.task.libraries

import android.content.Context
import android.graphics.Typeface
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.task.R
import com.task.databinding.DatePickerSpinnerBinding
import java.text.SimpleDateFormat
import java.util.*

class SingleDatePicker @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {
    private lateinit var binding: DatePickerSpinnerBinding
    private var dateHelper = DateHelper()
    private val yearsPicker: WheelYearPicker
    private val monthPicker: WheelMonthPicker
    private val daysOfMonthPicker: WheelDayOfMonthPicker
    private val daysPicker: WheelDayPicker
    private val pickers: MutableList<WheelPicker<*>> = ArrayList()
    private val listeners: List<OnDateChangedListener> = ArrayList()
    private val dtSelector: View
    private var mustBeOnFuture = false
    private var minDate: Date? = null
    private var maxDate: Date? = null
    private var defaultDate: Date
    private var displayYears = false
    private var displayMonth = false
    private var displayDaysOfMonth = false
    private var displayDays = true
    fun setDateHelper(dateHelper: DateHelper) {
        this.dateHelper = dateHelper
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        yearsPicker.setOnYearSelectedListener { picker, position, year ->
            checkMinMaxDate(picker)
            if (displayDaysOfMonth) {
                updateDaysOfMonth()
            }
        }
        monthPicker.setOnMonthSelectedListener { picker, monthIndex, monthName ->
            checkMinMaxDate(picker)
            if (displayDaysOfMonth) {
                updateDaysOfMonth()
            }
        }
        daysOfMonthPicker
            .setDayOfMonthSelectedListener { picker, dayIndex -> checkMinMaxDate(picker) }
        daysOfMonthPicker
            .setOnFinishedLoopListener {
                if (displayMonth) {
                    monthPicker.scrollTo(monthPicker.currentItemPosition + 1)
                    updateDaysOfMonth()
                }
            }
        daysPicker
            .setOnDaySelectedListener { picker, position, name, date -> checkMinMaxDate(picker) }
        setDefaultDate(defaultDate) //update displayed date
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        for (picker in pickers) {
            picker.isEnabled = enabled
        }
    }

    fun setDisplayYears(displayYears: Boolean) {
        this.displayYears = displayYears
        yearsPicker.visibility = if (displayYears) VISIBLE else GONE
    }

    fun setDisplayMonths(displayMonths: Boolean) {
        displayMonth = displayMonths
        monthPicker.visibility = if (displayMonths) VISIBLE else GONE
        checkSettings()
    }

    fun setDisplayDaysOfMonth(displayDaysOfMonth: Boolean) {
        this.displayDaysOfMonth = displayDaysOfMonth
        daysOfMonthPicker.visibility = if (displayDaysOfMonth) VISIBLE else GONE
        if (displayDaysOfMonth) {
            updateDaysOfMonth()
        }
        checkSettings()
    }

    fun setDisplayDays(displayDays: Boolean) {
        this.displayDays = displayDays
        daysPicker.visibility = if (displayDays) VISIBLE else GONE
        checkSettings()
    }

    fun setDisplayMonthNumbers(displayMonthNumbers: Boolean) {
        monthPicker.setDisplayMonthNumbers(displayMonthNumbers)
        monthPicker.updateAdapter()
    }

    fun setMonthFormat(monthFormat: String?) {
        monthPicker.monthFormat = monthFormat
        monthPicker.updateAdapter()
    }

    fun setTodayText(todayText: DateWithLabel?) {
        if (todayText?.label != null && todayText.label.isNotEmpty()) {
            daysPicker.setTodayText(todayText)
        }
    }

    fun setItemSpacing(size: Int) {
        for (picker in pickers) {
            picker.setItemSpace(size)
        }
    }

    fun setCurvedMaxAngle(angle: Int) {
        for (picker in pickers) {
            picker.setCurvedMaxAngle(angle)
        }
    }

    fun setCurved(curved: Boolean) {
        for (picker in pickers) {
            picker.setCurved(curved)
        }
    }

    fun setCyclic(cyclic: Boolean) {
        for (picker in pickers) {
            picker.setCyclic(cyclic)
        }
    }

    fun setTextSize(textSize: Int) {
        for (picker in pickers) {
            picker.setItemTextSize(textSize)
        }
    }

    fun setSelectedTextColor(selectedTextColor: Int) {
        for (picker in pickers) {
            picker.setSelectedItemTextColor(selectedTextColor)
        }
    }

    fun setTextColor(textColor: Int) {
        for (picker in pickers) {
            picker.setItemTextColor(textColor)
        }
    }

    fun setTextAlign(align: Int) {
        for (picker in pickers) {
            picker.setItemAlign(align)
        }
    }

    fun setTypeface(typeface: Typeface?) {
        if (typeface == null) return
        for (picker in pickers) {
            picker.setTypeface(typeface)
        }
    }

    private fun setFontToAllPickers(resourceId: Int) {
        if (resourceId > 0) {
            for (i in pickers.indices) {
                pickers[i].setTypeface(ResourcesCompat.getFont(context, resourceId))
            }
        }
    }

    fun setSelectorColor(selectorColor: Int) {
        dtSelector.setBackgroundColor(selectorColor)
    }

    fun setSelectorHeight(selectorHeight: Int) {
        val dtSelectorLayoutParams = dtSelector.layoutParams
        dtSelectorLayoutParams.height = selectorHeight
        dtSelector.layoutParams = dtSelectorLayoutParams
    }

    fun setVisibleItemCount(visibleItemCount: Int) {
        for (picker in pickers) {
            picker.setVisibleItemCount(visibleItemCount)
        }
    }

    fun setDayFormatter(simpleDateFormat: SimpleDateFormat?) {
        if (simpleDateFormat != null) {
            daysPicker.setDayFormatter(simpleDateFormat)
        }
    }

    fun setMinDate(minDate: Date?) {
        val calendar = Calendar.getInstance()
        calendar.timeZone = dateHelper.getTimeZone()
        calendar.time = minDate
        this.minDate = calendar.time
        setMinYear()
    }

    fun setMaxDate(maxDate: Date?) {
        val calendar = Calendar.getInstance()
        calendar.timeZone = dateHelper.getTimeZone()
        calendar.time = maxDate
        this.maxDate = calendar.time
        setMinYear()
    }

    fun setCustomLocale(locale: Locale?) {
        for (p in pickers) {
            p.setCustomLocale(locale)
            p.updateAdapter()
        }
    }

    private fun checkMinMaxDate(picker: WheelPicker<*>) {
        checkBeforeMinDate(picker)
        checkAfterMaxDate(picker)
    }

    private fun checkBeforeMinDate(picker: WheelPicker<*>) {
        picker.postDelayed({
            if (minDate != null && isBeforeMinDate(date)) {
                for (p in pickers) {
                    p.scrollTo(p.findIndexOfDate(minDate!!))
                }
            }
        }, DELAY_BEFORE_CHECK_PAST.toLong())
    }

    private fun checkAfterMaxDate(picker: WheelPicker<*>) {
        picker.postDelayed({
            if (maxDate != null && isAfterMaxDate(date)) {
                for (p in pickers) {
                    p.scrollTo(p.findIndexOfDate(maxDate!!))
                }
            }
        }, DELAY_BEFORE_CHECK_PAST.toLong())
    }

    private fun isBeforeMinDate(date: Date): Boolean {
        return dateHelper.getCalendarOfDate(date).before(dateHelper.getCalendarOfDate(minDate))
    }

    private fun isAfterMaxDate(date: Date): Boolean {
        return dateHelper.getCalendarOfDate(date).after(dateHelper.getCalendarOfDate(maxDate))
    }

    val date: Date
        get() {
            val calendar = Calendar.getInstance()
            calendar.timeZone = dateHelper.getTimeZone()
            if (displayDays) {
                val dayDate = daysPicker.currentDate
                calendar.time = dayDate
            } else {
                if (displayMonth) {
                    calendar[Calendar.MONTH] = monthPicker.currentMonth
                }
                if (displayYears) {
                    calendar[Calendar.YEAR] = yearsPicker.currentYear
                }
                if (displayDaysOfMonth) {
                    val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
                    if (daysOfMonthPicker.currentDay >= daysInMonth) {
                        calendar[Calendar.DAY_OF_MONTH] = daysInMonth
                    } else {
                        calendar[Calendar.DAY_OF_MONTH] = daysOfMonthPicker.currentDay + 1
                    }
                }
            }
            return calendar.time
        }

    fun setDefaultDate(date: Date?) {
        if (date != null) {
            val calendar = Calendar.getInstance()
            calendar.timeZone = dateHelper.getTimeZone()
            calendar.time = date
            defaultDate = calendar.time
            updateDaysOfMonth(calendar)
            for (picker in pickers) {
                picker.setDefaultDate(defaultDate)
            }
        }
    }

    private fun updateDaysOfMonth() {
        val date = date
        val calendar = Calendar.getInstance()
        calendar.timeZone = dateHelper.getTimeZone()
        calendar.time = date
        updateDaysOfMonth(calendar)
    }

    private fun updateDaysOfMonth(calendar: Calendar) {
        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        daysOfMonthPicker.setDaysInMonth(daysInMonth)
        daysOfMonthPicker.updateAdapter()
    }

    fun setMustBeOnFuture(mustBeOnFuture: Boolean) {
        this.mustBeOnFuture = mustBeOnFuture
        daysPicker.showOnlyFutureDate = mustBeOnFuture
        if (mustBeOnFuture) {
            val now = Calendar.getInstance()
            now.timeZone = dateHelper.getTimeZone()
            minDate = now.time //minDate is Today
        }
    }

    private fun setMinYear() {
        if (displayYears && minDate != null && maxDate != null) {
            val calendar = Calendar.getInstance()
            calendar.timeZone = dateHelper.getTimeZone()
            calendar.time = minDate
            yearsPicker.setMinYear(calendar[Calendar.YEAR])
            calendar.time = maxDate
            yearsPicker.setMaxYear(calendar[Calendar.YEAR])
        }
    }

    private fun checkSettings() {
        require(!(displayDays && (displayDaysOfMonth || displayMonth))) { "You can either display days with months or days and months separately" }
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.SingleDatePicker)
        val resources = resources
        setTodayText(DateWithLabel(a.getString(R.styleable.SingleDatePicker_picker_todayText), Date()))
        setTextColor(a.getColor(R.styleable.SingleDatePicker_picker_textColor, ContextCompat.getColor(context, R.color.gray_200)))
        setSelectedTextColor(a.getColor(R.styleable.SingleDatePicker_picker_selectedTextColor, ContextCompat.getColor(context, R.color.black)))
        setSelectorColor(a.getColor(R.styleable.SingleDatePicker_picker_selectorColor, ContextCompat.getColor(context, R.color.gray_300)))
        setItemSpacing(a.getDimensionPixelSize(R.styleable.SingleDatePicker_picker_itemSpacing, resources.getDimensionPixelSize(R.dimen.wheelSelectorHeight)))
        setCurvedMaxAngle(a.getInteger(R.styleable.SingleDatePicker_picker_curvedMaxAngle, WheelPicker.MAX_ANGLE))
        setSelectorHeight(a.getDimensionPixelSize(R.styleable.SingleDatePicker_picker_selectorHeight, resources.getDimensionPixelSize(R.dimen.wheelSelectorHeight)))
        setTextSize(a.getDimensionPixelSize(R.styleable.SingleDatePicker_picker_textSize, resources.getDimensionPixelSize(R.dimen.WheelItemTextSize)))
        setCurved(a.getBoolean(R.styleable.SingleDatePicker_picker_curved, IS_CURVED_DEFAULT))
        setCyclic(a.getBoolean(R.styleable.SingleDatePicker_picker_cyclic, IS_CYCLIC_DEFAULT))
        setMustBeOnFuture(a.getBoolean(R.styleable.SingleDatePicker_picker_mustBeOnFuture, MUST_BE_ON_FUTURE_DEFAULT))
        setVisibleItemCount(a.getInt(R.styleable.SingleDatePicker_picker_visibleItemCount, VISIBLE_ITEM_COUNT_DEFAULT))
        daysPicker.setDayCount(a.getInt(R.styleable.SingleDatePicker_picker_dayCount, SingleDateConstants.DAYS_PADDING))
        setDisplayDays(a.getBoolean(R.styleable.SingleDatePicker_picker_displayDays, displayDays))
        setDisplayMonths(a.getBoolean(R.styleable.SingleDatePicker_picker_displayMonth, displayMonth))
        setDisplayYears(a.getBoolean(R.styleable.SingleDatePicker_picker_displayYears, displayYears))
        setDisplayDaysOfMonth(a.getBoolean(R.styleable.SingleDatePicker_picker_displayDaysOfMonth, displayDaysOfMonth))
        setDisplayMonthNumbers(a.getBoolean(R.styleable.SingleDatePicker_picker_displayMonthNumbers, monthPicker.displayMonthNumbers()))
        setFontToAllPickers(a.getResourceId(R.styleable.SingleDatePicker_fontFamily, 0))
        val monthFormat = a.getString(R.styleable.SingleDatePicker_picker_monthFormat)
        setMonthFormat(if (TextUtils.isEmpty(monthFormat)) WheelMonthPicker.MONTH_FORMAT else monthFormat)
        setTextAlign(a.getInt(R.styleable.SingleDatePicker_picker_textAlign, ALIGN_CENTER))
        checkSettings()
        setMinYear()
        a.recycle()
        if (displayDaysOfMonth) {
            val now = Calendar.getInstance()
            now.timeZone = dateHelper.getTimeZone()
            updateDaysOfMonth(now)
        }
        daysPicker.updateAdapter() // For MustBeFuture and dayCount
    }

    interface OnDateChangedListener {
        fun onDateChanged(displayed: String?, date: Date?)
    }

    companion object {
        const val IS_CYCLIC_DEFAULT = true
        const val IS_CURVED_DEFAULT = false
        const val MUST_BE_ON_FUTURE_DEFAULT = false
        const val DELAY_BEFORE_CHECK_PAST = 200
        private const val VISIBLE_ITEM_COUNT_DEFAULT = 7
        const val ALIGN_CENTER = 0
    }

    init {
        binding = DatePickerSpinnerBinding.inflate(LayoutInflater.from(context),null,false)
        defaultDate = Date()
        inflate(context, R.layout.date_picker_spinner, this)
        yearsPicker = findViewById(R.id.yearPicker)
        monthPicker = findViewById(R.id.monthPicker)
        daysOfMonthPicker = findViewById(R.id.daysOfMonthPicker)
        daysPicker = findViewById(R.id.daysPicker)
        dtSelector = findViewById(R.id.dtSelector)
        pickers.addAll(
            listOf(
                daysPicker,
                daysOfMonthPicker,
                monthPicker,
                yearsPicker
            )
        )
        for (wheelPicker in pickers) {
            wheelPicker.setDateHelper(dateHelper)
        }
        init(context, attrs)
    }
}