package yanshi.notificationKotlin

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.app.RemoteInput
import yanshi.notificationKotlin.MainActivity.MaOb.MESSAGES
import yanshi.notificationKotlin.MainActivity.MaOb.sendOnChannel5Notification
import yanshi.notificationKotlin.Message.MessOb.Messages

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