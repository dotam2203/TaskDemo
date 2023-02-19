package com.task;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.task.libraries.SingleDatePicker;
import com.task.SingleDateDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SingleDatePickerMainActivity extends AppCompatActivity {

    @BindView(R.id.singleText)
    TextView singleText;

    SimpleDateFormat simpleDateFormat;
    SingleDateDialog.Builder singleBuilder;

    private static String TAG = "SingleDatePickerMainActivityWithDoublePicker";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        this.simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (singleBuilder != null)
            singleBuilder.dismiss();
    }

    @OnClick(R.id.singleLayout)
    public void simpleClicked() {

        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 17); // 4. Feb. 2018
        calendar.set(Calendar.MONTH, 2);
        calendar.set(Calendar.YEAR, 2023);
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 13);

        final Date defaultDate = calendar.getTime();

        singleBuilder = new SingleDateDialog.Builder(this)
                .setTimeZone(TimeZone.getDefault())
                .bottomSheet()
                .curved()
                .displayDays(false)
                .displayMonth(true)
                .displayDaysOfMonth(true)
                .displayYears(true)
                .defaultDate(defaultDate)
                .displayMonthNumbers(true)

                .displayListener(new SingleDateDialog.DisplayListener() {
                    @Override
                    public void onDisplayed(SingleDatePicker picker) {
                        Log.d(TAG, "Dialog displayed3");
                    }

                    @Override
                    public void onClosed(SingleDatePicker picker) {
                        Log.d(TAG, "Dialog closed");
                    }
                })

                .title("Choose date of birth")
                .listener(new SingleDateDialog.Listener() {
                    @Override
                    public void onDateSelected(Date date) {
                        singleText.setText(simpleDateFormat.format(date));
                    }
                });
        singleBuilder.display();
    }
}