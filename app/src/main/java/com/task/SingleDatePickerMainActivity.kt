package com.task

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.task.databinding.ActivityMainBinding
import com.task.libraries.SingleDatePicker
import java.text.SimpleDateFormat
import java.util.*

class SingleDatePickerMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var simpleDateFormat: SimpleDateFormat? = null
    var singleBuilder: SingleDateDialog.Builder? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        simpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        binding.singleLayout.setOnClickListener {
            getOnclickListener()
        }
    }

    private fun getOnclickListener() {
        Log.e(TAG, "getOnclickListener: onclicked!", )
        val calendar = Calendar.getInstance()
        calendar[Calendar.DAY_OF_MONTH] = 17 // 17. Feb. 2023
        calendar[Calendar.MONTH] = 2
        calendar[Calendar.YEAR] = 2023
        calendar[Calendar.HOUR_OF_DAY] = 11
        calendar[Calendar.MINUTE] = 13
        val defaultDate = calendar.time
        singleBuilder = SingleDateDialog.Builder(this)
            .setTimeZone(TimeZone.getDefault())
            .bottomSheet()
            .curved()
            .displayDays(false)
            .displayMonth(true)
            .displayDaysOfMonth(true)
            .displayYears(true)
            .defaultDate(defaultDate)
            .displayMonthNumbers(true)
            .displayListener(object : SingleDateDialog.DisplayListener {
                override fun onDisplayed(picker: SingleDatePicker?) {
                    Log.d(TAG, "Dialog displayed")
                }

                override fun onClosed(picker: SingleDatePicker?) {
                    Log.d(TAG, "Dialog closed")
                }
            })
            .title("Choose date of birth")
            .listener(object : SingleDateDialog.Listener {
                override fun onDateSelected(date: Date?) {
                    binding.singleText.text = simpleDateFormat!!.format(date)
                }
            })
        singleBuilder!!.display()
    }

    /*override fun onPause() {
        super.onPause()
        if (singleBuilder != null) singleBuilder!!.dismiss()
    }*/
    companion object {
        private const val TAG = "SingleDatePickerMainActivityCustom"
    }
}