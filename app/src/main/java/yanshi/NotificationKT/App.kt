package yanshi.NotificationKT

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import yanshi.NotificationKT.App.statified.CHANNEL_1_ID
import yanshi.NotificationKT.App.statified.CHANNEL_2_ID
import yanshi.NotificationKT.App.statified.CHANNEL_3_ID
import yanshi.NotificationKT.App.statified.CHANNEL_4_ID
import yanshi.NotificationKT.App.statified.CHANNEL_5_ID

class App : Application() {

    object statified{
        const val CHANNEL_1_ID: String = "channel1"
        const val CHANNEL_2_ID: String = "channel2"
        const val CHANNEL_3_ID: String = "channel3"
        const val CHANNEL_4_ID: String = "channel4"
        const val CHANNEL_5_ID: String = "channel5"
    }

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()

    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel1 = NotificationChannel(
                CHANNEL_1_ID,
                "Channel 1",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel1.description = "This is channel 1"

            val channel2 = NotificationChannel(
                CHANNEL_2_ID,
                "Channel 2",
                NotificationManager.IMPORTANCE_LOW
            )
            channel2.description = "This is channel 2"

            val channel3 = NotificationChannel(
                CHANNEL_3_ID,
                "Channel 3",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel3.description = "This is channel 3"

            val channel4 = NotificationChannel(
                CHANNEL_4_ID,
                "Channel 4",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel4.description = "This is channel 4"

            val channel5 = NotificationChannel(
                CHANNEL_5_ID,
                "Channel 5",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel5.description = "This is channel 5"


            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel1)
            manager.createNotificationChannel(channel2)
            manager.createNotificationChannel(channel3)
            manager.createNotificationChannel(channel4)
            manager.createNotificationChannel(channel5)
        }
    }

}