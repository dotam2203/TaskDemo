package com.task.libraries

import android.graphics.Color
import androidx.annotation.ColorInt
import java.text.SimpleDateFormat
import java.util.*

abstract class BaseDialog {
    private var isDisplaying = false

    @ColorInt
    protected var backgroundColor: Int? = Color.WHITE

    @ColorInt
    protected var mainColor: Int? = Color.BLACK

    @ColorInt
    protected var buttonColor: Int? = Color.WHITE

    @ColorInt
    protected var titleTextColor: Int? = null
    protected var okClicked = false
    protected var curved = false
    protected var mustBeOnFuture = false
    protected var minDate: Date? = null
    protected var maxDate: Date? = null
    protected var defaultDate: Date? = null
    protected var displayDays = false
    protected var displayDaysOfMonth = false
    protected var displayMonth = false
    protected var displayYears = false
    protected var displayMonthNumbers = false
    protected var dayFormatter: SimpleDateFormat? = null
    protected var customLocale: Locale? = null
    open fun display() {
        isDisplaying = true
    }

    open fun close() {
        isDisplaying = false
    }

    open fun dismiss() {
        isDisplaying = false
    }

    @JvmName("setBackgroundColor1")
    fun setBackgroundColor(@ColorInt backgroundColor: Int?) {
        this.backgroundColor = backgroundColor
    }

    @JvmName("setMainColor1")
    fun setMainColor(@ColorInt mainColor: Int?) {
        this.mainColor = mainColor
    }

    fun setTitleTextColor(@ColorInt titleTextColor: Int) {
        this.titleTextColor = titleTextColor
    }

    protected fun onClose() {
        isDisplaying = false
    }
}