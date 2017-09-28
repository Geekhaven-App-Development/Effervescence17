package org.effervescence.app17.fragments

import android.app.Fragment
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_sponsors.view.*
import org.effervescence.app17.R
import org.effervescence.app17.adapters.SponsorsAdapter
import org.effervescence.app17.models.Sponsor
import org.effervescence.app17.utils.SponsorDB
import java.util.*

/**
 * Created by akshat on 28/9/17.
 */

class SponsorFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sponsorDB = SponsorDB.getInstance(activity)
        var sponsors = sponsorDB.getAllSponsors()
        Collections.sort(sponsors, { a1: Sponsor, a2: Sponsor -> a1.priority - a2.priority })

        val sponsorAdapter = SponsorsAdapter(sponsors,activity)
        val sponsorRecyclerView = view.sponsors_list
        sponsorRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        sponsorRecyclerView.adapter = sponsorAdapter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_sponsors,container,false)
    }
}