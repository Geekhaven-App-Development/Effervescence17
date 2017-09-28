package org.effervescence.app17.activities

import android.animation.ValueAnimator
import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.airbnb.lottie.LottieAnimationView
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_splash.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.effervescence.app17.R
import org.effervescence.app17.models.Event
import org.effervescence.app17.models.Sponsor
import org.effervescence.app17.utils.AnimatorListenerAdapter
import org.effervescence.app17.utils.AppDB
import org.effervescence.app17.utils.SponsorDB
import org.jetbrains.anko.*
import java.util.*


class SplashActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Custom animation speed or duration.
        val animator = ValueAnimator.ofFloat(0f, 1f)
                .setDuration(500)
        //animator.addUpdateListener { animation -> animationView.setProgress(animation.animatedValue) }   //error
        //animator.start()

        animationView.setAnimation("loading_spinner.json")
        animationView.scale = 0.1f
        animationView.playAnimation()
        animationView.loop(true)

        when {
            isNetworkConnectionAvailable() -> {
                fetchLatestEventData()
            }
            savedInstanceState != null -> {
                //fetch old data
            }
            else -> {
                animationView.cancelAnimation()
                showAlert()
            }
        }
    }

    private fun fetchLatestEventData(){
        doAsync {
            val client = OkHttpClient()
            val request = Request.Builder()
                    .url("https://effervescence-iiita.github.io/Effervescence17/data/events.json")
                    .build()
            val response = client.newCall(request).execute()
            if(response.isSuccessful) {
               val list = Moshi.Builder()
                        .build()
                        .adapter<Array<Event>>(Array<Event>::class.java)
                        .fromJson(response.body()?.string())

                val eventDB = AppDB.getInstance(this@SplashActivity)
                eventDB.storeEvents(events = list.toList())
                val request2 = Request.Builder()
                        .url("https://effervescence-iiita.github.io/Effervescence17/data/sponsors.json")
                        .build()
                val response2 = client.newCall(request2).execute()
                if(response2.isSuccessful) {
                    val list2 = Moshi.Builder().build()
                            .adapter<Array<Sponsor>>(Array<Sponsor>::class.java)
                            .fromJson(response2.body()?.string())
                    var sponsors2 = list2.toList()
                    val sponsorDB = SponsorDB.getInstance(this@SplashActivity)
                    sponsorDB.storeSponsors(sponsors2)
                }

                uiThread {
                    // indicate download done
                    info(eventDB.getAllEvents())

                    animationView.setAnimation("checked_done.json")
                    animationView.loop(false)
                    animationView.playAnimation()
                    animationView.addAnimatorListener(AnimatorListenerAdapter(
                            onStart = {  },
                            onEnd = {
                                startActivity<MainActivity>()
                                finish()
                            },
                            onCancel = { },
                            onRepeat =  { }))
                }
            }
        }
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("No internet Connection")
        builder.setMessage("Please turn on internet connection to continue")
        builder.setNegativeButton("close") { _, _ -> finish() }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun isNetworkConnectionAvailable(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = cm.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
        return if (isConnected) {
            debug("Network Connected")
            true
        }
        else {
            debug("Network not Connected")
            false
        }
    }

}
