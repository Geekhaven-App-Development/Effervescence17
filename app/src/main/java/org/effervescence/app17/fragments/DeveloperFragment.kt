package org.effervescence.app17.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_developer.view.*
import org.effervescence.app17.R
import org.effervescence.app17.recyclerview.adapters.DeveloperAdapter
import org.effervescence.app17.utils.AppDB

/**
 * Created by sashank on 4/10/17.
 */

class DeveloperFragment: Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_developer,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = DeveloperAdapter(context)
        val appDB = AppDB.getInstance(context)

        view.recyclerView.layoutManager = LinearLayoutManager(context)
        view.recyclerView.adapter = adapter
        appDB.getAllDeveloperMembers().let { adapter.addDeveloper(it) }
        //Log.d("DATA", appDB.getAllDeveloperMembers()?.toString())

    }
}