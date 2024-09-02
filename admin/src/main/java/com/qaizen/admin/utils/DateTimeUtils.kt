package com.qaizen.admin.utils

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object DateTimeUtils {
    fun convertToLocalDateTime(
        dateTimeString: String,
        outputPattern: String = "yyyy-MM-dd HH:mm a",
    ): String {
        return try {
            // Parse the string into a LocalDateTime object
            val localDateTime = LocalDateTime.parse(dateTimeString)

            // Convert to local time zone
            val localDateTimeInZone = localDateTime
                .atZone(ZoneId.of("UTC"))
                .withZoneSameInstant(ZoneId.systemDefault())
                .toLocalDateTime()

            // Format the LocalDateTime object into the desired output format
            val outputFormatter = DateTimeFormatter.ofPattern(outputPattern)
            localDateTimeInZone.format(outputFormatter)
        } catch (e: DateTimeParseException) {
            // Handle parsing errors (invalid input format)
            "Invalid date-time format: ${e.message}"
        } catch (e: Exception) {
            // Handle other unexpected errors
            "Error: ${e.message}"
        }
    }
}