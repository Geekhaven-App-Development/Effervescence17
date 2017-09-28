package org.effervescence.app17.viewholders


import android.view.View
import android.view.ViewGroup
import com.ramotion.garlandview.inner.InnerItem
import org.effervescence.app17.models.Event

class EventItem(itemView: View) : InnerItem(itemView) {

    private val mEventLayout: View = (itemView as ViewGroup).getChildAt(0)

    /*public final TextView mHeader;
    public final TextView mName;
    public final TextView mAddress;
    public final ImageView mAvatar;
    public final View mAvatarBorder;
    public final View mLine;*/

    private var itemData: Event? = null

    override fun getInnerLayout(): View {
        return mEventLayout
    }

    internal fun setContent(data: Event) {
        itemData = data
    }

}
