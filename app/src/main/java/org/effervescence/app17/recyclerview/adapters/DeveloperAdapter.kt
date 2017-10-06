package org.effervescence.app17.recyclerview.adapters

import android.content.Context
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.layout_developer_member.view.*
import org.effervescence.app17.R
import org.effervescence.app17.models.Developer
import org.effervescence.app17.utils.GlideApp
import java.util.ArrayList

/**
 * Created by sashank on 4/10/17.
 */

class DeveloperAdapter(val context: Context) : RecyclerView.Adapter<DeveloperAdapter.ViewHolder>() {

    private val developerList = ArrayList<Developer>()

    override fun getItemCount() = developerList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_developer_member, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(context, developerList[position])
    }

    fun addDeveloper(developerList: List<Developer>) {
        this.developerList.addAll(developerList)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(context: Context, developer: Developer) {
            itemView.nameTextView.text = developer.name
            itemView.positionTextView.text = developer.position
            GlideApp.with(context)
                    .load(developer.imageUrl)
                    .circleCrop()
                    .placeholder(R.drawable.ic_event)
                    .into(itemView.personImageView)
            itemView.floatingActionButton.setOnClickListener ({
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(context, Uri.parse(developer.gitHubLink))
            })
        }
    }
}