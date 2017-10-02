package org.effervescence.app17.activities

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Pair
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.activity_event_detail.*
import kotlinx.android.synthetic.main.organizer_layout.view.*
import org.effervescence.app17.R
import org.effervescence.app17.utils.AppDB
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class EventDetailActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        info(intent.extras)
        val extras = intent.extras

        val eventID = intent.getLongExtra("id",0)

        if(eventID != 0L) {
            val event = AppDB.getInstance(this).getEventByID(eventID)
            titleTextView.text = event.name
            descriptionTextView.text = event.description
            facebookLinkTextView.text = event.facebookEventLink

            //dateTextView.text = Date(event.timestamp.times(1000)).toString()
            //locationTextView.text = event.location

            headerImageView.scaleType = ImageView.ScaleType.CENTER_CROP
            if(event.imageUrl != ""){
                Picasso.with(this).load(event.imageUrl).into(headerImageView)
            }

            event.organizers.map {
                val view = View.inflate(this,R.layout.organizer_layout, null)
                view.organizerNameTV.text = it.name
                view.phoneNumberTextView.text = it.phoneNumber.toString()
                organizerLinearLayout.addView(view)
            }
        }

        /*appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {

            internal val headerImage = findViewById<View>(R.id.header_image)
            internal val headerInfo = findViewById<View>(R.id.header_info)
            internal val avatarBorder = findViewById<View>(R.id.avatar_border)

            internal val avatarHOffset = resources.getDimensionPixelSize(R.dimen.profile_avatar_h_offset)
            internal val avatarVOffset = resources.getDimensionPixelSize(R.dimen.profile_avatar_v_offset)
            internal val avatarSize = resources.getDimensionPixelSize(R.dimen.profile_avatar_size)
            internal val textHOffset = resources.getDimensionPixelSize(R.dimen.profile_texts_h_offset)
            internal val textVMinOffset = resources.getDimensionPixelSize(R.dimen.profile_texts_v_min_offset)
            internal val textVMaxOffset = resources.getDimensionPixelSize(R.dimen.profile_texts_v_max_offset)
            internal val textVDiff = textVMaxOffset - textVMinOffset
            internal val header160 = resources.getDimensionPixelSize(R.dimen.dp190)
            internal val toolBarHeight: Int

            init {
                val styledAttributes = theme.obtainStyledAttributes(
                        intArrayOf(android.R.attr.actionBarSize))
                toolBarHeight = styledAttributes.getDimension(0, 0f).toInt() + statusBarHeight
                styledAttributes.recycle()

                avatarBorder.pivotX = 0f
                avatarBorder.pivotY = 0f
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

                val avatarHalf = avatar_border.measuredHeight / 2
                val avatarRightest = appBarLayout.measuredWidth / 2 - avatarHalf - avatarHOffset
                val avatarTopmost = avatarHalf + avatarVOffset

                avatar_border.x = avatarHOffset + avatarRightest * ratio
                avatar_border.y = avatarVOffset - avatarTopmost * ratio
                avatar_border.scaleX = 0.5f + 0.5f * ratio
                avatar_border.scaleY = 0.5f + 0.5f * ratio

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
                        ViewCompat.setTransitionName(findViewById(R.id.recyclerView), null)
                    }
                }

                override fun onTransitionEnd(transition: Transition) {}
                override fun onTransitionCancel(transition: Transition) {}
                override fun onTransitionPause(transition: Transition) {}
                override fun onTransitionResume(transition: Transition) {}
            })
        }*/
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
