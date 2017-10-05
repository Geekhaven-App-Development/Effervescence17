package org.effervescence.app17.activities

import android.Manifest
import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_event_detail.*
import kotlinx.android.synthetic.main.organizer_layout.view.*
import org.effervescence.app17.R
import org.effervescence.app17.utils.AlarmReceiver
import org.effervescence.app17.utils.AppDB
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*
import android.util.Log

class EventDetailActivity : AppCompatActivity(), AnkoLogger {

    private lateinit var appDB: AppDB
    private lateinit var callNumber: String
    val mainActivity = this
    val requestCode = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        appDB = AppDB.getInstance(this)

        val eventID = intent.getLongExtra("id", 0)

        if (eventID != 0L) {
            val event = appDB.getEventByID(eventID)
            titleTextView.text = event.name
            descriptionTextView.text = event.description
            facebookLinkTextView.text = event.facebookEventLink

            val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/India"))
            if (event.timestamp < 100L) {
                timeTextView.text = "Online Event"
                dateTextView.text = ""
                reminderTV.text = "Online Event"
                reminderRL.isClickable = false
                locationTextView.text = "Online"
            } else {
                calendar.timeInMillis = event.timestamp.times(1000L)

                val sdf = SimpleDateFormat("hh:mm a")
                sdf.timeZone = TimeZone.getTimeZone("Asia/India")

                timeTextView.text = sdf.format(calendar.time)

                sdf.applyPattern("MMMM d, yyyy")
                dateTextView.text = sdf.format(calendar.time)

                locationTextView.text = event.location
            }

            headerImageView.scaleType = ImageView.ScaleType.CENTER_CROP
            if (event.imageUrl != "") {
                Picasso.with(this).load(event.imageUrl).into(headerImageView)
            }

            event.organizers.map {
                val view = View.inflate(this, R.layout.organizer_layout, null)
                view.organizerNameTV.text = it.name
                callNumber = it.phoneNumber.toString()
                view.positionTextView.text = callNumber

                view.button2.setOnClickListener({
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.CALL_PHONE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this,
                                arrayOf(Manifest.permission.READ_CONTACTS),
                                requestCode)
                    }
                    else
                        startActivity(Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" + callNumber)))
                })

                organizerLinearLayout.addView(view)
            }

            if (appDB.isBookmarked(event.id)) {
                bookmarkTV.text = "Bookmarked"
            }

            reminderRL.setOnClickListener({

            })

            if (event.facebookEventLink.isBlank()) {
                facebookLinkLayout.visibility = View.GONE
            }

            if (event.organizers.isEmpty()) {
                organizersLayout.visibility = View.GONE
            }

            if (event.additionalInfo.isEmpty()) {
                additionalInfoLayout.visibility = View.GONE
            } else {
                additionalInfoTextView.text = event.additionalInfo.joinToString { "\n" }
            }

            bookmarkRL.setOnClickListener({
                if (appDB.addBookmark(event.id)) {
                    toast("Bookmark Added Successfully!!")
                    bookmarkTV.text = "Bookmarked"
                } else {
                    toast("Sorry! An error occurred.")
                }
            })

            reminderRL.setOnClickListener({
                if (event.timestamp > 100L) {
                    toast("Reminder Added Successfully!!")
                    if (event.location.isEmpty())
                        remindForEvent(calendar.timeInMillis, "Reminder!!",
                                event.name + " is about to start!")
                    else
                        remindForEvent(calendar.timeInMillis, "Reminder!!",
                                event.name + " is about to start. Reach " + event.location + "!")
                }
            })

            facebookLinkLayout.setOnClickListener({
                val url = event.facebookEventLink
                try {
                    baseContext.packageManager.getPackageInfo("com.facebook.katana", 0)
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=" + url))
                    baseContext.startActivity(intent)
                } catch (e: Exception) {
                    val builder = CustomTabsIntent.Builder()
                    val customTabsIntent = builder.build()
                    customTabsIntent.launchUrl(this, Uri.parse(url))
                }
            })

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return true
    }


    fun remindForEvent(time: Long, title: String, message: String) {

        val context = baseContext
        val alarmIntent = Intent(context, AlarmReceiver().javaClass)
        alarmIntent.putExtra("message", message)
        alarmIntent.putExtra("title", title)

        val pendingIntent = PendingIntent.getBroadcast(context, 0,
                alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        //Reminder at 10 minutes before the event
        alarmManager.setExact(AlarmManager.ELAPSED_REALTIME, time - 10 * 10 * 1000, pendingIntent)

        //For testing...
        //alarmManager.setExact(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 10 * 1000 , pendingIntent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            requestCode -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivity(Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" + callNumber)))
            } else {
                Log.d("TAG", "Call Permission Not Granted")
            }
        }
    }
}