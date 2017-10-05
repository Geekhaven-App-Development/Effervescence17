package org.effervescence.app17.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_updates.*
import kotlinx.android.synthetic.main.fragment_updates.view.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.effervescence.app17.R
import org.effervescence.app17.models.Notification
import org.effervescence.app17.recyclerview.adapters.UpdatesAdapter
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject

/**
 * Created by sashank on 1/10/17.
 */

class UpdatesFragment: Fragment(){

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
        progressBar.visibility = View.VISIBLE
        updates_list.visibility = View.GONE
        updates.visibility = View.GONE
        fetchLatestData(view)
    }

    private fun fetchLatestData(view: View){
        doAsync {
            val client = OkHttpClient()
            val request = Request.Builder()
                    .url("https://effervescence-17.firebaseio.com/notifications.json")
                    .build()
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                var updatesList: ArrayList<Notification> = ArrayList()
                var body = JSONObject(response.body()?.string())
                if(body != null) {
                    var keys = body.keys()

                    while (keys.hasNext()) {
                        var key = keys.next().toString()
                        var childObj = body.getJSONObject(key)
                        if(childObj != null) {
                            var newNotification = Notification()
                            newNotification.description = childObj.getString("description")
                            newNotification.senderName = childObj.getString("senderName")
                            newNotification.timestamp = childObj.getLong("timestamp")
                            newNotification.title = childObj.getString("title")
                            updatesList.add(newNotification)
                        }

                    }
                    uiThread {
                        progressBar.visibility = View.GONE
                        updates.visibility = View.GONE
                        updates_list.visibility = View.VISIBLE
                        val updatesAdapter = UpdatesAdapter(activity,updatesList)
                        val updatesRecyclerView = view.updates_list
                        updatesRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
                        updatesRecyclerView.adapter = updatesAdapter
                    }
                } else {
                    uiThread {
                        progressBar.visibility = View.GONE
                        updates.visibility = View.VISIBLE
                        updates.text = "No Notification"
                        updates_list.visibility = View.GONE
                    }
                }
            }
            else{
                uiThread {
                    progressBar.visibility = View.GONE
                    updates.visibility = View.VISIBLE
                    updates.text = "Connectivity Problem"
                    updates_list.visibility = View.GONE
                }
            }

        }
    }
}