package org.effervescence.app17.utils

import android.content.Context
import net.rehacktive.waspdb.WaspDb
import net.rehacktive.waspdb.WaspFactory
import org.effervescence.app17.models.Event

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


    companion object : SingletonHolder<AppDB, Context>(::AppDB)

    fun getAllEvents() = eventHash.getAllValues<Event>()

    fun getEventsOfCategory(category: String) = eventHash.getAllValues<Event>().filter {
        it.categories.contains(category)
    }

    fun storeEvents(events: List<Event>) = events.forEach { eventHash.put(it.id,it) }
}