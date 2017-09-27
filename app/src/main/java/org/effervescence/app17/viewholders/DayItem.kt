package org.effervescence.app17.viewholders

import android.support.v7.widget.RecyclerView
import android.view.View
import com.ramotion.garlandview.header.HeaderItem
import com.ramotion.garlandview.inner.InnerRecyclerView
import org.effervescence.app17.R
import org.effervescence.app17.main.inner.InnerDataAdapter

/**
 * Created by sashank on 27/9/17.
 */

class DayItem(itemView: View, pool: RecyclerView.RecycledViewPool) : HeaderItem(itemView) {

    private val mHeader: View = null!!
    private val mHeaderAlpha: View = null!!

    private val mRecyclerView: InnerRecyclerView

    private var mIsScrolling: Boolean = false


    init {
        // Init RecyclerView
        mRecyclerView = itemView.findViewById<View>(R.id.recycler_view) as InnerRecyclerView
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

}