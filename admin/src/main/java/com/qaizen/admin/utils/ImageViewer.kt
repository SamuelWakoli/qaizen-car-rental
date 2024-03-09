package com.qaizen.admin.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * Opens an image using the given context and URI.
 *
 * @param context The context to use for opening the image.
 * @param uri The URI of the image to open.
 * @param onError A callback to handle any exceptions that occur while opening the image.
 */
fun openImage(context: Context, uri: Uri, onError: (Exception) -> Unit = {}) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(uri, "image/*")
    }

    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        onError(e)
    }
}
