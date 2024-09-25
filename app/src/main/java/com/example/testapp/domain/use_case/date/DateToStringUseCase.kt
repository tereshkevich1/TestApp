package com.example.testapp.domain.use_case.date

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class DateToStringUseCase @Inject constructor() {
    operator fun invoke(
        milliSeconds: Long,
        format: String,
        locale: Locale = Locale.getDefault()
    ): String {
        val formatter = SimpleDateFormat(format, locale)
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds*1000
        return formatter.format(calendar.time)
    }
}