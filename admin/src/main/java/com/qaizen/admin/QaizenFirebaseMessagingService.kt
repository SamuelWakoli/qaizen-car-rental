package com.qaizen.admin

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class QaizenFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        message.notification?.let {
            val title = it.title
            val body = it.body

            // TODO: Show notification

            Log.d("QaizenFirebaseMessagingService", "Message Notification Title: $title")
            Log.d("QaizenFirebaseMessagingService", "Message Notification Body: $body")
        }
    }
}