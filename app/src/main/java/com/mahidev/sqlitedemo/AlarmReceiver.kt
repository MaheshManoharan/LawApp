package com.mahidev.sqlitedemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Show the notification here
        showNotification(context)
    }

    private fun showNotification(context: Context) {
        // Create a notification and show it using NotificationManager
        val builder = NotificationCompat.Builder(context, "CHANNEL_ID")
            .setSmallIcon(com.google.android.material.R.drawable.notification_bg)
            .setContentTitle("Notification Title")
            .setContentText("Notification Text")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(1, builder.build())
    }
}
