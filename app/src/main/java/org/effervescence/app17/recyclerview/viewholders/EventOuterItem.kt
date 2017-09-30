package org.effervescence.app17.recyclerview.viewholders

import android.content.Context
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.ramotion.garlandview.header.HeaderDecorator
import com.ramotion.garlandview.header.HeaderItem
import com.ramotion.garlandview.inner.InnerLayoutManager
import com.ramotion.garlandview.inner.InnerRecyclerView
import kotlinx.android.synthetic.main.event_outer_header.view.*
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

        // Init header
        mHeader = itemView.header
        mHeaderAlpha = itemView.header_alpha


        /*
        mHeaderCaption1 = itemView.header_text_1
        mHeaderCaption2 = itemView.header_text_2
        mName = itemView.findViewById(R.id.tv_name)
        mInfo = itemView.findViewById(R.id.tv_info)
        mAvatar = itemView.findViewById(R.id.avatar)

        mMiddle = itemView.findViewById(R.id.header_middle)
        mMiddleAnswer = itemView.findViewById(R.id.header_middle_answer)
        mFooter = itemView.findViewById(R.id.header_footer)

        mMiddleCollapsible.add(mAvatar.parent as View)
        mMiddleCollapsible.add(mName.parent as View)*/

        // Init RecyclerView
        mRecyclerView = itemView.recycler_view
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

        // Init fonts
        //DataBindingUtil.bind(((FrameLayout) mHeader).getChildAt(0));
    }

    override fun isScrolling(): Boolean {
        return mIsScrolling
    }

    override fun getViewGroup(): InnerRecyclerView {
        return itemView.recycler_view
    }

    override fun getHeader(): View {
        return itemView.header
    }

    override fun getHeaderAlphaView(): View {
        return itemView.header_alpha
    }

    fun setContent(innerDataList: List<Event>) {
        val context = itemView.context

        //final InnerData header = innerDataList.subList(0, 1).get(0);
        //final List<InnerData> tail = innerDataList.subList(1, innerDataList.size());

        mRecyclerView.layoutManager = InnerLayoutManager()
        (mRecyclerView.adapter as GarlandInnerAdapter).addData(innerDataList)

        /*Glide.with(context)
                .load(header.avatarUrl)
                .placeholder(R.drawable.avatar_placeholder)
                .bitmapTransform(new CropCircleTransformation(context))
                .into(mAvatar);*/

        /*final String title1 = header.title + "?";

        final Spannable title2 = new SpannableString(header.title + "? - " + header.name);
        title2.setSpan(new AbsoluteSizeSpan(mTitleSize1), 0, title1.length(), SPAN_INCLUSIVE_INCLUSIVE);
        title2.setSpan(new AbsoluteSizeSpan(mTitleSize2), title1.length(), title2.length(), SPAN_INCLUSIVE_INCLUSIVE);
        title2.setSpan(new ForegroundColorSpan(Color.argb(204, 255, 255, 255)), title1.length(), title2.length(), SPAN_INCLUSIVE_INCLUSIVE);

        mHeaderCaption1.setText(title1);
        mHeaderCaption2.setText(title2);

        mName.setText(String.format("%s %s", header.name, context.getString(R.string.asked)));
        mInfo.setText(String.format("%s %s · %s", header.age, context.getString(R.string.years), header.address));*/
    }

    internal fun clearContent() {
        //Glide.clear(mAvatar);
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

       /* ViewCompat.setPivotY(mFooter, 0f)
        ViewCompat.setScaleY(mFooter, footerRatio)
        ViewCompat.setAlpha(mFooter, footerRatio)

        ViewCompat.setPivotY(mMiddleAnswer, mMiddleAnswer.height.toFloat())
        ViewCompat.setScaleY(mMiddleAnswer, 1f - answerRatio)
        ViewCompat.setAlpha(mMiddleAnswer, 0.5f - answerRatio)

        ViewCompat.setAlpha(mHeaderCaption1, answerRatio)
        ViewCompat.setAlpha(mHeaderCaption2, 1f - answerRatio)*/
        /*        val mc2 = mMiddleCollapsible[1]
        ViewCompat.setPivotX(mc2, 0f)
        ViewCompat.setPivotY(mc2, (mc2.height / 2).toFloat())

        for (view in mMiddleCollapsible) {
            ViewCompat.setScaleX(view, avatarRatio)
            ViewCompat.setScaleY(view, avatarRatio)
            ViewCompat.setAlpha(view, avatarRatio)
        }*/

        /*val lp = mMiddle.layoutParams
        lp.height = m120dp - (m10dp * (1f - middleRatio)).toInt()
        mMiddle.layoutParams = lp*/
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
