package org.effervescence.app17.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.fragment_updates.view.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.effervescence.app17.R
import org.effervescence.app17.models.Notification
import org.effervescence.app17.recyclerview.adapters.UpdatesAdapter
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by sashank on 1/10/17.
 */

class UpdatesFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_updates, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshAdapter(view)

        view.refresh.setColorSchemeResources(R.color.colorAccent)

        view.refresh.setOnRefreshListener {
            refreshAdapter(view)
            view.refresh.isRefreshing = false
        }
    }

    // TODO: FIX IT. Asynchrony issue
    private fun refreshAdapter(view: View){
        val list = fetchLatestData()

        if(list!= null) {
            val updatesAdapter = UpdatesAdapter(activity, list)
            val updatesRecyclerView = view.updates_list
            updatesRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
            updatesRecyclerView.adapter = updatesAdapter
        }
        else
            Toast.makeText(context,"No data available",Toast.LENGTH_SHORT).show()

    }

    private fun fetchLatestData(): Array<Notification>? {
        var list: Array<Notification>? = null
        doAsync {
            val client = OkHttpClient()
            val request = Request.Builder()
                    .url("https://effervescence-17.firebaseio.com/notifications.json")
                    .build()
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                list = Moshi.Builder()
                        .build()
                        .adapter<Array<Notification>>(Array<Notification>::class.java)
                        .fromJson(response.body()?.string())

            }
        }
        return list
    }
}