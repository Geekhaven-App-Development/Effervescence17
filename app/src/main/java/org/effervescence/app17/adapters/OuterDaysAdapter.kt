package org.effervescence.app17.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ramotion.garlandview.TailAdapter
import org.effervescence.app17.utils.EventData
import org.effervescence.app17.viewholders.DayItem

/**
 * Created by sashank on 27/9/17.
 */

class OuterDaysAdapter(private val mData: List<List<EventData>>) : TailAdapter<DayItem>() {

    private val POOL_SIZE = 16
    private val mPool: RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()

    init {

        mPool.setMaxRecycledViews(0, POOL_SIZE)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayItem {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return DayItem(view, mPool)
    }

    override fun onBindViewHolder(holder: DayItem, position: Int) {

    }

    override fun getItemCount(): Int {
        return mData.size
    }

}
