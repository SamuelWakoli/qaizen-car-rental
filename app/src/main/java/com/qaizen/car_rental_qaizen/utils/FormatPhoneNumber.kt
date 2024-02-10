package com.qaizen.car_rental_qaizen.utils

import android.content.Context

/**
 * Formats a phone number to the standard format for the given context.
 *
 * @param phone The phone number to format.
 * @param context The context to use to get the country code.
 * @return The formatted phone number.
 */
fun formatPhoneNumber(phone: String, context: Context): String {
    val code = context.resources.configuration.locale.country

    // Get the country code from the phone number.
    val countryCode = phone.substring(0, 2)

    // If the country code is "+254", remove the "+" from the phone number.
    if (countryCode == "+$code") {
        return phone.substring(1)
    }

    // If the country code is "07" or "01", convert it to "2547" or "2541".
    if (countryCode == "07" || countryCode == "01") {
        return "$code${phone.substring(1)}"
    }

    // Return the phone number as it is.
    return phone
}