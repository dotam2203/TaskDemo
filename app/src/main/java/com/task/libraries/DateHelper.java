package com.task.libraries;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateHelper {
    // Don't use static, as timezone may change while app is alive
    private TimeZone timeZone = TimeZone.getDefault();

    public DateHelper() {
        this.timeZone = TimeZone.getDefault();
    }

    public DateHelper(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public void setTimeZone(TimeZone timeZoneValue) {
        timeZone = timeZoneValue;
    }

    @NonNull
    public TimeZone getTimeZone() {
        if(this.timeZone == null) {
            return TimeZone.getDefault();
        } else {
            return timeZone;
        }
    }
    public Calendar getCalendarOfDate(Date date) {
        final Calendar calendar = Calendar.getInstance(getTimeZone());
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }
    public Date today() {
        Calendar now = Calendar.getInstance(getTimeZone());
        return now.getTime();
    }
    public int getMonth(Date date) {
        return getCalendarOfDate(date).get(Calendar.MONTH);
    }
    public int getDay(Date date) {
        return getCalendarOfDate(date).get(Calendar.DAY_OF_MONTH);
    }
}
