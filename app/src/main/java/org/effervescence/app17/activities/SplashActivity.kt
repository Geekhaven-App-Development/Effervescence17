package org.effervescence.app17.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okhttp3.OkHttpClient
import okhttp3.Request
import org.effervescence.app17.R
import org.effervescence.app17.models.Event
import org.effervescence.app17.utils.EventDB
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.info
import org.jetbrains.anko.uiThread

class SplashActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        fetchLatestEventData()
    }

    private fun fetchLatestEventData(){
        doAsync {
            val client = OkHttpClient()
            val request = Request.Builder()
                    .url("https://api.myjson.com/bins/942p1")
                    .build()
            val response = client.newCall(request).execute()
            if(response.isSuccessful) {
               val list = Moshi.Builder()
                        .build()
                        .adapter<Array<Event>>(Array<Event>::class.java)
                        .fromJson(response.body()?.string())

                val eventDB = EventDB.getInstance(this@SplashActivity)
                eventDB.storeEvents(events = list.toList())

                uiThread {
                    // indicate download done
                    info(eventDB.getAllEvents())

                }
            }
        }
    }

}
