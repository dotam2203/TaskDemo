package com.task;

import static com.task.libraries.SingleDateConstants.STEP_MINUTES_DEFAULT;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.task.libraries.BaseDialog;
import com.task.libraries.BottomSheetHelper;
import com.task.libraries.DateHelper;
import com.task.libraries.SingleDatePicker;
import com.task.libraries.DateWithLabel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class SingleDateDialog extends BaseDialog {

    private final DateHelper dateHelper = new DateHelper();
    private Listener listener;
    private BottomSheetHelper bottomSheetHelper;
    private SingleDatePicker picker;

    @Nullable
    private String title;
    @Nullable
    private Integer titleTextSize;
    @Nullable
    private Integer bottomSheetHeight;
    @Nullable
    private String todayText;
    @Nullable
    private DisplayListener displayListener;

    private SingleDateDialog(Context context) {
        this(context, false);
    }

    private SingleDateDialog(Context context, boolean bottomSheet) {
        final int layout = bottomSheet ? R.layout.custom_date_picker : R.layout.custom_date_picker;
        this.bottomSheetHelper = new BottomSheetHelper(context, layout);

        this.bottomSheetHelper.setListener(new BottomSheetHelper.Listener() {
            @Override
            public void onOpen() {
            }

            @Override
            public void onLoaded(View view) {
                init(view);
                if (displayListener != null) {
                    displayListener.onDisplayed(picker);
                }
            }

            @Override
            public void onClose() {
                SingleDateDialog.this.onClose();

                if (displayListener != null) {
                    displayListener.onClosed(picker);
                }
            }
        });
    }


    private void init(View view) {
        picker = (SingleDatePicker) view.findViewById(R.id.picker);
        picker.setDateHelper(dateHelper);
        if (picker != null) {
            if (bottomSheetHeight != null) {
                ViewGroup.LayoutParams params = picker.getLayoutParams();
                params.height = bottomSheetHeight;
                picker.setLayoutParams(params);
            }
        }

        final TextView buttonOk = (TextView) view.findViewById(R.id.tvApply);
        if (buttonOk != null) {
            buttonOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    okClicked = true;
                    close();
                }
            });

            if (mainColor != null) {
                buttonOk.setTextColor(buttonColor);
            }

            if (titleTextSize != null) {
                buttonOk.setTextSize(titleTextSize);
            }
        }

        final View sheetContentLayout = view.findViewById(R.id.sheetContentLayout);
        if (sheetContentLayout != null) {
            sheetContentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            if (backgroundColor != null) {
                sheetContentLayout.setBackgroundColor(backgroundColor);
            }
        }

        final TextView titleTextView = (TextView) view.findViewById(R.id.sheetTitle);
        if (titleTextView != null) {
            titleTextView.setText("Choose date of birth");

            if (titleTextColor != null) {
                titleTextView.setTextColor(titleTextColor);
            }

            if (titleTextSize != null) {
                titleTextView.setTextSize(titleTextSize);
            }
        }

        picker.setTodayText(new DateWithLabel(todayText, new Date()));

        if (curved) {
            picker.setCurved(true);
            picker.setVisibleItemCount(7);
        } else {
            picker.setCurved(false);
            picker.setVisibleItemCount(5);
        }
        picker.setMustBeOnFuture(mustBeOnFuture);

        if (dayFormatter != null) {
            picker.setDayFormatter(dayFormatter);
        }

        if (customLocale != null) {
            picker.setCustomLocale(customLocale);
        }

        if (mainColor != null) {
            picker.setSelectedTextColor(mainColor);
        }

        // displayYears used in setMinDate / setMaxDate
        picker.setDisplayYears(displayYears);

        if (minDate != null) {
            picker.setMinDate(minDate);
        }

        if (maxDate != null) {
            picker.setMaxDate(maxDate);
        }

        if (defaultDate != null) {
            picker.setDefaultDate(defaultDate);
        }
        picker.setDisplayDays(displayDays);
        picker.setDisplayMonths(displayMonth);
        picker.setDisplayDaysOfMonth(displayDaysOfMonth);
    }

    public SingleDateDialog setListener(Listener listener) {
        this.listener = listener;
        return this;
    }

    public SingleDateDialog setCurved(boolean curved) {
        this.curved = curved;
        return this;
    }

    private void setDisplayListener(DisplayListener displayListener) {
        this.displayListener = displayListener;
    }

    public SingleDateDialog setTitle(@Nullable String title) {
        this.title = title;
        return this;
    }

    public SingleDateDialog setTitleTextSize(@Nullable Integer titleTextSize) {
        this.titleTextSize = titleTextSize;
        return this;
    }

    public SingleDateDialog setBottomSheetHeight(@Nullable Integer bottomSheetHeight) {
        this.bottomSheetHeight = bottomSheetHeight;
        return this;
    }

    public SingleDateDialog setTodayText(@Nullable String todayText) {
        this.todayText = todayText;
        return this;
    }

    public SingleDateDialog setMustBeOnFuture(boolean mustBeOnFuture) {
        this.mustBeOnFuture = mustBeOnFuture;
        return this;
    }

    public SingleDateDialog setMinDateRange(Date minDate) {
        this.minDate = minDate;
        return this;
    }

    public SingleDateDialog setMaxDateRange(Date maxDate) {
        this.maxDate = maxDate;
        return this;
    }

    public SingleDateDialog setDefaultDate(Date defaultDate) {
        this.defaultDate = defaultDate;
        return this;
    }

    public SingleDateDialog setDisplayDays(boolean displayDays) {
        this.displayDays = displayDays;
        return this;
    }

    public SingleDateDialog setDisplayMonthNumbers(boolean displayMonthNumbers) {
        this.displayMonthNumbers = displayMonthNumbers;
        return this;
    }

    public SingleDateDialog setDisplayDaysOfMonth(boolean displayDaysOfMonth) {
        this.displayDaysOfMonth = displayDaysOfMonth;
        return this;
    }


    private SingleDateDialog setDisplayMonth(boolean displayMonth) {
        this.displayMonth = displayMonth;
        return this;
    }

    private SingleDateDialog setDisplayYears(boolean displayYears) {
        this.displayYears = displayYears;
        return this;
    }

    public SingleDateDialog setDayFormatter(SimpleDateFormat dayFormatter) {
        this.dayFormatter = dayFormatter;
        return this;
    }

    public SingleDateDialog setCustomLocale(Locale locale) {
        this.customLocale = locale;
        return this;
    }
    private SingleDateDialog setTimeZone(TimeZone timeZone) {
        dateHelper.setTimeZone(timeZone);
        return this;
    }

    @Override
    public void display() {
        super.display();
    }

    @Override
    public void close() {
        super.close();

        if (listener != null && okClicked) {
            listener.onDateSelected(picker.getDate());
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    public interface Listener {
        void onDateSelected(Date date);
    }
    public interface DisplayListener {
        void onDisplayed(SingleDatePicker picker);
        void onClosed(SingleDatePicker picker);
    }

    public static class Builder {
        private final Context context;
        private SingleDateDialog dialog;

        @Nullable
        private Listener listener;
        @Nullable
        private DisplayListener displayListener;

        @Nullable
        private String title;

        @Nullable
        private Integer titleTextSize;

        @Nullable
        private Integer bottomSheetHeight;

        @Nullable
        private String todayText;

        private boolean bottomSheet;

        private boolean curved;
        private boolean mustBeOnFuture;

        private boolean displayDays = true;
        private boolean displayMonth = false;
        private boolean displayDaysOfMonth = false;
        private boolean displayYears = false;
        private boolean displayMonthNumbers = false;
        private boolean focusable = false;

        @ColorInt
        @Nullable
        private Integer backgroundColor = null;

        @ColorInt
        @Nullable
        private Integer mainColor = null;

        @ColorInt
        @Nullable
        private Integer titleTextColor = null;

        @Nullable
        private Date minDate;
        @Nullable
        private Date maxDate;
        @Nullable
        private Date defaultDate;

        @Nullable
        private SimpleDateFormat dayFormatter;

        @Nullable
        private Locale customLocale;
        private TimeZone timeZone;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder title(@Nullable String title) {
            this.title = title;
            return this;
        }

        public Builder titleTextSize(@Nullable Integer titleTextSize) {
            this.titleTextSize = titleTextSize;
            return this;
        }

        public Builder bottomSheetHeight(@Nullable Integer bottomSheetHeight) {
            this.bottomSheetHeight = bottomSheetHeight;
            return this;
        }

        public Builder todayText(@Nullable String todayText) {
            this.todayText = todayText;
            return this;
        }

        public Builder bottomSheet() {
            this.bottomSheet = true;
            return this;
        }

        public Builder curved() {
            this.curved = true;
            return this;
        }

        public Builder mustBeOnFuture() {
            this.mustBeOnFuture = true;
            return this;
        }

        public Builder displayDays(boolean displayDays) {
            this.displayDays = displayDays;
            return this;
        }

        public Builder displayDaysOfMonth(boolean displayDaysOfMonth) {
            this.displayDaysOfMonth = displayDaysOfMonth;
            return this;
        }

        public Builder displayMonth(boolean displayMonth) {
            this.displayMonth = displayMonth;
            return this;
        }

        public Builder displayYears(boolean displayYears) {
            this.displayYears = displayYears;
            return this;
        }

        public Builder listener(@Nullable Listener listener) {
            this.listener = listener;
            return this;
        }

        public Builder displayListener(@Nullable DisplayListener displayListener) {
            this.displayListener = displayListener;
            return this;
        }

        public Builder titleTextColor(@NonNull @ColorInt int titleTextColor) {
            this.titleTextColor = titleTextColor;
            return this;
        }

        public Builder backgroundColor(@NonNull @ColorInt int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public Builder mainColor(@NonNull @ColorInt int mainColor) {
            this.mainColor = mainColor;
            return this;
        }

        public Builder minDateRange(Date minDate) {
            this.minDate = minDate;
            return this;
        }

        public Builder maxDateRange(Date maxDate) {
            this.maxDate = maxDate;
            return this;
        }

        public Builder displayMonthNumbers(boolean displayMonthNumbers) {
            this.displayMonthNumbers = displayMonthNumbers;
            return this;
        }

        public Builder defaultDate(Date defaultDate) {
            this.defaultDate = defaultDate;
            return this;
        }

        public Builder setDayFormatter(SimpleDateFormat dayFormatter) {
            this.dayFormatter = dayFormatter;
            return this;
        }

        public Builder customLocale(Locale locale) {
            this.customLocale = locale;
            return this;
        }

        public Builder setTimeZone(TimeZone timeZone) {
            this.timeZone = timeZone;
            return this;
        }

        public Builder focusable() {
            this.focusable = true;
            return this;
        }

        public SingleDateDialog build() {
            final SingleDateDialog dialog = new SingleDateDialog(context, bottomSheet)
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
                    .setTimeZone(timeZone);

            if (mainColor != null) {
                dialog.setMainColor(mainColor);
            }

            if (backgroundColor != null) {
                dialog.setBackgroundColor(backgroundColor);
            }

            if (titleTextColor != null) {
                dialog.setTitleTextColor(titleTextColor);
            }

            if (displayListener != null) {
                dialog.setDisplayListener(displayListener);
            }

            return dialog;
        }

        public void display() {
            dialog = build();
            dialog.display();
        }

        public void close() {
            if (dialog != null) {
                dialog.close();
            }
        }

        public void dismiss() {
            if (dialog != null)
                dialog.dismiss();
        }
    }

}
