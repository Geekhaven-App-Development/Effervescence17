package org.effervescence.app17.recyclerview.viewholders

import android.view.View
import android.view.ViewGroup
import com.ramotion.garlandview.inner.InnerItem
import kotlinx.android.synthetic.main.inner_item.view.*
import org.effervescence.app17.activities.EventDetailActivity
import org.effervescence.app17.models.Event
import org.jetbrains.anko.startActivity

class EventInnerItem(itemView: View): InnerItem(itemView) {
    override fun getInnerLayout(): View {
        return (itemView as ViewGroup).getChildAt(0)
    }

    fun bindItem(event: Event) {
        itemView.titleTextView.text = event.name
        itemView.locationTextView.text = event.location
        itemView.rootConstraintLayout.setOnClickListener({
            itemView.context.startActivity<EventDetailActivity>("id" to event.id)
        })
    }

    fun clearContent() {
        // Clear binding
    }
}