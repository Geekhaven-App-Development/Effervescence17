package org.effervescence.app17.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ramotion.garlandview.TailLayoutManager
import com.ramotion.garlandview.TailSnapHelper
import com.ramotion.garlandview.header.HeaderTransformer
import kotlinx.android.synthetic.main.fragment_days.*
import org.effervescence.app17.R
import org.effervescence.app17.recyclerview.adapters.GarlandOuterAdapter
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
        eventData.add(appDB.getAllEvents().subList(0,10))
        eventData.add(appDB.getAllEvents().subList(10,20))
        eventData.add(appDB.getAllEvents().subList(20,30))

        //(activity as AppCompatActivity).setSupportActionBar(toolbar)

        initRecyclerView(eventData)
    }

    private fun initRecyclerView(data: List<List<Event>>) {

        daysRecyclerView.layoutManager = TailLayoutManager(context).setPageTransformer(HeaderTransformer())
        daysRecyclerView.adapter = GarlandOuterAdapter(data)

        TailSnapHelper().attachToRecyclerView(daysRecyclerView)
    }


}