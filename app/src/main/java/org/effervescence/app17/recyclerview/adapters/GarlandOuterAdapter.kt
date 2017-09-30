package org.effervescence.app17.recyclerview.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ramotion.garlandview.TailAdapter
import org.effervescence.app17.R
import org.effervescence.app17.models.Event
import org.effervescence.app17.recyclerview.viewholders.EventOuterItem

/**
 * Created by betterclever on 29/09/17.
 */

class GarlandOuterAdapter(private val mData: List<List<Event>>) : TailAdapter<EventOuterItem>() {

    override fun onBindViewHolder(holder: EventOuterItem?, position: Int) {
        holder?.setContent(mData[position])
    }

    private val POOL_SIZE = 16
    private val mPool = RecyclerView.RecycledViewPool()

    init {
        mPool.setMaxRecycledViews(0, POOL_SIZE)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventOuterItem {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)

        print("Hello")
        return EventOuterItem(view, mPool)
    }


    override fun getItemCount(): Int {
        return mData.size
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.event_outer_item
    }

}