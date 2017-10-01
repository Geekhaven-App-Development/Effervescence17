package org.effervescence.app17.utils

import android.content.Context
import net.rehacktive.waspdb.WaspDb
import net.rehacktive.waspdb.WaspFactory
import org.effervescence.app17.models.Event
import org.effervescence.app17.models.Sponsor

/**
 * Created by betterclever on 16/09/17.
 */



class AppDB private constructor(context: Context) {
    private val waspDB: WaspDb = WaspFactory.openOrCreateDatabase(
            context.filesDir.path,
            "eventDB",
            "effervescence17")

    private val eventHash = waspDB.openOrCreateHash("events")
    private val teamHash = waspDB.openOrCreateHash("team")
    private val sponsorHash = waspDB.openOrCreateHash("sponsors")


    companion object : SingletonHolder<AppDB, Context>(::AppDB)

    fun getAllEvents(): MutableList<Event> = eventHash.getAllValues<Event>()

    fun getEventsOfCategory(category: String) = eventHash.getAllValues<Event>().filter {
        it.categories.contains(category)
    }

    fun getEventByID(id: Long): Event = eventHash.get<Event>(id)

    fun storeEvents(events: List<Event>) = events.forEach { eventHash.put(it.id,it) }

    fun getAllSponsors(): MutableList<Sponsor> = sponsorHash.getAllValues<Sponsor>()

    fun storeSponsors(sponsors: List<Sponsor>) = sponsors.forEach { sponsorHash.put(it.id,it) }
}