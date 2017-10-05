package org.effervescence.app17.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_team.view.*

import org.effervescence.app17.R
import org.effervescence.app17.recyclerview.adapters.TeamAdapter
import org.effervescence.app17.utils.AppDB


class TeamFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_team, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TeamAdapter(context)
        val appDB = AppDB.getInstance(context)

        view.recyclerView.layoutManager = LinearLayoutManager(context)
        view.recyclerView.adapter = adapter
        appDB.getAllTeamMembers().let { adapter.addTeam(it) }

    }
}
