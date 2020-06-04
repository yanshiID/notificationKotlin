package yanshi.notificationKotlin

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat

class CustomReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "Image Clicked", Toast.LENGTH_SHORT).show()

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.cancel(10)
    }

}