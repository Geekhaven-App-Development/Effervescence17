package org.effervescence.app17.recyclerview.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.bookmark_event_layout.view.*
import org.effervescence.app17.R
import org.effervescence.app17.models.Event
import java.util.*

/**
 * Created by betterclever on 17/09/17.
 */

class BookmarksAdapter(val context: Context) : RecyclerView.Adapter<BookmarksAdapter.ViewHolder>() {

    private val events = ArrayList<Event>()

    override fun getItemCount() = events.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.bookmark_event_layout,parent,false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(context, events[position])
    }

    fun addEvents(events: List<Event>){
        this.events.addAll(events)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(context: Context, event: Event) {
            itemView.eventNameTV.text = event.name
            itemView.eventTime.text = Date(event.timestamp).toString().substring(4,16)
            // TODO: Set image Glide
            if(event.imageUrl.isEmpty())
                itemView.eventImageView.setImageResource(R.drawable.placeholder_event);
            else
                Picasso.with(context).load(event.imageUrl).placeholder(R.drawable.placeholder_event).into(itemView.eventImageView);

        }
    }
}