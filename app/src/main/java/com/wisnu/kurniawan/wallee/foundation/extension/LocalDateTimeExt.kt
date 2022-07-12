package com.wisnu.kurniawan.wallee.foundation.extension

import androidx.appcompat.app.AppCompatDelegate
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.*

fun LocalDateTime.isSameDay(dateTime: LocalDateTime): Boolean {
    return toLocalDate().isEqual(dateTime.toLocalDate())
}

fun LocalDateTime.isTomorrow(dateTime: LocalDateTime): Boolean {
    return toLocalDate().isEqual(dateTime.toLocalDate().plusDays(1))
}

fun LocalDateTime.isYesterday(dateTime: LocalDateTime): Boolean {
    return toLocalDate().isEqual(dateTime.toLocalDate().minusDays(1))
}

fun LocalDateTime.isSameMinute(dateTime: LocalDateTime): Boolean {
    return truncatedTo(ChronoUnit.MINUTES).isEqual(dateTime.truncatedTo(ChronoUnit.MINUTES))
}

fun LocalDateTime.isSameHour(dateTime: LocalDateTime): Boolean {
    return truncatedTo(ChronoUnit.HOURS).isEqual(dateTime.truncatedTo(ChronoUnit.HOURS))
}

fun LocalDateTime.formatDateTime(): String {
    val pattern = "EEE, dd MMM yyyy"
    return format(pattern)
}

fun LocalDateTime.formatDate(): String {
    val pattern = "dd MMM yyyy"
    return format(pattern)
}

fun LocalDateTime.formatMonth(): String {
    val pattern = "MMM yyyy"
    return format(pattern)
}

fun LocalDateTime.format(pattern: String): String {
    val zoneId = ZoneId.systemDefault()
    val locale = AppCompatDelegate.getApplicationLocales().get(0) ?: Locale.getDefault()

    return SimpleDateFormat(pattern, locale).format(atZone(zoneId).toInstant().toEpochMilli())
}

fun LocalDateTime.toMillis(): Long {
    val zoneId = ZoneId.systemDefault()
    return atZone(zoneId).toInstant().toEpochMilli()
}

fun LocalDate.toMillis(zone: ZoneId = ZoneId.systemDefault()): Long {
    return atStartOfDay(zone).toInstant().toEpochMilli()
}

fun Long.toLocalDateTime(): LocalDateTime {
    val zoneId = ZoneId.systemDefault()
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(this), zoneId)
}

fun LocalDate.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.of(this, LocalTime.of(0, 0, 0, 0))
}

fun LocalDateTime.isFriday(): Boolean {
    return dayOfWeek == DayOfWeek.FRIDAY
}
