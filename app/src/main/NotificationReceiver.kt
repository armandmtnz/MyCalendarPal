package com.example.mcpclient

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

/**
 * BroadcastReceiver that creates and displays a local notification when
 * triggered by the AlarmManager.  It reads the message from the intent
 * extras and uses a fixed notification channel.
 */
class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val message = intent.getStringExtra(EXTRA_MESSAGE) ?: return
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create notification channel on Android 8+
        val channelId = NOTIFICATION_CHANNEL_ID
        val channel = NotificationChannel(
            channelId,
            "MCP Reminders",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Reminder")
            .setContentText(message)
            .setAutoCancel(true)
            .build()

        // Use unique ID for each notification; in real code you may persist IDs.
        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }

    companion object {
        const val EXTRA_MESSAGE = "com.example.mcpclient.EXTRA_MESSAGE"
        private const val NOTIFICATION_CHANNEL_ID = "mcp_reminders"
    }
}