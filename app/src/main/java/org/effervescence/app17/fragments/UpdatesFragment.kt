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

    private lateinit var updatesAdapter : UpdatesAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_updates, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshAdapter(view)

        refresh.setColorSchemeResources(R.color.colorAccent)

        refresh.setOnRefreshListener {
            refreshAdapter(view)
            refresh.isRefreshing = false
        }

        updatesAdapter = UpdatesAdapter()
        updatesRV.adapter = updatesAdapter
        updatesRV.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)

    }


    private fun refreshAdapter(view: View){
        noNotifsTV.visibility = View.GONE
        fetchLatestData(view)
    }

    private fun fetchLatestData(view: View){
        doAsync {
            val client = OkHttpClient()
            val request = Request.Builder()
                    .url("https://effervescence-17.firebaseio.com/notifications.json")
                    .build()
            val response = client.newCall(request).execute()
            try {
                if (response.isSuccessful) {
                    val updatesList: ArrayList<Notification> = ArrayList()
                    val body = JSONObject(response.body()?.string())
                    val keys = body.keys()

                    while (keys.hasNext()) {
                        val key = keys.next().toString()
                        val childObj = body.getJSONObject(key)
                        Log.d("akshat", childObj.toString())
                        if (childObj != null) {
                            val newNotification = Notification()
                            newNotification.description = childObj.getString("description")
                            newNotification.senderName = childObj.getString("senderName")
                            newNotification.timestamp = childObj.getLong("timestamp")
                            newNotification.title = childObj.getString("title")
                            updatesList.add(newNotification)
                        }
                    }
                    uiThread {
                        noNotifsTV?.visibility = View.GONE
                        updatesRV?.visibility = View.VISIBLE
                        updatesAdapter.updateData(updatesList)
                    }
                }
            }catch (e: Exception) {
                uiThread {
                    noNotifsTV?.visibility = View.VISIBLE
                    noNotifsTV?.text = "No Notifications !!"
                    updatesRV?.visibility = View.GONE
                }
            }


        }
    }
}