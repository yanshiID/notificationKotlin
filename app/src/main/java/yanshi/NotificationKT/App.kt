package yanshi.NotificationKT

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import yanshi.NotificationKT.App.statified.CHANNEL_1_ID
import yanshi.NotificationKT.App.statified.CHANNEL_2_ID
import yanshi.NotificationKT.App.statified.CHANNEL_3_ID
import yanshi.NotificationKT.App.statified.CHANNEL_4_ID
import yanshi.NotificationKT.App.statified.CHANNEL_5_ID
import yanshi.NotificationKT.App.statified.CHANNEL_6_ID
import yanshi.NotificationKT.App.statified.CHANNEL_7_ID
import yanshi.NotificationKT.App.statified.CHANNEL_8_ID
import yanshi.NotificationKT.App.statified.CHANNEL_9_ID
import yanshi.NotificationKT.App.statified.GROUP_1_ID
import yanshi.NotificationKT.App.statified.GROUP_2_ID

class App : Application() {

    object statified{
        val GROUP_1_ID = "group1"
        val GROUP_2_ID = "group2"
        const val CHANNEL_1_ID = "channel1"
        const val CHANNEL_2_ID = "channel2"
        const val CHANNEL_3_ID = "channel3"
        const val CHANNEL_4_ID = "channel4"
        const val CHANNEL_5_ID = "channel5"
        const val CHANNEL_6_ID = "channel6"
        const val CHANNEL_7_ID = "channel7"
        const val CHANNEL_8_ID = "channel8"
        const val CHANNEL_9_ID = "channel9"
    }

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()

    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val group1 = NotificationChannelGroup(
                GROUP_1_ID,
                "Group 1"
            )

            val group2 = NotificationChannelGroup(
                GROUP_2_ID,
                "Group 2"
            )


//          GROUP 1
            val channel1 = NotificationChannel(
                CHANNEL_1_ID,
                "Channel 1",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel1.description = "This is channel 1"
            channel1.group = GROUP_1_ID

            val channel2 = NotificationChannel(
                CHANNEL_2_ID,
                "Channel 2",
                NotificationManager.IMPORTANCE_LOW
            )
            channel2.description = "This is channel 2"
            channel2.group = GROUP_1_ID

            val channel3 = NotificationChannel(
                CHANNEL_3_ID,
                "Channel 3",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel3.description = "This is channel 3"
            channel3.group = GROUP_1_ID

            val channel4 = NotificationChannel(
                CHANNEL_4_ID,
                "Channel 4",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel4.description = "This is channel 4"
            channel4.group = GROUP_1_ID

            val channel5 = NotificationChannel(
                CHANNEL_5_ID,
                "Channel 5",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel5.description = "This is channel 5"
            channel5.group = GROUP_1_ID

            val channel6 = NotificationChannel(
                CHANNEL_6_ID,
                "Channel 6",
                NotificationManager.IMPORTANCE_LOW
            )
            channel6.description = "This is channel 6"
            channel6.group = GROUP_1_ID

            val channel7 = NotificationChannel(
                CHANNEL_7_ID,
                "Channel 7",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel7.description = "This is channel 7"
            channel7.group = GROUP_1_ID


//          GROUP 2
            val channel8 = NotificationChannel(
                CHANNEL_8_ID,
                "Channel 8",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel8.description = "This is channel 8"
            channel8.group = GROUP_2_ID


//          NO GROUP
            val channel9 = NotificationChannel(
                CHANNEL_9_ID,
                "Channel 9",
                NotificationManager.IMPORTANCE_LOW
            )
            channel9.description = "This is channel 9"


            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannelGroup(group1)
            manager.createNotificationChannelGroup(group2)
            manager.createNotificationChannel(channel1)
            manager.createNotificationChannel(channel2)
            manager.createNotificationChannel(channel3)
            manager.createNotificationChannel(channel4)
            manager.createNotificationChannel(channel5)
            manager.createNotificationChannel(channel6)
            manager.createNotificationChannel(channel7)
            manager.createNotificationChannel(channel8)
            manager.createNotificationChannel(channel9)
        }
    }

}