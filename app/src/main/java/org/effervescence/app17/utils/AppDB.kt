package org.effervescence.app17.utils

import android.content.Context
import net.rehacktive.waspdb.WaspDb
import net.rehacktive.waspdb.WaspFactory
import org.effervescence.app17.models.Developer
import org.effervescence.app17.models.Event
import org.effervescence.app17.models.Person
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
    private val bookmarksHash = waspDB.openOrCreateHash("bookmarks")
    private val teamHash = waspDB.openOrCreateHash("team")
    private val developerHash = waspDB.openOrCreateHash("developer")
    private val sponsorHash = waspDB.openOrCreateHash("sponsors")

    companion object : SingletonHolder<AppDB, Context>(::AppDB)

    fun getAllEvents(): MutableList<Event> = eventHash.getAllValues<Event>()

    fun getAllTeamMembers(): MutableList<Person>? = teamHash.getAllValues<Person>()

    fun  getAllDeveloperMembers(): MutableList<Developer>? = developerHash.getAllValues<Developer>()

    fun getEventsOfCategory(category: String) = eventHash.getAllValues<Event>()
            .filter { it.categories.contains(category) }
            .sortedBy { it.timestamp }

    fun getBookmarkedEvents(): List<Event>? = bookmarksHash.getAllValues<Event>()

    fun addBookmark(id: Long): Boolean = bookmarksHash.put(id, getEventByID(id))

    fun isBookmarked(id: Long) = (bookmarksHash.get<Event>(id) != null)

    fun getEventByID(id: Long): Event = eventHash.get<Event>(id)

    fun storeEvents(events: List<Event>) = events.forEach { eventHash.put(it.id, it) }

    fun getAllSponsors(): MutableList<Sponsor> = sponsorHash.getAllValues<Sponsor>()

    fun storeSponsors(sponsors: List<Sponsor>) = sponsors.forEach { sponsorHash.put(it.id, it) }

    fun storeTeam(teamList: List<Person>) = teamList.forEach({ teamHash.put(it.id, it) })

}