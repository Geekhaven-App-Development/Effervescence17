package org.effervescence.app17.viewholders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.ramotion.garlandview.header.HeaderItem
import com.ramotion.garlandview.inner.InnerRecyclerView
import org.effervescence.app17.R
import org.effervescence.app17.main.inner.InnerDataAdapter
import org.effervescence.app17.models.Event

/**
 * Created by sashank on 27/9/17.
 */

class DayItem(itemView: View, pool: RecyclerView.RecycledViewPool) : HeaderItem(itemView) {

    private val mHeader: View = itemView.findViewById(R.id.header)
    private val mHeaderAlpha: View = itemView.findViewById(R.id.header_alpha)

    private var mRecyclerView: InnerRecyclerView = itemView.findViewById<View>(R.id.rv_inner) as InnerRecyclerView

    private var mIsScrolling: Boolean = false

    private var dayData: List<Event>? = null

    init {


        // Init RecyclerView
        mRecyclerView.recycledViewPool = pool
        mRecyclerView.adapter = InnerDataAdapter()

    }

    override fun isScrolling(): Boolean {
            return mIsScrolling
    }

    override fun getViewGroup(): InnerRecyclerView {
        return mRecyclerView
    }

    override fun getHeader(): View {
        return mHeader
    }

    override fun getHeaderAlphaView(): View {
        return mHeaderAlpha
    }

    internal fun setContent(data: List<Event>) {
        dayData = data
    }

}