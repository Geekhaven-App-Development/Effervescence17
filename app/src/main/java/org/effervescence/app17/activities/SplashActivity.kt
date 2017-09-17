package org.effervescence.app17.activities

import android.app.AlertDialog
import android.content.Context
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
import android.net.NetworkInfo
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.content.DialogInterface
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import com.airbnb.lottie.LottieAnimationView
import android.animation.ValueAnimator

class SplashActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Custom animation speed or duration.
        val animationView = findViewById<View>(R.id.animation_view) as LottieAnimationView
        val animator = ValueAnimator.ofFloat(0f, 1f)
                .setDuration(500)
        //animator.addUpdateListener { animation -> animationView.setProgress(animation.animatedValue) }   //error
        //animator.start()

        if(isNetworkConnectionAvailable() == true){
            fetchLatestEventData()
            animationView.setAnimation("checked_done.json");
            animationView.loop(false);
            animationView.playAnimation();
        }
        else if(savedInstanceState != null ){
            //fetch old data
        }
        else {
            animationView.cancelAnimation()
            showAlert()
        }
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

    fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("No internet Connection")
        builder.setMessage("Please turn on internet connection to continue")
        builder.setNegativeButton("close") { dialog, which -> finish() }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    fun isNetworkConnectionAvailable(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = cm.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
        if (isConnected) {
            Log.d("Network", "Connected")
            return true
        }
        else {
            //showAlert()
            Log.d("Network", "Not Connected")
            return false
        }
    }

}
