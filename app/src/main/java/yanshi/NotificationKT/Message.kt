package yanshi.NotificationKT

import yanshi.NotificationKT.Message.aob.sender
import yanshi.NotificationKT.Message.aob.text
import yanshi.NotificationKT.Message.aob.timestamp

class Message {

    object aob {

        var text : CharSequence? = null
        var timestamp: Long? = null
        var sender : CharSequence? = null

        fun Messages(text: CharSequence?, sender: CharSequence?): Message {
            this.text = text
            this.sender = sender
            timestamp = System.currentTimeMillis()

            return Message()
        }

    }

    fun getText() : CharSequence? { return text }
    fun getTimeStamp() : Long? { return timestamp }
    fun getSender() : CharSequence? { return sender }

}