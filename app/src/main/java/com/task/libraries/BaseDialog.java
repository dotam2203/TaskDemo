package com.task.libraries;
import android.graphics.Color;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public abstract class BaseDialog {
    public static final int DEFAULT_ITEM_COUNT_MODE_CURVED = 7;
    public static final int DEFAULT_ITEM_COUNT_MODE_NORMAL = 5;

    private boolean isDisplaying;

    @Nullable
    @ColorInt
    protected Integer backgroundColor = Color.WHITE;

    @Nullable
    @ColorInt
    protected Integer mainColor = Color.BLACK;

    @Nullable
    @ColorInt
    protected Integer buttonColor = Color.WHITE;

    @Nullable
    @ColorInt
    protected Integer titleTextColor = null;

    protected boolean okClicked = false;
    protected boolean curved = false;
    protected boolean mustBeOnFuture = false;

    @Nullable
    protected Date minDate;
    @Nullable
    protected Date maxDate;
    @Nullable
    protected Date defaultDate;

    protected boolean displayDays;
    protected boolean displayDaysOfMonth;
    protected boolean displayMonth;
    protected boolean displayYears;
    protected boolean displayMonthNumbers;


    protected SimpleDateFormat dayFormatter;

    protected Locale customLocale;

    public void display() {
        this.isDisplaying = true;
    }

    public void close() {
        this.isDisplaying = false;
    }

    public void dismiss() {
        this.isDisplaying = false;
    }

    public boolean isDisplaying() {
        return isDisplaying;
    }

    public void setBackgroundColor(@ColorInt Integer backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setMainColor(@ColorInt Integer mainColor) {
        this.mainColor = mainColor;
    }

    public void setTitleTextColor(@NonNull @ColorInt int titleTextColor) {
        this.titleTextColor = titleTextColor;
    }

    protected void onClose() {
        this.isDisplaying = false;
    }
}
