package com.wisnu.kurniawan.wallee.foundation.uiextension

import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.wisnu.foundation.coredatetime.toMillis
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import kotlinx.parcelize.Parcelize

private const val DATE_PICKER_TAG = "date_picker"
private const val TIME_PICKER_TAG = "time_picker"

fun AppCompatActivity.showDatePicker(
    selection: LocalDate? = null,
    selectedDate: (LocalDate) -> Unit
) {
    val zone = ZoneId.ofOffset("UTC", ZoneOffset.UTC)
    val start = LocalDate.of(2000, 1, 1).toMillis(zone)
    val end = LocalDate.now().toMillis(zone)
    val calendarConstraints = CalendarConstraints.Builder()
        .setValidator(
            DateValidatorBounds(
                start = start,
                end = end,
            )
        )
        .setStart(start)
        .setEnd(end)
        .build()
    val picker = MaterialDatePicker
        .Builder
        .datePicker()
        .apply {
            if (selection != null) {
                setSelection(
                    selection.toMillis(zone)
                )
            }
        }
        .setCalendarConstraints(calendarConstraints)
        .build()
    picker.show(supportFragmentManager, DATE_PICKER_TAG)
    picker.addOnPositiveButtonClickListener {
        selectedDate(
            Instant
                .ofEpochMilli(it)
                .atZone(zone)
                .toLocalDate()
        )
    }
}

fun AppCompatActivity.showTimePicker(
    time: LocalTime? = null,
    selectedTime: (LocalTime) -> Unit
) {
    val picker = MaterialTimePicker
        .Builder()
        .setTimeFormat(TimeFormat.CLOCK_12H)
        .apply {
            if (time != null) {
                setHour(time.hour)
                setMinute(time.minute)
            } else {
                setHour(9)
            }
        }
        .build()

    picker.show(supportFragmentManager, TIME_PICKER_TAG)
    picker.addOnPositiveButtonClickListener {
        selectedTime(LocalTime.of(picker.hour, picker.minute))
    }
}

@Parcelize
private class DateValidatorBounds(
    private val start: Long?,
    private val end: Long?,
) : CalendarConstraints.DateValidator, Parcelable {
    override fun isValid(date: Long): Boolean = when {
        start != null && end != null -> date in start..end
        start != null -> date >= start
        end != null -> date <= end
        else -> true
    }
}
