package com.example.mymoviedatabase

import android.app.IntentService
import android.content.Intent
import com.example.mymoviedatabase.view.fragments.BROADCAST_INTENT_FILTER

const val MAIN_SERVICE_STRING_EXTRA = "Loaded from TMDB"

class MainService(name: String = "MainService") : IntentService(name) {

    override fun onHandleIntent(intent: Intent?) {
        intent?.let {
            val broadcastIntent = Intent(BROADCAST_INTENT_FILTER)
            broadcastIntent.putExtra(MAIN_SERVICE_STRING_EXTRA, "")
            sendBroadcast(broadcastIntent)
        }
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}