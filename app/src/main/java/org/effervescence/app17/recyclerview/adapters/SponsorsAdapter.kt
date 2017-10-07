package org.effervescence.app17.recyclerview.adapters

import android.content.Context
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.sponsor_container.view.*
import org.effervescence.app17.R
import org.effervescence.app17.models.Sponsor

/**
 * Created by akshat on 28/9/17.
 */
class SponsorsAdapter(private val sponsorsList: List<Sponsor>, val context: Context) : RecyclerView.Adapter<SponsorsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sponsor_container,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(sponsorsList[position])
    }

    override fun getItemCount(): Int {
        return sponsorsList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        fun bindItem(sponsor: Sponsor) {
            itemView.sponsor_category.text = sponsor.categories.joinToString(",")
            itemView.sponsor_name.text = sponsor.name
            Picasso.with(context).load(sponsor.imageUrl).into(itemView.sponsor_image)
            itemView.sponsorLayout.setOnClickListener ({
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                if(sponsor.website.isNotEmpty())
                    customTabsIntent.launchUrl(context, Uri.parse(sponsor.website))
            })
        }
    }

}