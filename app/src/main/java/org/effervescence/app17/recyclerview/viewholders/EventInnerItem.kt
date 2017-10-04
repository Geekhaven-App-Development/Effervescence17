package org.effervescence.app17.recyclerview.viewholders

import android.view.View
import android.view.ViewGroup
import com.ramotion.garlandview.inner.InnerItem
import kotlinx.android.synthetic.main.inner_item.view.*
import org.effervescence.app17.R
import org.effervescence.app17.activities.EventDetailActivity
import org.effervescence.app17.models.Event
import org.effervescence.app17.utils.AppDB
import org.effervescence.app17.utils.GlideApp
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat
import java.util.*

class EventInnerItem(itemView: View) : InnerItem(itemView) {
    override fun getInnerLayout(): View = (itemView as ViewGroup).getChildAt(0)

    fun bindItem(event: Event) {
        itemView.titleTextView.text = event.name
        itemView.locationTextView.text = event.location

        val appDB = AppDB.getInstance(itemView.context)

        val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/India"))
        calendar.timeInMillis = event.timestamp.times(1000L)

        val sdf = SimpleDateFormat("hh:mm a")
        sdf.timeZone = TimeZone.getTimeZone("Asia/India")

        itemView.timeTextView.text = sdf.format(calendar.time)

        sdf.applyPattern("MMMM d, YYYY")
        itemView.dateTextView.text = sdf.format(calendar.time)

        GlideApp.with(itemView.context)
                .load(event.imageUrl)
                .circleCrop()
                .placeholder(R.drawable.ic_event)
                .into(itemView.eventImageView)

        itemView.rootConstraintLayout.setOnClickListener({
            itemView.context.startActivity<EventDetailActivity>("id" to event.id)
        })

    }

    fun clearContent() {
        itemView.titleTextView.text = ""
        itemView.locationTextView.text = ""
        itemView.timeTextView.text = ""
        itemView.dateTextView.text = ""
    }
}