package org.effervescence.app17.main.inner

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import org.effervescence.app17.R
import org.effervescence.app17.models.Event
import org.effervescence.app17.viewholders.DayItem
import org.effervescence.app17.viewholders.EventItem


import java.util.ArrayList


class InnerDataAdapter : com.ramotion.garlandview.inner.InnerAdapter<EventItem>() {

    private val mData = ArrayList<Event>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventItem? {
        val view = LayoutInflater.from(parent.context).
                inflate(R.layout.card_event, parent, false)
        return EventItem(view)
    }

    override fun onBindViewHolder(holder: EventItem, position: Int) {
        holder.setContent(mData[position])
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    fun addData(innerDataList: List<Event>) {
        val size = mData.size
        mData.addAll(innerDataList)
        notifyItemRangeInserted(size, innerDataList.size)
    }

    fun clearData() {
        mData.clear()
        notifyDataSetChanged()
    }
}