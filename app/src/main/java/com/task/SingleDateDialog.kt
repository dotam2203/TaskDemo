package com.task

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import com.task.libraries.*
import java.text.SimpleDateFormat
import java.util.*

class SingleDateDialog private constructor(context: Context, bottomSheet: Boolean = false) : BaseDialog() {
    private val dateHelper = DateHelper()
    private var listener: Listener? = null
    private val bottomSheetHelper: BottomSheetHelper
    private var picker: SingleDatePicker? = null
    private var title: String? = null
    private var titleTextSize: Int? = null
    private var bottomSheetHeight: Int? = null
    private var todayText: String? = null
    private var displayListener: DisplayListener? = null
    private fun init(view: View) {
        picker = view.findViewById<View>(R.id.picker) as SingleDatePicker
        picker!!.setDateHelper(dateHelper)
        if (picker != null) {
            if (bottomSheetHeight != null) {
                val params = picker!!.layoutParams
                params.height = bottomSheetHeight as Int
                picker!!.layoutParams = params
            }
        }
        val buttonOk = view.findViewById<View>(R.id.tvApply) as TextView
        if (buttonOk != null) {
            buttonOk.setOnClickListener {
                okClicked = true
                close()
            }
            if (mainColor != null) {
                buttonOk.setTextColor(buttonColor!!)
            }
            if (titleTextSize != null) {
                buttonOk.textSize = titleTextSize!!.toFloat()
            }
        }
        val sheetContentLayout = view.findViewById<View>(R.id.sheetContentLayout)
        if (sheetContentLayout != null) {
            sheetContentLayout.setOnClickListener { }
            if (backgroundColor != null) {
                sheetContentLayout.setBackgroundColor(backgroundColor!!)
            }
        }
        val titleTextView = view.findViewById<View>(R.id.sheetTitle) as TextView
        if (titleTextView != null) {
            titleTextView.text = "Choose date of birth"
            if (titleTextColor != null) {
                titleTextView.setTextColor(titleTextColor!!)
            }
            if (titleTextSize != null) {
                titleTextView.textSize = titleTextSize!!.toFloat()
            }
        }
        picker!!.setTodayText(DateWithLabel(todayText, Date()))
        if (curved) {
            picker!!.setCurved(true)
            picker!!.setVisibleItemCount(7)
        } else {
            picker!!.setCurved(false)
            picker!!.setVisibleItemCount(5)
        }
        picker!!.setMustBeOnFuture(mustBeOnFuture)
        if (dayFormatter != null) {
            picker!!.setDayFormatter(dayFormatter)
        }
        if (customLocale != null) {
            picker!!.setCustomLocale(customLocale)
        }
        if (mainColor != null) {
            picker!!.setSelectedTextColor(mainColor!!)
        }

        // displayYears used in setMinDate / setMaxDate
        picker!!.setDisplayYears(displayYears)
        if (minDate != null) {
            picker!!.setMinDate(minDate)
        }
        if (maxDate != null) {
            picker!!.setMaxDate(maxDate)
        }
        if (defaultDate != null) {
            picker!!.setDefaultDate(defaultDate)
        }
        picker!!.setDisplayDays(displayDays)
        picker!!.setDisplayMonths(displayMonth)
        picker!!.setDisplayDaysOfMonth(displayDaysOfMonth)
    }

    fun setListener(listener: Listener?): SingleDateDialog {
        this.listener = listener
        return this
    }

    fun setCurved(curved: Boolean): SingleDateDialog {
        this.curved = curved
        return this
    }

    private fun setDisplayListener(displayListener: DisplayListener) {
        this.displayListener = displayListener
    }

    fun setTitle(title: String?): SingleDateDialog {
        this.title = title
        return this
    }

    fun setTitleTextSize(titleTextSize: Int?): SingleDateDialog {
        this.titleTextSize = titleTextSize
        return this
    }

    fun setBottomSheetHeight(bottomSheetHeight: Int?): SingleDateDialog {
        this.bottomSheetHeight = bottomSheetHeight
        return this
    }

    fun setTodayText(todayText: String?): SingleDateDialog {
        this.todayText = todayText
        return this
    }

    fun setMustBeOnFuture(mustBeOnFuture: Boolean): SingleDateDialog {
        this.mustBeOnFuture = mustBeOnFuture
        return this
    }

    fun setMinDateRange(minDate: Date?): SingleDateDialog {
        this.minDate = minDate
        return this
    }

    fun setMaxDateRange(maxDate: Date?): SingleDateDialog {
        this.maxDate = maxDate
        return this
    }

    fun setDefaultDate(defaultDate: Date?): SingleDateDialog {
        this.defaultDate = defaultDate
        return this
    }

    fun setDisplayDays(displayDays: Boolean): SingleDateDialog {
        this.displayDays = displayDays
        return this
    }

    fun setDisplayMonthNumbers(displayMonthNumbers: Boolean): SingleDateDialog {
        this.displayMonthNumbers = displayMonthNumbers
        return this
    }

    fun setDisplayDaysOfMonth(displayDaysOfMonth: Boolean): SingleDateDialog {
        this.displayDaysOfMonth = displayDaysOfMonth
        return this
    }

    private fun setDisplayMonth(displayMonth: Boolean): SingleDateDialog {
        this.displayMonth = displayMonth
        return this
    }

    private fun setDisplayYears(displayYears: Boolean): SingleDateDialog {
        this.displayYears = displayYears
        return this
    }

    fun setDayFormatter(dayFormatter: SimpleDateFormat?): SingleDateDialog {
        this.dayFormatter = dayFormatter
        return this
    }

    fun setFocusable(focusable: Boolean): SingleDateDialog {
        bottomSheetHelper.setFocusable(focusable)
        return this
    }

    fun setCustomLocale(locale: Locale?): SingleDateDialog {
        customLocale = locale
        return this
    }

    private fun setTimeZone(timeZone: TimeZone?): SingleDateDialog {
        dateHelper.setTimeZone(timeZone)
        return this
    }

    override fun display() {
        super.display()
        bottomSheetHelper.display()
    }

    override fun close() {
        super.close()
        bottomSheetHelper.hide()
        if (listener != null && okClicked) {
            listener!!.onDateSelected(picker!!.date)
        }
    }

    override fun dismiss() {
        super.dismiss()
        bottomSheetHelper.dismiss()
    }

    interface Listener {
        fun onDateSelected(date: Date?)
    }

    interface DisplayListener {
        fun onDisplayed(picker: SingleDatePicker?)
        fun onClosed(picker: SingleDatePicker?)
    }

    class Builder(private val context: Context) {
        private var dialog: SingleDateDialog? = null
        private var listener: Listener? = null
        private var displayListener: DisplayListener? = null
        private var title: String? = null
        private var titleTextSize: Int? = null
        private var bottomSheetHeight: Int? = null
        private var todayText: String? = null
        private var bottomSheet = false
        private var curved = false
        private var mustBeOnFuture = false
        private var displayDays = true
        private var displayMonth = false
        private var displayDaysOfMonth = false
        private var displayYears = false
        private var displayMonthNumbers = false
        private var focusable = false

        @ColorInt
        private var backgroundColor: Int? = null

        @ColorInt
        private var mainColor: Int? = null

        @ColorInt
        private var titleTextColor: Int? = null
        private var minDate: Date? = null
        private var maxDate: Date? = null
        private var defaultDate: Date? = null
        private var dayFormatter: SimpleDateFormat? = null
        private var customLocale: Locale? = null
        private var timeZone: TimeZone? = null
        fun title(title: String?): Builder {
            this.title = title
            return this
        }

        fun titleTextSize(titleTextSize: Int?): Builder {
            this.titleTextSize = titleTextSize
            return this
        }

        fun bottomSheetHeight(bottomSheetHeight: Int?): Builder {
            this.bottomSheetHeight = bottomSheetHeight
            return this
        }

        fun todayText(todayText: String?): Builder {
            this.todayText = todayText
            return this
        }

        fun bottomSheet(): Builder {
            bottomSheet = true
            return this
        }

        fun curved(): Builder {
            curved = true
            return this
        }

        fun mustBeOnFuture(): Builder {
            mustBeOnFuture = true
            return this
        }

        fun displayDays(displayDays: Boolean): Builder {
            this.displayDays = displayDays
            return this
        }

        fun displayDaysOfMonth(displayDaysOfMonth: Boolean): Builder {
            this.displayDaysOfMonth = displayDaysOfMonth
            return this
        }

        fun displayMonth(displayMonth: Boolean): Builder {
            this.displayMonth = displayMonth
            return this
        }

        fun displayYears(displayYears: Boolean): Builder {
            this.displayYears = displayYears
            return this
        }

        fun listener(listener: Listener?): Builder {
            this.listener = listener
            return this
        }

        fun displayListener(displayListener: DisplayListener?): Builder {
            this.displayListener = displayListener
            return this
        }

        fun titleTextColor(@ColorInt titleTextColor: Int): Builder {
            this.titleTextColor = titleTextColor
            return this
        }

        fun backgroundColor(@ColorInt backgroundColor: Int): Builder {
            this.backgroundColor = backgroundColor
            return this
        }

        fun mainColor(@ColorInt mainColor: Int): Builder {
            this.mainColor = mainColor
            return this
        }

        fun minDateRange(minDate: Date?): Builder {
            this.minDate = minDate
            return this
        }

        fun maxDateRange(maxDate: Date?): Builder {
            this.maxDate = maxDate
            return this
        }

        fun displayMonthNumbers(displayMonthNumbers: Boolean): Builder {
            this.displayMonthNumbers = displayMonthNumbers
            return this
        }

        fun defaultDate(defaultDate: Date?): Builder {
            this.defaultDate = defaultDate
            return this
        }

        fun setDayFormatter(dayFormatter: SimpleDateFormat?): Builder {
            this.dayFormatter = dayFormatter
            return this
        }

        fun customLocale(locale: Locale?): Builder {
            customLocale = locale
            return this
        }

        fun setTimeZone(timeZone: TimeZone?): Builder {
            this.timeZone = timeZone
            return this
        }

        fun focusable(): Builder {
            focusable = true
            return this
        }

        fun build(): SingleDateDialog {
            val dialog = SingleDateDialog(context, bottomSheet)
                .setTitle(title)
                .setTitleTextSize(titleTextSize)
                .setBottomSheetHeight(bottomSheetHeight)
                .setTodayText(todayText)
                .setListener(listener)
                .setCurved(curved)
                .setMaxDateRange(maxDate)
                .setMinDateRange(minDate)
                .setDefaultDate(defaultDate)
                .setDisplayMonth(displayMonth)
                .setDisplayYears(displayYears)
                .setDisplayDaysOfMonth(displayDaysOfMonth)
                .setDisplayMonthNumbers(displayMonthNumbers)
                .setDisplayDays(displayDays)
                .setDayFormatter(dayFormatter)
                .setCustomLocale(customLocale)
                .setMustBeOnFuture(mustBeOnFuture)
                .setTimeZone(timeZone)
                .setFocusable(focusable)
            if (mainColor != null) {
                dialog.setMainColor(mainColor)
            }
            if (backgroundColor != null) {
                dialog.setBackgroundColor(backgroundColor)
            }
            if (titleTextColor != null) {
                dialog.setTitleTextColor(titleTextColor!!)
            }
            if (displayListener != null) {
                dialog.setDisplayListener(displayListener!!)
            }
            return dialog
        }

        fun display() {
            dialog = build()
            dialog!!.display()
        }

        fun close() {
            if (dialog != null) {
                dialog!!.close()
            }
        }

        fun dismiss() {
            if (dialog != null) dialog!!.dismiss()
        }
    }

    init {
        val layout = R.layout.custom_date_picker
        bottomSheetHelper = BottomSheetHelper(context, layout)
        bottomSheetHelper.setListener(object : BottomSheetHelper.Listener {
            override fun onOpen() {}
            override fun onLoaded(view: View) {
                init(view)
                if (displayListener != null) {
                    displayListener!!.onDisplayed(picker)
                }
            }

            override fun onClose() {
                this@SingleDateDialog.onClose()
                if (displayListener != null) {
                    displayListener!!.onClosed(picker)
                }
            }
        })
    }
}