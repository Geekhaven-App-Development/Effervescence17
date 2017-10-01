package org.effervescence.app17.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_sponsors.view.*
import org.effervescence.app17.R
import org.effervescence.app17.recyclerview.adapters.SponsorsAdapter
import org.effervescence.app17.utils.AppDB

/**
 * Created by akshat on 28/9/17.
 */

class SponsorFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sponsorDB = AppDB.getInstance(activity)
        val sponsors = sponsorDB.getAllSponsors()
        sponsors.sortBy { it.priority }

        val sponsorAdapter = SponsorsAdapter(sponsors, activity)
        val sponsorRecyclerView = view.sponsors_list
        sponsorRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        sponsorRecyclerView.adapter = sponsorAdapter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_sponsors, container, false)
    }
}