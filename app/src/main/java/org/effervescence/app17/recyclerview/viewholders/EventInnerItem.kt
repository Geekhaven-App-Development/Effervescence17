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

        itemView.timeTextView.text = SimpleDateFormat("hh:mm a").format(calendar.time)
        itemView.dateTextView.text = SimpleDateFormat("MMMM d, YYYY").format(calendar.time)

        GlideApp.with(itemView.context)
                .load(event.imageUrl)
                .circleCrop()
                .placeholder(R.drawable.ic_event)
                .into(itemView.eventImageView)

        itemView.rootConstraintLayout.setOnClickListener({
            itemView.context.startActivity<EventDetailActivity>("id" to event.id)
        })

        /*if(appDB.isBookmarked(event.id)){
            itemView.bookmarkImageView.setImageResource(R.drawable.ic_bookmark_black_24dp)
        }
        else {
            itemView.bookmarkImageView.setImageResource(R.drawable.ic_bookmark_border_black_24dp)
        }*/
    }

    fun clearContent() {
        // Clear binding
    }
}