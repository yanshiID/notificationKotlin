package yanshi.NotificationKT

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.app.RemoteInput
import yanshi.NotificationKT.MainActivity.MES.MESSAGES
import yanshi.NotificationKT.MainActivity.MES.sendOnChannel5Notification
import yanshi.NotificationKT.Message.aob.Messages

class DirectReplyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val remoteInput : Bundle = RemoteInput.getResultsFromIntent(intent)

        if (remoteInput != null) {
            val replyText : CharSequence? = remoteInput.getCharSequence("key_text_reply")
            val answer : Message = Messages(replyText, null)
            MESSAGES.add(answer)

            sendOnChannel5Notification(context)
        }

    }

}