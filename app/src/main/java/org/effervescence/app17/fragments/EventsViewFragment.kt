package org.effervescence.app17.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ramotion.garlandview.TailLayoutManager
import com.ramotion.garlandview.TailSnapHelper
import com.ramotion.garlandview.header.HeaderTransformer
import kotlinx.android.synthetic.main.fragment_days.*
import org.effervescence.app17.R
import org.effervescence.app17.models.Event
import org.effervescence.app17.recyclerview.adapters.GarlandOuterAdapter
import org.effervescence.app17.utils.AppDB


class EventsViewFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_days, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appDB = AppDB.getInstance(context)
        val eventData = HashMap<String, List<Event>>()

        eventData["Main Stage"] = appDB.getEventsOfCategory("main stage")
        eventData["Dramatics"] = appDB.getEventsOfCategory("dramatics")
        eventData["Music"] = appDB.getEventsOfCategory("music")
        eventData["Dance"] = appDB.getEventsOfCategory("dance")
        eventData["Fine Arts"] = appDB.getEventsOfCategory("fine arts")
        eventData["AMS"] = appDB.getEventsOfCategory("AMS")
        eventData["Literature"] = appDB.getEventsOfCategory("literature")
        eventData["Informal"] = appDB.getEventsOfCategory("informal")

        val categories = arrayOf("Main Stage", "Dramatics", "Music", "Dance", "Fine Arts", "AMS", "Literature", "Informal")
        initRecyclerView(categories, eventData)
    }

    private fun initRecyclerView(categories: Array<String>, data: Map<String, List<Event>>) {

        daysRecyclerView.layoutManager = TailLayoutManager(context).setPageTransformer(HeaderTransformer())
        daysRecyclerView.adapter = GarlandOuterAdapter(categories,data)

        TailSnapHelper().attachToRecyclerView(daysRecyclerView)
    }


}