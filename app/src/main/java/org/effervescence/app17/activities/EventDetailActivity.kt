package org.effervescence.app17.activities

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.content.res.TypedArray
import android.os.Build
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.transition.Transition
import android.util.Pair
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


import java.util.ArrayList

import jp.wasabeef.glide.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.activity_event_detail.*
import org.effervescence.app17.R

class EventDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fullName = intent.getStringExtra(BUNDLE_NAME)
        //val title = fullName.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] + getString(R.string.profile)

        (findViewById<View>(R.id.tv_title) as TextView).text = title
        (findViewById<View>(R.id.tv_name) as TextView).text = fullName
        (findViewById<View>(R.id.tv_info) as TextView).text = intent.getStringExtra(BUNDLE_INFO)
        //(findViewById<View>(R.id.tv_status) as TextView).text = intent.getStringExtra(BUNDLE_STATUS)

        val recyclerView = findViewById<View>(R.id.recycler_view) as RecyclerView
        //val listData = intent.getParcelableArrayListExtra<DetailsData>(BUNDLE_LIST_DATA)
        //recyclerView.adapter = ProfileAdapter(listData)

        Glide.with(this)
                .load(intent.getStringExtra(BUNDLE_AVATAR_URL))
                .apply(RequestOptions.bitmapTransform(CropCircleTransformation()))
                .into(avatar)

        val appBarLayout = findViewById<View>(R.id.app_bar) as AppBarLayout

        appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {

            internal val headerImage = findViewById<View>(R.id.header_image)
            internal val headerInfo = findViewById<View>(R.id.header_info)
            internal val avatar = findViewById<View>(R.id.avatar_border)
            internal val texts = findViewById<View>(R.id.texts) as LinearLayout

            internal val avatarHOffset = resources.getDimensionPixelSize(R.dimen.profile_avatar_h_offset)
            internal val avatarVOffset = resources.getDimensionPixelSize(R.dimen.profile_avatar_v_offset)
            internal val avatarSize = resources.getDimensionPixelSize(R.dimen.profile_avatar_size)
            internal val textHOffset = resources.getDimensionPixelSize(R.dimen.profile_texts_h_offset)
            internal val textVMinOffset = resources.getDimensionPixelSize(R.dimen.profile_texts_v_min_offset)
            internal val textVMaxOffset = resources.getDimensionPixelSize(R.dimen.profile_texts_v_max_offset)
            internal val textVDiff = textVMaxOffset - textVMinOffset
            internal val header160 = resources.getDimensionPixelSize(R.dimen.dp160)
            internal val toolBarHeight: Int

            init {
                val styledAttributes = theme.obtainStyledAttributes(
                        intArrayOf(android.R.attr.actionBarSize))
                toolBarHeight = styledAttributes.getDimension(0, 0f).toInt() + statusBarHeight
                styledAttributes.recycle()

                avatar.pivotX = 0f
                avatar.pivotY = 0f
                texts.pivotX = 0f
                texts.pivotY = 0f
            }

            internal val textStart = ArrayList<Float>()

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                val diff = toolBarHeight + verticalOffset
                val y = if (diff < 0) header160 - diff else header160
                headerInfo.top = y

                val lp = headerImage.layoutParams as FrameLayout.LayoutParams
                lp.height = y
                headerImage.layoutParams = lp

                val totalScrollRange = appBarLayout.totalScrollRange
                val ratio = (totalScrollRange.toFloat() + verticalOffset) / totalScrollRange

                val avatarHalf = avatar.measuredHeight / 2
                val avatarRightest = appBarLayout.measuredWidth / 2 - avatarHalf - avatarHOffset
                val avatarTopmost = avatarHalf + avatarVOffset

                avatar.x = avatarHOffset + avatarRightest * ratio
                avatar.y = avatarVOffset - avatarTopmost * ratio
                avatar.scaleX = 0.5f + 0.5f * ratio
                avatar.scaleY = 0.5f + 0.5f * ratio

                if (textStart.isEmpty() && verticalOffset == 0) {
                    (0 until texts.childCount).mapTo(textStart) { texts.getChildAt(it).x }
                }

                texts.x = textHOffset + (avatarSize * 0.5f - avatarVOffset) * (1f - ratio)
                texts.y = textVMinOffset + textVDiff * ratio
                texts.scaleX = 0.8f + 0.2f * ratio
                texts.scaleY = 0.8f + 0.2f * ratio
                for (i in textStart.indices) {
                    texts.getChildAt(i).x = textStart[i] * ratio
                }
            }
        })

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.sharedElementEnterTransition.addListener(object : Transition.TransitionListener {

                internal var isStarting = true

                override fun onTransitionStart(transition: Transition) {
                    if (isStarting) {
                        isStarting = false

                        ViewCompat.setTransitionName(findViewById(R.id.header_image), null)
                        ViewCompat.setTransitionName(findViewById(R.id.recycler_view), null)
                    }
                }

                override fun onTransitionEnd(transition: Transition) {}
                override fun onTransitionCancel(transition: Transition) {}
                override fun onTransitionPause(transition: Transition) {}
                override fun onTransitionResume(transition: Transition) {}
            })
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return true
    }

    private val statusBarHeight: Int
        get() {
            var result = 0
            val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = resources.getDimensionPixelSize(resourceId)
            }
            return result
        }

    companion object {

        private val BUNDLE_NAME = "BUNDLE_NAME"
        private val BUNDLE_INFO = "BUNDLE_INFO"
        private val BUNDLE_STATUS = "BUNDLE_STATUS"
        private val BUNDLE_AVATAR_URL = "BUNDLE_AVATAR_URL"
        private val BUNDLE_LIST_DATA = "BUNDLE_LIST_DATA"

        fun start(activity: Activity,
                  url: String, name: String, info: String, status: String,
                  avatar: View, card: View, image: View, list: View) {
            val starter = Intent(activity, EventDetailActivity::class.java)
            starter.putExtra(BUNDLE_NAME, name)
            starter.putExtra(BUNDLE_INFO, info)
            starter.putExtra(BUNDLE_STATUS, status)
            starter.putExtra(BUNDLE_AVATAR_URL, url)
            //starter.putParcelableArrayListExtra(BUNDLE_LIST_DATA, listData)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val p1 = Pair.create(avatar, activity.getString(R.string.transition_avatar_border))
                val p2 = Pair.create(card, activity.getString(R.string.transition_card))
                val p3 = Pair.create(image, activity.getString(R.string.transition_background))
                val p4 = Pair.create(list, activity.getString(R.string.transition_list))

                val options = ActivityOptions.makeSceneTransitionAnimation(activity, p1, p2, p3, p4)
                activity.startActivity(starter, options.toBundle())
            } else {
                activity.startActivity(starter)
            }
        }
    }

}
