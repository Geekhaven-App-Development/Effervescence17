package org.effervescence.app17.activities

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_splash.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.effervescence.app17.R
import org.effervescence.app17.models.*
import org.effervescence.app17.utils.AnimatorListenerAdapter
import org.effervescence.app17.utils.AppDB
import org.jetbrains.anko.*


class SplashActivity : AppCompatActivity(), AnkoLogger {

    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        sharedPrefs = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

        animationView.setAnimation("loading_spinner.json")
        animationView.playAnimation()
        animationView.loop(true)

        when {
            isNetworkConnectionAvailable() -> {
                fetchLatestData()
            }
            !sharedPrefs.getBoolean("firstrun", true) -> {
                showFinishedAnimation()
            }
            else -> {
                animationView.cancelAnimation()
                showAlert()
            }
        }
    }

    private fun fetchLatestData() {
        doAsync {
            val appDB = AppDB.getInstance(this@SplashActivity)
            val client = OkHttpClient()


            try {
                val request = Request.Builder()
                        .url("https://effervescence-iiita.github.io/Effervescence17/data/events.json")
                        .build()


                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    val list = Moshi.Builder()
                            .build()
                            .adapter<Array<Event>>(Array<Event>::class.java)
                            .fromJson(response.body()?.string())

                    appDB.storeEvents(events = list.toList())
                }

                val request2 = Request.Builder()
                        .url("https://effervescence-iiita.github.io/Effervescence17/data/sponsors.json")
                        .build()
                val response2 = client.newCall(request2).execute()
                if (response2.isSuccessful) {
                    val arrayOfSponsors = Moshi.Builder().build()
                            .adapter<Array<Sponsor>>(Array<Sponsor>::class.java)
                            .fromJson(response2.body()?.string())
                    appDB.storeSponsors(arrayOfSponsors.toList())
                }

                val request3 = Request.Builder()
                        .url("https://effervescence-iiita.github.io/Effervescence17/data/team.json")
                        .build()
                val response3 = client.newCall(request3).execute()

                if (response3.isSuccessful) {
                    val teamArray = Moshi.Builder().build()
                            .adapter<Array<Person>>(Array<Person>::class.java)
                            .fromJson(response3.body()?.string())

                    appDB.storeTeam(teamArray.toList())
                }

                val request4 = Request.Builder()
                        .url("https://effervescence-iiita.github.io/Effervescence17/data/developer.json")
                        .build()
                val response4 = client.newCall(request4).execute()

                if(response4.isSuccessful) {
                    val developersArray = Moshi.Builder().build()
                            .adapter<Array<Developer>>(Array<Developer>::class.java)
                            .fromJson(response4.body()?.string())

                    appDB.storeDevelopers(developersArray.toList())
                }

                uiThread {
                    if (!response.isSuccessful || !response2.isSuccessful || !response3.isSuccessful || !response4.isSuccessful) {
                        if (sharedPrefs.getBoolean("firstrun", true)) {
                            showAlert()
                        } else {
                            showFinishedAnimation()
                        }
                    } else {
                        showFinishedAnimation()
                    }
                }
            } catch (exception: Exception) {
                uiThread {
                    animationView.cancelAnimation()
                    if (sharedPrefs.getBoolean("firstrun", true)) {
                        showAlert()
                    } else {
                        showFinishedAnimation()
                    }
                }
            }
        }
    }


    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("No internet Connection")
        builder.setMessage("Please turn on internet connection to continue. Internet is needed at least once before running the app.")
        builder.setNegativeButton("close") { _, _ -> finish() }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun showFinishedAnimation() {
        animationView.setAnimation("checked_done.json")
        animationView.loop(false)
        animationView.playAnimation()
        animationView.addAnimatorListener(AnimatorListenerAdapter(
                onStart = { },
                onEnd = {
                    sharedPrefs.edit().putBoolean("firstrun", false).commit()
                    startActivity<MainActivity>()
                    finish()
                },
                onCancel = { },
                onRepeat = { }))
    }

    private fun isNetworkConnectionAvailable(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = cm.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
        return if (isConnected) {
            debug("Network Connected")
            true
        } else {
            debug("Network not Connected")
            false
        }
    }

}
