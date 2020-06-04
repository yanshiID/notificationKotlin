package yanshi.NotificationKT

//  Converted from java to kotlin by yanshiID from codinginflow.com
//  https://www.youtube.com/playlist?list=PLrnPJCHvNZuCN52QwGu7YTSLIMrjCF0gM

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.provider.Settings
import android.support.v4.media.session.MediaSessionCompat
import android.view.View
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.Person
import androidx.core.app.RemoteInput
import androidx.core.graphics.drawable.IconCompat
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
import yanshi.NotificationKT.MainActivity.MES.MESSAGES
import yanshi.NotificationKT.MainActivity.MES.notificationManager
import yanshi.NotificationKT.MainActivity.MES.sendOnChannel5Notification
import yanshi.NotificationKT.Message.aob.Messages

class MainActivity : AppCompatActivity() {

    var editTextTitle: EditText?=null
    var editTextMessage: EditText?=null

    lateinit var mediaSession: MediaSessionCompat

    object MES{

        lateinit var notificationManager: NotificationManagerCompat

        val MESSAGES : MutableList<Message> = ArrayList()

//        MessagingStyle : https://www.youtube.com/watch?v=DsFYPTnCbs8&list=PLrnPJCHvNZuCN52QwGu7YTSLIMrjCF0gM&index=5
//    error on looping
        fun sendOnChannel5Notification(context: Context) {

            val activityIntent = Intent(context, MainActivity::class.java)
            val contentIntent : PendingIntent = PendingIntent.getActivity(context, 0, activityIntent, 0)

            val remoteInput = RemoteInput.Builder("key_text_reply")
                .setLabel("Your answer...")
                .build()

            val replyIntent : Intent
            var replyPendingIntent : PendingIntent? = null

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                replyIntent = Intent(context, DirectReplyReceiver::class.java)
                replyPendingIntent = PendingIntent.getBroadcast(context, 0, replyIntent, 0)
            } else {
                // start chat activity instead (PendingIntent.getActivity)
                // cancel notification with NotificationManagerCompat.cancel(id)
            }

            val replyAction = NotificationCompat.Action.Builder(
                R.drawable.ic_reply,
                "Reply",
                replyPendingIntent
            ).addRemoteInput(remoteInput).build()

            // Person added in API level 28
            val person = Person.Builder()
                .setIcon(IconCompat.createWithResource(context, R.drawable.ic_person))
                .setName("Me")
                .build()

            val messagingStyle = NotificationCompat.MessagingStyle("Me")  // Deprecated
//        val messagingStyle = NotificationCompat.MessagingStyle(person)
            messagingStyle.isGroupConversation = true
            messagingStyle.conversationTitle = "Group Chat"

            for (chatMessage : Message in MESSAGES) {
                val notificationMessage : NotificationCompat.MessagingStyle.Message =
                    NotificationCompat.MessagingStyle.Message(
                        chatMessage.getText(),
                        chatMessage.getTimeStamp()!!,
                        chatMessage.getSender()
                    )
                messagingStyle.addMessage(notificationMessage)
            }

            val notification : Notification = NotificationCompat.Builder(context, CHANNEL_5_ID)
                .setSmallIcon(R.drawable.ic_five)
                .setStyle(messagingStyle)
                .addAction(replyAction)
                .setColor(Color.BLUE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .build()

            notificationManager.notify(1, notification)

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationManager = NotificationManagerCompat.from(this)

        editTextTitle = findViewById(R.id.edit_text_title)
        editTextMessage = findViewById(R.id.edit_text_message)

        mediaSession = MediaSessionCompat(this, "tag")

        MESSAGES.add(Messages("Good morning!", "Juan"))
        MESSAGES.add(Messages("Bello!", null))
        MESSAGES.add(Messages("Hi!", "Jenny"))
    }

//        BigTextStyle : https://www.youtube.com/watch?v=lVzhzi2e_Zw&list=PLrnPJCHvNZuCN52QwGu7YTSLIMrjCF0gM&index=3
    fun sendOnChannel1(v: View) {

        val title: String = editTextTitle?.text.toString()
        val message: String = editTextMessage?.text.toString()
        
        val activityIntent = Intent(this, MainActivity::class.java)
        val contentIntent = PendingIntent.getActivity(this,
        0, activityIntent, 0)

        val broadcastIntent = Intent(this, NotificationReceiver::class.java)
        broadcastIntent.putExtra("toastMessage", message)
        val actionIntent = PendingIntent.getBroadcast(this,
            0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val largeIcon : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.cover)

        val notification = NotificationCompat.Builder(this, CHANNEL_1_ID)
            .setSmallIcon(R.drawable.ic_one)
            .setContentTitle(title)
            .setContentText(message)
            .setLargeIcon(largeIcon)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(getString(R.string.long_dummy_text))
                .setBigContentTitle("Big Content Title")
                .setSummaryText("Summary Text"))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setColor(Color.BLUE)
            .setContentIntent(contentIntent)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .addAction(R.mipmap.ic_launcher, "Toast", actionIntent)
            .build()

        notificationManager.notify(1, notification)

    }

//      MediaStyle : https://www.youtube.com/watch?v=s0Q2QKZ4OP8&list=PLrnPJCHvNZuCN52QwGu7YTSLIMrjCF0gM&index=4
    fun sendOnChannel2(v: View) {

        val title: String = editTextTitle?.text.toString()
        val message: String = editTextMessage?.text.toString()

        val artwork : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.cover)

        val notification = NotificationCompat.Builder(this, CHANNEL_2_ID)
            .setSmallIcon(R.drawable.ic_two)
            .setContentTitle(title)
            .setContentText(message)
            .setLargeIcon(artwork)
            .addAction(R.drawable.ic_dislike, "Dislike", null)
            .addAction(R.drawable.ic_previous, "Previous", null)
            .addAction(R.drawable.ic_pause, "Pause", null)
            .addAction(R.drawable.ic_next, "Next", null)
            .addAction(R.drawable.ic_like, "Like", null)
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle()
                .setShowActionsInCompactView(1, 2, 3)
                .setMediaSession(mediaSession.sessionToken))
            .setSubText("Sub Text")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()

        notificationManager.notify(2, notification)

    }

//        BigPictureStyle : https://www.youtube.com/watch?v=s0Q2QKZ4OP8&list=PLrnPJCHvNZuCN52QwGu7YTSLIMrjCF0gM&index=4
    fun sendOnChannel3(v: View) {

        val title: String = editTextTitle?.text.toString()
        val message: String = editTextMessage?.text.toString()

        val activityIntent = Intent(this, MainActivity::class.java)
        val contentIntent = PendingIntent.getActivity(this,
            0, activityIntent, 0)

        val picture : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.cover)

        val notification = NotificationCompat.Builder(this, CHANNEL_3_ID)
            .setSmallIcon(R.drawable.ic_three)
            .setContentTitle(title)
            .setContentText(message)
            .setLargeIcon(picture)
            .setStyle(NotificationCompat.BigPictureStyle()
                .bigPicture(picture)
                .bigLargeIcon(null))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setContentIntent(contentIntent)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .build()

        notificationManager.notify(3, notification)

    }

//      InboxStyle : https://www.youtube.com/watch?v=lVzhzi2e_Zw&list=PLrnPJCHvNZuCN52QwGu7YTSLIMrjCF0gM&index=3
    fun sendOnChannel4(v: View) {

        val title: String = editTextTitle?.text.toString()
        val message: String = editTextMessage?.text.toString()

        val notification = NotificationCompat.Builder(this, CHANNEL_4_ID)
            .setSmallIcon(R.drawable.ic_four)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.InboxStyle()
                .addLine("This is Line 1")
                .addLine("This is Line 2")
                .addLine("This is Line 3")
                .addLine("This is Line 4")
                .addLine("This is Line 5")
                .addLine("This is Line 6")
                .addLine("This is Line 7")
                .setBigContentTitle("Big Content Title")
                .setSummaryText("Summary Text"))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(4, notification)

    }

//        MessagingStyle : https://www.youtube.com/watch?v=DsFYPTnCbs8&list=PLrnPJCHvNZuCN52QwGu7YTSLIMrjCF0gM&index=5
    fun sendOnChannel5 (v: View) {

//        Set Notification Settings : https://www.youtube.com/watch?v=kfaZTF95Pt4&list=PLrnPJCHvNZuCN52QwGu7YTSLIMrjCF0gM&index=9
        if (!notificationManager.areNotificationsEnabled()) {
            openNotificationSettings()
            return
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && isChannelBlocked(CHANNEL_5_ID)) {
            openChannelSettings(CHANNEL_5_ID)
            return
        }

        sendOnChannel5Notification(this)
    }

    private fun openNotificationSettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
            startActivity(intent)
        } else {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse("package:$packageName")
            startActivity(intent)
        }
    }

    @RequiresApi(26)
    private fun isChannelBlocked(channelId : String) : Boolean {
        val manager = getSystemService(NotificationManager::class.java)
        val channel = manager.getNotificationChannel(channelId)

        return channel != null && channel.importance == NotificationManager.IMPORTANCE_NONE
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun openChannelSettings(channelId : String) {
        val intent = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS)
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
        intent.putExtra(Settings.EXTRA_CHANNEL_ID, channelId)
        startActivity(intent)
    }




//        ProgressBar : https://www.youtube.com/watch?v=IHNC7OtOEI4&list=PLrnPJCHvNZuCN52QwGu7YTSLIMrjCF0gM&index=6
    fun sendOnChannel6(v: View) {

        val progressMax = 100

        val notification = NotificationCompat.Builder(this, CHANNEL_6_ID)
            .setSmallIcon(R.drawable.ic_six)
            .setContentTitle("Download")
            .setContentText("Download in progress")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            // switch the indeterminate to /false/ to see the progress or keep it to see animation only
            .setProgress(progressMax, 0, true)

        notificationManager.notify(2, notification.build())

        Thread(Runnable {
            fun run() {
                SystemClock.sleep(2000)
                for (progress in 0 until progressMax step 20) {

                    // /uncomment/ the bottom script to see the progress or keep it to see animation only

//                    notification.setProgress(progressMax, progress, false)
//                    notificationManager.notify(2, notification.build())
                    SystemClock.sleep(1000)
                }
                notification.setContentText("Download Finished")
                    .setProgress(0,0,false)
                    .setOngoing(false)
                notificationManager.notify(2, notification.build())
            }
            run()
        }).start()

    }

//        NotificationGroups : https://www.youtube.com/watch?v=ZC4x4eEBU9U&list=PLrnPJCHvNZuCN52QwGu7YTSLIMrjCF0gM&index=7
    fun sendOnChannel7(v: View) {

        val group1 = "Group 1"
        val title1 = "Title 1"
        val message1 = "Message 1"
        val title2 = "Title 2"
        val message2 = "Message 2"

        val notification1 = NotificationCompat.Builder(this, CHANNEL_7_ID)
            .setSmallIcon(R.drawable.ic_7)
            .setContentTitle(title1)
            .setContentText(message1)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setGroup(group1)
            .build()

        val notification2 = NotificationCompat.Builder(this, CHANNEL_7_ID)
            .setSmallIcon(R.drawable.ic_7)
            .setContentTitle(title2)
            .setContentText(message2)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setGroup(group1)
            .build()

        val summaryNotification = NotificationCompat.Builder(this, CHANNEL_7_ID)
            .setSmallIcon(R.drawable.ic_reply)
            .setStyle(NotificationCompat.InboxStyle()
                .addLine("$title2 $message2")
                .addLine("$title1 $message1")
                .setBigContentTitle("2 new messages")
                .setSummaryText("user@example.com"))
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setGroup(group1)
            .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_CHILDREN)
            .setGroupSummary(true)
            .build()

        SystemClock.sleep(2000)
        notificationManager.notify(2, notification1)
        SystemClock.sleep(2000)
        notificationManager.notify(3, notification2)
        SystemClock.sleep(2000)
        notificationManager.notify(4, summaryNotification)

//        val notification = NotificationCompat.Builder(this, CHANNEL_2_ID)
//            .setSmallIcon(R.drawable.ic_7)
//            .setContentTitle("Title")
//            .setContentText("Message")
//            .setPriority(NotificationCompat.PRIORITY_LOW)
//            .build()

//        for (i in 1..5) {
//            SystemClock.sleep(2000)
//            notificationManager.notify(i, notification)
//        }
    }

//    Delete Notification Channels : https://www.youtube.com/watch?v=3Je9ZEAAjfA&list=PLrnPJCHvNZuCN52QwGu7YTSLIMrjCF0gM&index=10
    fun deleteNotificationChannels(v: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = getSystemService(NotificationManager::class.java)
//            Delete Channel
            manager.deleteNotificationChannel(CHANNEL_8_ID)

//            Delete Group Channel
//            manager.deleteNotificationChannelGroup(GROUP_1_ID)
        }
    }

}
