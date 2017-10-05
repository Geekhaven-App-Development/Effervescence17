package org.effervescence.app17.activities

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
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

class EventDetailActivity : AppCompatActivity(), AnkoLogger {

    private lateinit var appDB : AppDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)
        remindForEvent(null,"Test","Testing notifs")

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
            calendar.timeInMillis = event.timestamp.times(1000L)

            val sdf = SimpleDateFormat("hh:mm a")
            sdf.timeZone = TimeZone.getTimeZone("Asia/India")

            timeTextView.text = sdf.format(calendar.time)

            sdf.applyPattern("MMMM d, YYYY")
            dateTextView.text = sdf.format(calendar.time)

            locationTextView.text = event.location

            headerImageView.scaleType = ImageView.ScaleType.CENTER_CROP
            if (event.imageUrl != "") {
                Picasso.with(this).load(event.imageUrl).into(headerImageView)
            }

            event.organizers.map {
                val view = View.inflate(this, R.layout.organizer_layout, null)
                view.organizerNameTV.text = it.name
                view.positionTextView.text = it.phoneNumber.toString()
                organizerLinearLayout.addView(view)
            }

            if(appDB.isBookmarked(event.id)){
                bookmarkTV.text = "Bookmarked"
            }

            reminderRL.setOnClickListener({

            })

            if(event.facebookEventLink.isBlank()){
                facebookLinkLayout.visibility = View.GONE
            }

            if(event.organizers.isEmpty()){
                organizersLayout.visibility = View.GONE
            }

            if(event.additionalInfo.isEmpty()){
                additionalInfoLayout.visibility = View.GONE
            } else {
                additionalInfoTextView.text = event.additionalInfo.joinToString { "\n" }
            }


            bookmarkRL.setOnClickListener({
                if(appDB.addBookmark(event.id)){
                    toast("Bookmark Added Successfully!!")
                    bookmarkTV.text = "Bookmarked"
                }else {
                    toast("Sorry! An error occurred.")
                }
            })
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return true
    }


    fun remindForEvent(dateTime: Date?, title: String, message: String) {

        val context = baseContext
        val alarmIntent = Intent(context, AlarmReceiver().javaClass)
        alarmIntent.putExtra("message", message)
        alarmIntent.putExtra("title", title)

        val pendingIntent = PendingIntent.getBroadcast(context, 0,
                alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        //TODO: For demo set after 30 minutes.
        alarmManager.set(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 30 * 60 * 1000, pendingIntent)

    }

}
