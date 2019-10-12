package mx.unam.tic.docencia.notificationunam.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import mx.unam.tic.docencia.notificationunam.MainActivity
import mx.unam.tic.docencia.notificationunam.R

class NotificationFirebaseService : FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        //sendTokenBackend(p0)
    }

    override fun onMessageReceived(p0: RemoteMessage) { //Se ejecuta cuando se recibe una noificación
        super.onMessageReceived(p0)
    }

    private fun sendNotification(remoteMessage: RemoteMessage){
        val intent= Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent=PendingIntent.getActivity(this, 0,
            intent, PendingIntent.FLAG_ONE_SHOT)
        val defaultTone=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val notificationChannel=
                NotificationChannel(
                    remoteMessage.notification?.channelId.toString(),
                    "Test Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            notificationChannel.description="Description here"
            notificationChannel.enableLights(true)
            notificationChannel.lightColor= Color.BLUE
            notificationChannel.vibrationPattern= longArrayOf(100,200,500,1000)
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val notificationBuilder=NotificationCompat.Builder(
            this,
            remoteMessage.notification?.channelId.toString()
        ) // Construye las notificaciones en la barra de notificaciones
        notificationBuilder.setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.mipmap.ic_launcher)
            .setTicker(remoteMessage.notification?.ticker)
            .setContentTitle(remoteMessage.notification?.title)
            .setContentText(remoteMessage.notification?.body)
            .setSound(defaultTone)
            .setContentIntent(pendingIntent)
        notificationManager.notify(
            System.currentTimeMillis().toInt(),
            notificationBuilder.build()
        )
    }
}