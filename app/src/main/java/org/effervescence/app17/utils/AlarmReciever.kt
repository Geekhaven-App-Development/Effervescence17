package org.effervescence.app17.utils

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import org.effervescence.app17.R
import org.effervescence.app17.activities.MainActivity

/**
 * Created by sashank on 3/10/17.
 */

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val message = intent.getStringExtra("message")
        val title = intent.getStringExtra("title")

        val notIntent = Intent(context, MainActivity().javaClass)
        val contentIntent = PendingIntent.getActivity(context, 0,
                notIntent, PendingIntent.FLAG_CANCEL_CURRENT)
        val manager = NotificationManagerCompat.from(context)

        val style = NotificationCompat.BigTextStyle()
        style.bigText(message)

        val builder = NotificationCompat.Builder(context, "group_01")
                .setContentIntent(contentIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(style)
                .setWhen(java.lang.System.currentTimeMillis())
                .setAutoCancel(true)

        val notification = builder.build()
        manager.notify(0, notification)
    }
}