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
import org.effervescence.app17.adapters.OuterDaysAdapter
import org.effervescence.app17.models.Event
import org.effervescence.app17.utils.AppDB


class DaysViewFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_days, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appDB = AppDB.getInstance(context)
        val eventData = ArrayList<List<Event>>()

        //TODO: Apply correct logic
        eventData.add(appDB.getAllEvents())
        eventData.add(appDB.getAllEvents())
        eventData.add(appDB.getAllEvents())

        initRecyclerView(eventData)
    }

    private fun initRecyclerView(data: List<List<Event>>) {

        daysRecyclerView.layoutManager = TailLayoutManager(context).setPageTransformer(HeaderTransformer())
        daysRecyclerView.adapter = OuterDaysAdapter(data)

        TailSnapHelper().attachToRecyclerView(daysRecyclerView)
    }


}