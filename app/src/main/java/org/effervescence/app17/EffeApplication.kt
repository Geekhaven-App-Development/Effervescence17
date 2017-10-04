package org.effervescence.app17

import android.app.Application
import com.google.firebase.messaging.FirebaseMessaging

/**
 * Created by betterclever on 10/4/2017.
 */
class EffeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseMessaging.getInstance().subscribeToTopic("all")
    }
}