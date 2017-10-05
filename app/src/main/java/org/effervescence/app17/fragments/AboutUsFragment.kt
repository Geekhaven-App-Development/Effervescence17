package org.effervescence.app17.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.effervescence.app17.R
import android.content.Intent
import android.net.Uri
import kotlinx.android.synthetic.main.fragment_about.view.*
import android.support.customtabs.CustomTabsIntent

/**
 * Created by sashank on 4/10/17.
 */

class AboutUsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_about,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.fb_icon.setOnClickListener({
            val id = "effervescence.iiita/"
            val url = "https://www.facebook.com/" + id
            try {
                context.packageManager.getPackageInfo("com.facebook.katana", 0)
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=" + url))
                context.startActivity(intent)
            } catch (e: Exception) {
                openChromeTab(context,url)
            }
        })

        view.twitter_icon.setOnClickListener ({
            try {
                val intent = Intent(Intent.ACTION_VIEW,
                        Uri.parse("twitter://user?screen_name=goeffervescence"))
                startActivity(intent)

            } catch (e: Exception) {
                openChromeTab(context,"https://twitter.com/goeffervescence")
            }

        })

        view.insta_icon.setOnClickListener ({
            openChromeTab(context,"https://www.instagram.com/goeffervescence/")
        })

        view.youtube_icon.setOnClickListener ({
            openChromeTab(context,"https://www.youtube.com/channel/UCSKd_5A0v36U5Bt0y-SDJWg")
        })

        view.tv_web.setOnClickListener ({
            openChromeTab(context,"https://www.effe.org.in")
        })

        view.tv_social_cause.setOnClickListener ({
            openChromeTab(context,"https://www.effe.org.in/social-cause/")
        })
    }

    fun openChromeTab(context: Context,url: String){
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(context, Uri.parse(url))
    }
}