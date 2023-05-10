package com.task.notifications

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.task.MainActivity
import com.task.R

/**
 * Author: tamdt35@fpt.com.vn
 * Date: 05/05/2023
 */
class MyFirebaseMessagingService(var context: Context, var title: String, var message: String) {
  private val CHANNEL_ID = "Thông Báo"
  private val NOTIFICATION_ID = 123
  @SuppressLint("MissingPermission", "RemoteViewLayout")
  fun Notification() {
    createNotificationChannel()
    var pendingIntent :  PendingIntent? = null
    var senInt  : Intent? = null
    if (pendingIntent == null) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        senInt = Intent(context, MainActivity::class.java).apply {
          flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        pendingIntent = PendingIntent.getActivities(context, 0, arrayOf(senInt), PendingIntent.FLAG_IMMUTABLE)
      } else {
        senInt = Intent(context, MainActivity::class.java).apply {
          flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        pendingIntent = PendingIntent.getActivities(context, 0, arrayOf(senInt), 0)
      }
    }

    //------set notification dialog----
    val contentView = RemoteViews(context.packageName, R.layout.notification_message)
    contentView.apply {
      setTextViewText(R.id.text_title_noti,title)
      setTextViewText(R.id.text_message_noti,message)
    }
    val isNotification = NotificationCompat.Builder(context, CHANNEL_ID)
      .setSmallIcon(R.drawable.ic_star)
      .setContent(contentView)
      .setContentIntent(pendingIntent)
      .setPriority(NotificationCompat.PRIORITY_DEFAULT)
      .build()
    NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, isNotification)
    //---------------------------------
  }

  private fun createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val name = "Custom CustomNotification"
      val descrip = "Channel descrip"
      val imports = NotificationManager.IMPORTANCE_DEFAULT
      val cannels = NotificationChannel(CHANNEL_ID, name, imports).apply {
        description = descrip
      }
      val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
      notificationManager.createNotificationChannel(cannels)
    }
  }
}