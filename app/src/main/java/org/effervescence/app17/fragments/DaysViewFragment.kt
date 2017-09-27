package org.effervescence.app17.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ramotion.garlandview.TailLayoutManager
import com.ramotion.garlandview.TailRecyclerView
import com.ramotion.garlandview.TailSnapHelper
import com.ramotion.garlandview.header.HeaderTransformer
import org.effervescence.app17.R
import org.effervescence.app17.adapters.OuterDaysAdapter
import org.effervescence.app17.utils.EventData
import org.effervescence.app17.utils.EventDB


class DaysViewFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val v = inflater.inflate(R.layout.fragment_days, container, false)

        // initRecyclerView(null, v)
        // Inflate the layout for this fragment
        return v
    }

    private fun initRecyclerView(data: List<List<EventData>>, v: View) {

        val rv = v.findViewById<View>(R.id.recycler_view) as TailRecyclerView
        rv.layoutManager = TailLayoutManager(context).setPageTransformer(HeaderTransformer())
        rv.adapter = OuterDaysAdapter(data)

        TailSnapHelper().attachToRecyclerView(rv)
    }


}