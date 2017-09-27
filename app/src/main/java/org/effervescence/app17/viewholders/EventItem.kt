package org.effervescence.app17.main.inner


import android.view.View
import android.view.ViewGroup
import org.effervescence.app17.utils.EventData

class EventItem(itemView: View) : com.ramotion.garlandview.inner.InnerItem(itemView) {

    private val mEventLayout: View

    /*public final TextView mHeader;
    public final TextView mName;
    public final TextView mAddress;
    public final ImageView mAvatar;
    public final View mAvatarBorder;
    public final View mLine;*/

    var itemData: EventData? = null
        private set

    init {
        mEventLayout = (itemView as ViewGroup).getChildAt(0)
    }

    override fun getInnerLayout(): View {
        return mEventLayout
    }

    internal fun setContent(data: EventData) {
        itemData = data

    }

}
