package yanshi.NotificationKT

//  Converted from java to kotlin by yanshiID from codinginflow.com
//  https://www.youtube.com/playlist?list=PLrnPJCHvNZuCN52QwGu7YTSLIMrjCF0gM

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat
import android.view.View
import android.widget.EditText
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

            for (chatMessage in MESSAGES) {
                val notificationMessage =
                    NotificationCompat.MessagingStyle.Message(
                        chatMessage.getText(),
                        chatMessage.getTimeStamp()!!,
                        chatMessage.getSender()
                    )
                messagingStyle.addMessage(notificationMessage)
            }

            val notification = NotificationCompat.Builder(context, CHANNEL_5_ID)
                .setSmallIcon(R.drawable.ic_one)
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

//        MessagingStyle
    fun sendOnChannel5 (v: View) {
        sendOnChannel5Notification(this)
    }

}
