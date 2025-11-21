package uk.ac.tees.mad.petcare.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val petName = intent.getStringExtra(NotificationScheduler.EXTRA_PET_NAME) ?: "Your pet"
        val petInfo = intent.getStringExtra(NotificationScheduler.EXTRA_PET_INFO) ?: "Reminder"

        val title = "$petName — PetCare Reminder"
        val body = petInfo

        NotificationHelper.showNotification(context, petName.hashCode(), title, body)
    }
}