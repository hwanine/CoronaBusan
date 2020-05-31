package com.jhlee.coronabusan

    import android.app.NotificationChannel
    import android.app.NotificationManager
    import android.app.PendingIntent
    import android.content.Context
    import android.content.Intent
    import android.media.RingtoneManager
    import android.os.Build
    import android.util.Log
    import androidx.core.app.NotificationCompat
    import com.google.firebase.messaging.FirebaseMessagingService
    import com.google.firebase.messaging.RemoteMessage


class FirebaseMessagingService : FirebaseMessagingService() {


        // [START receive_message]
        override fun onMessageReceived(remoteMessage: RemoteMessage) {
            sendNotification(
                remoteMessage.notification?.title.toString(),
                remoteMessage.notification?.body.toString()
            )
        }

        override fun onNewToken(token: String) {
            Log.d(TAG, "Refreshed token: $token")
        }

        private fun sendNotification(messageTitle: String, messageBody: String) {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT)

            val channelId = getString(R.string.firebase_id)
            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val notificationBuilder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            // Since android Oreo notification channel is needed.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(channelId,
                    "코로나19 부산",
                    NotificationManager.IMPORTANCE_DEFAULT)
                notificationManager.createNotificationChannel(channel)
            }

            notificationManager.notify(R.string.firebase_id, notificationBuilder.build())
        }

        companion object {
            private const val TAG = "MyFirebaseMsgService"
        }
    }