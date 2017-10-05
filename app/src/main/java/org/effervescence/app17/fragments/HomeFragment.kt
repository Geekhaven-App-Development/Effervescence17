package org.effervescence.app17.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.effervescence.app17.R
import org.effervescence.app17.recyclerview.adapters.BookmarksAdapter
import org.effervescence.app17.recyclerview.adapters.UpcomingAdapter
import org.effervescence.app17.utils.AppDB
import java.util.*

/**
 * Created by betterclever on 16/09/17.
 */

class HomeFragment : Fragment() {

    companion object {
        fun newInstance(): HomeFragment {
            val args = Bundle()
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.upcomingRecyclerView.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false)

        val layoutManager = LinearLayoutManager(context)
        layoutManager.isAutoMeasureEnabled = true

        view.bookmarksRecyclerView.layoutManager = LinearLayoutManager(context)
        view.bookmarksRecyclerView.isNestedScrollingEnabled = false

        view.bookmarksRecyclerView.setHasFixedSize(true)
        view.bookmarksRecyclerView.setItemViewCacheSize(20)
        view.bookmarksRecyclerView.isDrawingCacheEnabled = true
        view.bookmarksRecyclerView.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH

        view.upcomingRecyclerView.isNestedScrollingEnabled = false
        view.upcomingRecyclerView.setItemViewCacheSize(20)
        view.upcomingRecyclerView.isDrawingCacheEnabled = true
        view.upcomingRecyclerView.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH

        val appDB = AppDB.getInstance(context)
        val upcomingAdapter = UpcomingAdapter(context)
        val bookmarksAdapter = BookmarksAdapter(context)
        view.upcomingRecyclerView.adapter = upcomingAdapter
        view.bookmarksRecyclerView.adapter = bookmarksAdapter

        upcomingAdapter.addEvents(appDB.getAllEvents()
                .filter { it.timestamp > System.currentTimeMillis()/1000L }
                .sortedBy { it.timestamp }
                .subList(0,10))



        if(appDB.getBookmarkedEvents()!!.isEmpty()) {
            Toast.makeText(context, "No Bookmarks!", Toast.LENGTH_SHORT).show()
            view.bookmarksRecyclerView.visibility = View.GONE
            view.noDataText.visibility = View.VISIBLE
        }
        else
            appDB.getBookmarkedEvents()?.let { bookmarksAdapter.addEvents(it.sortedBy { it.timestamp }) }
    }
}