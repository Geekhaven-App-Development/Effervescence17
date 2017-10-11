package org.effervescence.app17.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.effervescence.app17.R
import org.effervescence.app17.recyclerview.adapters.BookmarksAdapter
import org.effervescence.app17.recyclerview.adapters.UpcomingAdapter
import org.effervescence.app17.utils.AppDB

/**
 * Created by betterclever on 16/09/17.
 */

class HomeFragment : Fragment() {

    private lateinit var appDB: AppDB

    companion object {
        fun newInstance(): HomeFragment {
            val args = Bundle()
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appDB = AppDB.getInstance(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        upcomingRecyclerView.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false)

        val layoutManager = LinearLayoutManager(context)
        layoutManager.isAutoMeasureEnabled = true

        bookmarksRecyclerView.layoutManager = LinearLayoutManager(context)
        bookmarksRecyclerView.isNestedScrollingEnabled = false

        bookmarksRecyclerView.setHasFixedSize(true)
        bookmarksRecyclerView.setItemViewCacheSize(20)
        bookmarksRecyclerView.isDrawingCacheEnabled = true
        bookmarksRecyclerView.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH

        upcomingRecyclerView.isNestedScrollingEnabled = false
        upcomingRecyclerView.setItemViewCacheSize(20)
        upcomingRecyclerView.isDrawingCacheEnabled = true
        upcomingRecyclerView.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH

        appDB = AppDB.getInstance(context)
        val upcomingAdapter = UpcomingAdapter(context)
        val bookmarksAdapter = BookmarksAdapter(context)
        upcomingRecyclerView.adapter = upcomingAdapter
        bookmarksRecyclerView.adapter = bookmarksAdapter

        upcomingAdapter.addEvents(appDB.getAllEvents()
                .filter { (it.timestamp - 5 * 60 * 60 - 30 * 60 ) > System.currentTimeMillis() / 1000L }
                .sortedBy { it.timestamp }
                .subList(0, 10))



        if (appDB.getBookmarkedEvents().isEmpty()) {
            bookmarksRecyclerView.visibility = View.GONE
            noDataText.visibility = View.VISIBLE
        } else {
            bookmarksRecyclerView.visibility = View.VISIBLE
            appDB.getBookmarkedEvents().let { bookmarksAdapter.addEvents(it.sortedBy { it.timestamp }) }
        }
    }

    override fun onResume() {
        super.onResume()

        if (appDB.getBookmarkedEvents().isEmpty()) {
            bookmarksRecyclerView.visibility = View.GONE
            noDataText.visibility = View.VISIBLE
        } else {
            noDataText.visibility = View.GONE
            bookmarksRecyclerView.visibility = View.VISIBLE

            (bookmarksRecyclerView.adapter as BookmarksAdapter).clearData()
            appDB.getBookmarkedEvents().let {
                (bookmarksRecyclerView.adapter as BookmarksAdapter)
                        .addEvents(it.sortedBy { it.timestamp })
            }
        }

    }
}