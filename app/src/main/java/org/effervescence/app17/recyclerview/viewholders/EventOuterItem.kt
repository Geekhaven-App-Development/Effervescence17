package org.effervescence.app17.recyclerview.viewholders

import android.support.v7.widget.RecyclerView
import android.view.View

import com.ramotion.garlandview.header.HeaderDecorator
import com.ramotion.garlandview.header.HeaderItem
import com.ramotion.garlandview.inner.InnerLayoutManager
import com.ramotion.garlandview.inner.InnerRecyclerView
import kotlinx.android.synthetic.main.category_header.view.*
import kotlinx.android.synthetic.main.event_outer_item.view.*

import org.effervescence.app17.R
import org.effervescence.app17.models.Event
import org.effervescence.app17.recyclerview.adapters.GarlandInnerAdapter

import java.util.ArrayList

class EventOuterItem(itemView: View, pool: RecyclerView.RecycledViewPool) : HeaderItem(itemView) {

    private val mHeader: View
    private val mHeaderAlpha: View

    private val mRecyclerView: InnerRecyclerView

    private val mMiddleCollapsible = ArrayList<View>(2)

    private val m10dp: Int = itemView.context.resources.getDimensionPixelSize(R.dimen.dp10)
    private val m120dp: Int = itemView.context.resources.getDimensionPixelSize(R.dimen.dp120)
    private val mTitleSize1: Int = itemView.context.resources.getDimensionPixelSize(R.dimen.header_title2_text_size)
    private val mTitleSize2: Int = itemView.context.resources.getDimensionPixelSize(R.dimen.header_title2_name_text_size)

    private var mIsScrolling: Boolean = false

    init {

        mHeader = itemView.header
        mHeaderAlpha = itemView.header_alpha

        mRecyclerView = itemView.recyclerView
        mRecyclerView.recycledViewPool = pool

        val adapter = GarlandInnerAdapter()
        mRecyclerView.adapter = adapter

        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                mIsScrolling = newState != RecyclerView.SCROLL_STATE_IDLE
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                onItemScrolled(recyclerView, dx, dy)
            }
        })

        mRecyclerView.addItemDecoration(HeaderDecorator(
                itemView.context.resources.getDimensionPixelSize(R.dimen.dp80),
                itemView.context.resources.getDimensionPixelSize(R.dimen.inner_item_offset)))
    }

    override fun isScrolling(): Boolean = mIsScrolling

    override fun getViewGroup(): InnerRecyclerView = itemView.recyclerView

    override fun getHeader(): View = itemView.header

    override fun getHeaderAlphaView(): View = itemView.header_alpha

    fun setContent(category: String,innerDataList: List<Event>?) {
        val context = itemView.context

        itemView.headerTextView.text = category
        mRecyclerView.layoutManager = InnerLayoutManager()
        innerDataList?.let { (mRecyclerView.adapter as GarlandInnerAdapter).addData(it) }

    }

    internal fun clearContent() {
        (mRecyclerView.adapter as GarlandInnerAdapter).clearData()
    }

    private fun computeRatio(recyclerView: RecyclerView?): Float {
        val child0 = recyclerView!!.getChildAt(0)
        val pos = recyclerView.getChildAdapterPosition(child0)
        if (pos != 0) {
            return 0f
        }

        val height = child0.height
        val y = Math.max(0f, child0.y)
        return y / height
    }

    private fun onItemScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        val ratio = computeRatio(recyclerView)

        val footerRatio = Math.max(0f, Math.min(FOOTER_RATIO_START, ratio) - FOOTER_RATIO_DIFF) / FOOTER_RATIO_MAX
        val avatarRatio = Math.max(0f, Math.min(AVATAR_RATIO_START, ratio) - AVATAR_RATIO_DIFF) / AVATAR_RATIO_MAX
        val answerRatio = Math.max(0f, Math.min(ANSWER_RATIO_START, ratio) - ANSWER_RATIO_DIFF) / ANSWER_RATIO_MAX
        val middleRatio = Math.max(0f, Math.min(MIDDLE_RATIO_START, ratio) - MIDDLE_RATIO_DIFF) / MIDDLE_RATIO_MAX
    }

    companion object {

        private val AVATAR_RATIO_START = 1f
        private val AVATAR_RATIO_MAX = 0.25f
        private val AVATAR_RATIO_DIFF = AVATAR_RATIO_START - AVATAR_RATIO_MAX

        private val ANSWER_RATIO_START = 0.75f
        private val ANSWER_RATIO_MAX = 0.35f
        private val ANSWER_RATIO_DIFF = ANSWER_RATIO_START - ANSWER_RATIO_MAX

        private val MIDDLE_RATIO_START = 0.7f
        private val MIDDLE_RATIO_MAX = 0.1f
        private val MIDDLE_RATIO_DIFF = MIDDLE_RATIO_START - MIDDLE_RATIO_MAX

        private val FOOTER_RATIO_START = 1.1f
        private val FOOTER_RATIO_MAX = 0.35f
        private val FOOTER_RATIO_DIFF = FOOTER_RATIO_START - FOOTER_RATIO_MAX
    }

}
