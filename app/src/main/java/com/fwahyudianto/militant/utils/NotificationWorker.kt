package com.fwahyudianto.militant.utils

//  Import Library
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.fwahyudianto.militant.R
import com.fwahyudianto.militant.data.services.ApiConfig

class NotificationWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    //  private val time = context.getString(R.string.event_start)

    override fun doWork(): Result {
        Log.d("Militan-NotificationWorker", "Starting Worker ...")
        try {
            val response = ApiConfig.getApiService().getListByParam(1, null, 1).execute()
            if (response.isSuccessful) {
                val event = response.body()?.listEvents?.firstOrNull()
                event?.let {
                    val remaining = (it.quota ?: 0) - (it.registrants ?: 0)
                    val formattedTime = HelperDateTime.formatDateTime(it.beginTime!!)
                    val title = it.name!!
                    val message = "$formattedTime. Remaining Quota : $remaining"

                    showNotification(title, message)
                }
                return Result.success()
            } else {
                return Result.retry()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.retry()
        }
    }

    private fun showNotification(title: String, message: String) {
        val channelId = "event_channel"
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Event Notification",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)

        notificationManager.notify(1, builder.build())
        Log.d("Militan-NotificationWorker", "Worker finished ...")
    }
}