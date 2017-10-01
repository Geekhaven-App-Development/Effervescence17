package org.effervescence.app17.recyclerview.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ramotion.garlandview.inner.InnerAdapter
import com.ramotion.garlandview.inner.InnerItem
import kotlinx.android.synthetic.main.inner_item.view.*
import org.effervescence.app17.R
import org.effervescence.app17.models.Event

import java.util.ArrayList


class GarlandInnerAdapter : InnerAdapter<GarlandInnerAdapter.EventInnerItem>() {

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

    inner class EventInnerItem(itemView: View): InnerItem(itemView) {
        override fun getInnerLayout(): View {
            return (itemView as ViewGroup).getChildAt(0)
        }

        fun bindItem(event: Event) {
            itemView.titleTextView.text = event.name
            itemView.locationTextView.text = event.location
            itemView.setOnClickListener({

            })
        }

        fun clearContent() {
            // Clear binding
        }
    }
}
