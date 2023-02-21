package com.task.libraries

import java.util.*

class DateHelper {
    // Don't use static, as timezone may change while app is alive
    private var timeZone = TimeZone.getDefault()
    fun setTimeZone(timeZoneValue: TimeZone) {
        timeZone = timeZoneValue
    }

    fun getTimeZone(): TimeZone {
        return if (timeZone == null) {
            TimeZone.getDefault()
        } else {
            timeZone
        }
    }

    fun getCalendarOfDate(date: Date?): Calendar {
        val calendar = Calendar.getInstance(getTimeZone())
        if (date != null) {
            calendar.time = date
        }
        calendar[Calendar.MILLISECOND] = 0
        calendar[Calendar.SECOND] = 0
        return calendar
    }

    fun today(): Date {
        val now = Calendar.getInstance(getTimeZone())
        return now.time
    }

    fun getMonth(date: Date?): Int {
        return getCalendarOfDate(date)[Calendar.MONTH]
    }

    fun getDay(date: Date?): Int {
        return getCalendarOfDate(date)[Calendar.DAY_OF_MONTH]
    }

    init {
        timeZone = TimeZone.getDefault()
    }
}