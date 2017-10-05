package org.effervescence.app17.recyclerview.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.layout_team_member.view.*
import org.effervescence.app17.R
import org.effervescence.app17.models.Person
import org.effervescence.app17.utils.GlideApp
import java.util.*
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v4.content.ContextCompat


/**
 * Created by betterclever on 17/09/17.
 */
class TeamAdapter(val context: Context) : RecyclerView.Adapter<TeamAdapter.ViewHolder>() {

    private val teamList = ArrayList<Person>()

    override fun getItemCount() = teamList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_team_member, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(context, teamList[position])
    }

    fun addTeam(teamList: List<Person>) {
        this.teamList.addAll(teamList)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(context: Context, person: Person) {
            itemView.nameTextView.text = person.name
            itemView.positionTextView.text = person.position
            GlideApp.with(context)
                    .load(person.imageUrl)
                    .circleCrop()
                    .placeholder(R.drawable.ic_event)
                    .into(itemView.personImageView)

            itemView.floatingActionButton.setOnClickListener ({
                val callNumber = person.contact
                val intent = Intent(Intent.ACTION_CALL)
                intent.data = Uri.parse("tel:" + callNumber)

                if(ContextCompat.checkSelfPermission(
                        context, android.Manifest.permission.CALL_PHONE )
                        == PackageManager.PERMISSION_GRANTED ) {
                    context.startActivity(intent)
                }
            })
        }
    }
}