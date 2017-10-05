package org.effervescence.app17.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kannan.glazy.GlazyCard
import com.kannan.glazy.Utils
import com.kannan.glazy.pager.GlazyFragmentPagerAdapter
import com.kannan.glazy.transformers.GlazyPagerTransformer
import com.kannan.glazy.views.GlazyImageView.ImageCutType
import kotlinx.android.synthetic.main.fragment_proshows.*
import org.effervescence.app17.R

/**
 * Created by sashank on 30/9/17.
 */

class ProShowsFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_proshows, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager(view)
    }

    private fun setupViewPager(view: View) {
        val resources = context.resources
        val imgKumar = resources.getIdentifier("kumar", "drawable", activity.packageName)
        val imgFarhan = resources.getIdentifier("farhan", "drawable", activity.packageName)
        val imgEDM = resources.getIdentifier("edm", "drawable", activity.packageName)
        val imgHumour = resources.getIdentifier("humour_night", "drawable", activity.packageName)

        val desc_celeb = "They say that when you become a celebrity ,you own the world and the world owns you. " +
                "So get ready to own the world as the bar has been raised up higher. After superstars like Neha " +
                "Kakkar and Benny Dayal, Effervescence’17 proudly presents to you Farhan Akhtar on MTV Beats Night this October !"

        val desc_edm = "EDM isn’t just about drops and rave, it is the form of music that has something to offer" +
                " to everyone. Get ready to match you heartbeats with the beats and drops and dance your heart out " +
                "with famous Portuguese DJ duos, Club Banditz, all set to raise the electric mood in the atmosphere!"

        val desc_kavyom = "Gear up to enjoy the Infinite Sky of Poetry at Kavyom, being held for the very first " +
                "time in IIIT Allahabad. Relish an indelible and a stupendous evening with the one and only, Kumar " +
                "Vishwas. It is doubtlessly going to be the most congenial event for all poetry lovers out there."

        val desc_humour = "Even if laughter doesn't add years to you life, it surely adds life to your years. " +
                "Effervescence'17 presents to you the Humour Night for the very first time. We're bringing in the pros, " +
                "to help you forget your woes, at least for that one night!"

        val pagerAdapter = GlazyFragmentPagerAdapter(childFragmentManager, context)

        pagerAdapter.addCardItem(
                GlazyCard()
                        .withTitle("CELEB NIGHT")
                        .withSubTitle("15th October")
                        .withDescription(desc_celeb.toUpperCase())
                        .withImageRes(imgFarhan)
                        .withImageCutType(ImageCutType.WAVE)
                        .withImageCutHeightDP(50)
        )

        pagerAdapter.addCardItem(
                GlazyCard()
                        .withTitle("EDM NIGHT")
                        .withSubTitle("13th October")
                        .withDescription(desc_edm.toUpperCase())
                        .withImageRes(imgEDM)
                        .withImageCutType(ImageCutType.LINE_POSITIVE)
                        .withImageCutHeightDP(50)
        )

        pagerAdapter.addCardItem(
                GlazyCard()
                        .withTitle("KAVYOM")
                        .withSubTitle("14th October")
                        .withDescription(desc_kavyom.toUpperCase())
                        .withImageRes(imgKumar)
                        .withImageCutType(ImageCutType.ARC)
                        .withImageCutHeightDP(50)
        )

        pagerAdapter.addCardItem(
                GlazyCard()
                        .withTitle("HUMOUR NIGHT")
                        .withSubTitle("12th October")
                        .withDescription(desc_humour.toUpperCase())
                        .withImageRes(imgHumour)
                        .withImageCutType(ImageCutType.WAVE)
                        .withImageCutHeightDP(50)
        )

        pager.adapter = pagerAdapter
        pager.pageMargin = Utils.dpToPx(context, 25)
        pager.setPageTransformer(false, GlazyPagerTransformer())
    }

}