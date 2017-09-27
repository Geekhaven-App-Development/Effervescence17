package org.effervescence.app17.utils

import android.content.Context
import net.rehacktive.waspdb.WaspDb
import net.rehacktive.waspdb.WaspFactory
import org.effervescence.app17.models.Event

/**
 * Created by betterclever on 16/09/17.
 */



class EventDB private constructor(context: Context) {
    private val waspDB: WaspDb = WaspFactory.openOrCreateDatabase(
            context.filesDir.path,
            "eventDB",
            "effervescence17")

    private val hash = waspDB.openOrCreateHash("users")

    companion object : SingletonHolder<EventDB, Context>(::EventDB)

    fun getAllEvents() = hash.getAllValues<Event>()

    fun getEventsOfCategory(category: String) = hash.getAllValues<Event>().filter {
        it.categories.contains(category)
    }

    fun getEventsOfDay(day: Int) = hash.getAllValues<Event>().filter {
        it.days.contains(day)
    }

    fun storeEvents(events: List<Event>) = events.forEach { hash.put(it.id,it) }
}