package org.effervescence.app17.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.effervescence.app17.R
import android.content.Intent
import android.net.Uri
import kotlinx.android.synthetic.main.fragment_about.view.*


/**
 * Created by sashank on 4/10/17.
 */

class AboutUsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_about,container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view!!.fb_icon.setOnClickListener({
            var intent: Intent? = null
            val Id = "effervescence.iiita/"
            try {
                context.packageManager.getPackageInfo("com.facebook.katana", 0)
                val url = "https://www.facebook.com/" + Id
                intent = Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=" + url))
            } catch (e: Exception) {
                val url = "https://facebook.com/" + Id
                intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
            }
            context.startActivity(intent)
        })

        view.twitter_icon.setOnClickListener ({
            try {
                val intent = Intent(Intent.ACTION_VIEW,
                        Uri.parse("twitter://user?screen_name=goeffervescence"))
                startActivity(intent)

            } catch (e: Exception) {
                startActivity(Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://twitter.com/goeffervescence")))
            }

        })

        view.insta_icon.setOnClickListener ({
            startActivity(Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.instagram.com/goeffervescence/")))
        })

        view.youtube_icon.setOnClickListener ({
            startActivity(Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/channel/UCSKd_5A0v36U5Bt0y-SDJWg")))
        })

        view.tv_web.setOnClickListener ({
            startActivity(Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.effe.org.in")))
        })

        view.tv_social_cause.setOnClickListener ({
            startActivity(Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.effe.org.in/social-cause/")))
        })
    }
}