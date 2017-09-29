package org.effervescence.app17.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.sponsor_container.view.*
import org.effervescence.app17.R
import org.effervescence.app17.models.Sponsor

/**
 * Created by akshat on 28/9/17.
 */
class SponsorsAdapter(val sponsorsList: List<Sponsor>, val context: Context) : RecyclerView.Adapter<SponsorsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sponsor_container,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var i = 0
        holder.name.text = sponsorsList[position].name
        var allCategories = ""
        while( i < sponsorsList[position].categories.size){
            if (allCategories.equals("")){
                allCategories += sponsorsList[position].categories[i]
            }
            else {
                allCategories += ", " + sponsorsList[position].categories[i]
            }
            i++
        }
        holder.category.text = allCategories
        //Picasso.with(context).load(sponsorsList[position].imageUrl).into(holder.image)
    }

    override fun getItemCount(): Int {
        return sponsorsList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var category: TextView
        var name: TextView
        var image: ImageView
        init {
            category = view.sponsor_category
            name = view.sponsor_name
            image = view.sponsor_image
        }
    }

}