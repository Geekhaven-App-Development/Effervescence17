package org.effervescence.app17.recyclerview.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.updates_layout.view.*
import org.effervescence.app17.R
import org.effervescence.app17.models.Notification
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by sashank on 1/10/17.
 */

class UpdatesAdapter : RecyclerView.Adapter<UpdatesAdapter.ViewHolder>() {
    val list = ArrayList<Notification>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent!!.context)
                    .inflate(R.layout.updates_layout, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position])
    }

    fun updateData(list: List<Notification>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun  bindItem(notification: Notification) {
            itemView.tv_title.text = notification.title
            itemView.tv_description.text = notification.description

            val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/India"))
            calendar.timeInMillis = notification.timestamp.times(1000L)

            val sdf = SimpleDateFormat("hh:mm a")
            //sdf.timeZone = TimeZone.getTimeZone("Asia/India")

            val time = sdf.format(calendar.time)

            sdf.applyPattern("MMM d")
            itemView.tv_timestamp.text = notification.timestamp.toString()
            itemView.tv_timestamp.text = "$time ${sdf.format(calendar.time)}"
        }
    }

}