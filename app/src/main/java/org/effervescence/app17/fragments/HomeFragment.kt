package org.effervescence.app17.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.effervescence.app17.R
import org.effervescence.app17.adapters.BookmarksAdapter
import org.effervescence.app17.adapters.UpcomingAdapter
import org.effervescence.app17.utils.AppDB

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
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //view.header_anim.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.bg2))
        //(activity as AppCompatActivity).setSupportActionBar(view.toolbar)

        view.upcomingRecyclerView.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false)

        val layoutManager = LinearLayoutManager(context)
        layoutManager.isAutoMeasureEnabled = true

        view.bookmarksRecyclerView.layoutManager = LinearLayoutManager(context)
        view.bookmarksRecyclerView.isNestedScrollingEnabled = false

        val appDB = AppDB.getInstance(context)
        val upcomingAdapter = UpcomingAdapter(context)
        val bookmarksAdapter = BookmarksAdapter(context)
        view.upcomingRecyclerView.adapter = upcomingAdapter
        view.bookmarksRecyclerView.adapter = bookmarksAdapter
        upcomingAdapter.addEvents(appDB.getAllEvents())

        repeat(2,{ bookmarksAdapter.addEvents(appDB.getAllEvents())})
        repeat(2,{ upcomingAdapter.addEvents(appDB.getAllEvents())})
    }
}