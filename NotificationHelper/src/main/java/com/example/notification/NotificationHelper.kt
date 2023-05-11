package com.example.notification

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.view.View
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import java.io.IOException
import java.net.URL
import java.util.Random
import javax.net.ssl.HttpsURLConnection

/**
 * Author: tamdt35@fpt.com.vn
 * Date: 05/05/2023
 */
class NotificationHelper(val context: Context, val title: String, val message: String, val url: String? = "") {
  private val CHANNEL_ID = "Message_test"
  private val NOTIFICATION_ID = Random().nextInt(900)+100

  @SuppressLint("RemoteViewLayout", "SuspiciousIndentation")
  fun CustomNotification() {
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val contentView = RemoteViews(context.packageName, R.layout.notification_normal)
    val contentViewBig = RemoteViews(context.packageName, R.layout.notification_expand)
    contentView.setTextViewText(R.id.text_title_normal_noti, title)
    contentView.setTextViewText(R.id.text_normal_noti, message)
    contentViewBig.setTextViewText(R.id.text_title_expand_noti, title)
    contentViewBig.setTextViewText(R.id.text_expand_noti, message)
    if(url!= null){
      val bitmap : Bitmap? = getBitmapFromURL(url)
      contentView.setImageViewBitmap(R.id.image_normal_noti,bitmap)
      contentViewBig.setImageViewBitmap(R.id.image_normal_noti,bitmap)
    }
    else{
      contentView.setViewVisibility(R.id.image_normal_noti,View.GONE)
      contentViewBig.setViewVisibility(R.id.image_expand_noti,View.GONE)
    }

    //load url
    /*val viewNomarl = contentView.apply(context, null)
    val imageUrlNormal = viewNomarl.findViewById<ImageView>(R.id.image_normal_noti)
    val viewExpand = contentViewBig.apply(context, null)
    val imageUrlExpand = viewExpand.findViewById<ImageView>(R.id.image_expand_noti)
    if (url != null) {
      Glide.with(context).load(Constants.POSTER_BASE_URL + url).into(imageUrlNormal)
      Glide.with(context).load(Constants.POSTER_BASE_URL + url).into(imageUrlExpand)
    } else {
      imageUrlNormal.visibility = View.GONE
      imageUrlExpand.visibility = View.GONE
    }*/

    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
      .setSmallIcon(R.drawable.ic_notification)
      .setCustomContentView(contentView)
      .setCustomBigContentView(contentViewBig)
      .setStyle(NotificationCompat.DecoratedCustomViewStyle())
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val channel = NotificationChannel(CHANNEL_ID, "Custom CustomNotification", NotificationManager.IMPORTANCE_DEFAULT)
      channel.enableVibration(true)
      notificationManager.createNotificationChannel(channel)
      builder.setChannelId(CHANNEL_ID)
    }
    val notification = builder.build()
    notificationManager.notify(NOTIFICATION_ID, notification)
  }
  fun getBitmapFromURL(src: String): Bitmap?{
    try {
      val connection = URL(src).openConnection() as HttpsURLConnection
      connection.apply {
        doInput = true
        connect()
        val input = connection.inputStream
        return BitmapFactory.decodeStream(input)
      }
    } catch (e: IOException){}
    return null
  }
}