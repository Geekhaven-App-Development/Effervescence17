package org.effervescence.app17.recyclerview.adapters

import android.view.LayoutInflater
import android.view.ViewGroup

import com.ramotion.garlandview.inner.InnerAdapter
import org.effervescence.app17.R
import org.effervescence.app17.models.Event
import org.effervescence.app17.recyclerview.viewholders.EventInnerItem
import org.effervescence.app17.utils.AppDB

import java.util.ArrayList


class GarlandInnerAdapter : InnerAdapter<EventInnerItem>() {

    private val mData = ArrayList<Event>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventInnerItem {
        return EventInnerItem(LayoutInflater.from(parent.context)
                .inflate(viewType, parent, false))
    }

    override fun onBindViewHolder(holder: EventInnerItem, position: Int) {
        holder.bindItem(mData[position])
    }

    override fun onViewRecycled(holder: EventInnerItem) {
        holder.clearContent()
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.inner_item
    }

    fun addData(innerDataList: List<Event>) {
        mData.addAll(innerDataList)
        notifyItemRangeInserted(mData.size, innerDataList.size)
    }

    fun clearData() {
        mData.clear()
        notifyDataSetChanged()
    }

}
