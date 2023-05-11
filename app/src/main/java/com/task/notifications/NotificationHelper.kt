import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import coil.load
import coil.size.Scale
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.imageview.ShapeableImageView
import com.task.R
import com.task.constants.Constants

/**
 * Author: tamdt35@fpt.com.vn
 * Date: 05/05/2023
 */
class NotificationHelper(val context: Context, val title: String, val message: String, val url: String?="") {
  private val CHANNEL_ID = "Message_test"
  private val NOTIFICATION_ID = 123

  @SuppressLint("RemoteViewLayout", "SuspiciousIndentation")
  fun CustomNotification() {
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val contentView = RemoteViews(context.packageName, R.layout.notification_normal)
    val contentViewBig = RemoteViews(context.packageName, R.layout.notification_expand)
    contentView.setTextViewText(R.id.text_title_normal_noti, title)
    contentView.setTextViewText(R.id.text_normal_noti, message)
    contentViewBig.setTextViewText(R.id.text_title_expand_noti, title)
    contentViewBig.setTextViewText(R.id.text_expand_noti, message)

    //load url
    val viewNomarl = contentView.apply(context,null)
    val imageUrlNormal = viewNomarl.findViewById<ImageView>(R.id.image_normal_noti)
    val viewExpand = contentViewBig.apply(context,null)
    val imageUrlExpand = viewExpand.findViewById<ImageView>(R.id.image_expand_noti)
    if(url != null){
      Glide.with(context).load(Constants.POSTER_BASE_URL + url).into(imageUrlNormal)
      Glide.with(context).load(Constants.POSTER_BASE_URL + url).into(imageUrlExpand)
    }
    else{
      imageUrlNormal.visibility = View.GONE
      imageUrlExpand.visibility = View.GONE
    }

    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
      .setSmallIcon(R.drawable.ic_notification)
      .setCustomBigContentView(contentViewBig)
      .setCustomContentView(contentView)
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
}