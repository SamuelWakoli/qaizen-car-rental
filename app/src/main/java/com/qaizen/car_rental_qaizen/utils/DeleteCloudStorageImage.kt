package com.qaizen.car_rental_qaizen.utils

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

/**
 * Deletes an image from the given URI.
 *
 * @param url The URL of the image to delete.
 * @param onSuccess A callback function that will be called if the image is deleted successfully.
 * @param onFailure A callback function that will be called if the image deletion fails.
 */
fun deleteImageFromUri(
    url: String,
    onSuccess: () -> Unit = {},
    onFailure: (Exception) -> Unit = {}
) {
    val storage = Firebase.storage
    val imageRef = storage.getReferenceFromUrl(url)

    imageRef.delete().addOnSuccessListener {
        onSuccess()
    }.addOnFailureListener {
        onFailure(it)
    }
}