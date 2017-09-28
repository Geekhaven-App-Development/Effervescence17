package org.effervescence.app17.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ramotion.garlandview.TailAdapter
import com.ramotion.garlandview.inner.InnerItem
import org.effervescence.app17.R
import org.effervescence.app17.models.Event
import org.effervescence.app17.viewholders.DayItem

/**
 * Created by sashank on 27/9/17.
 */

class OuterDaysAdapter(private val mData: List<List<Event>>) : TailAdapter<DayItem>() {

    private val POOL_SIZE = 16
    private val mPool: RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()

    init {

        mPool.setMaxRecycledViews(0, POOL_SIZE)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayItem {
        /// TODO: INFLATE CORRECT VIEW HERE
        val view = LayoutInflater.from(parent.context).
                inflate(R.layout.layout_daysview, parent, false)
        Log.d("ADAPTER", "outer")
        val item = DayItem(view,mPool)
        return item
    }

    override fun onBindViewHolder(holder: DayItem, position: Int) {
        holder.setContent(mData[position])
    }

    override fun getItemCount(): Int {
        return mData.size
    }

}
