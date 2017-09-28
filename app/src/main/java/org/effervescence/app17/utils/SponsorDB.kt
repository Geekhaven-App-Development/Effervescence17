package org.effervescence.app17.utils

import android.content.Context
import net.rehacktive.waspdb.WaspDb
import net.rehacktive.waspdb.WaspFactory
import org.effervescence.app17.models.Sponsor

/**
 * Created by akshat on 28/9/17.
 */

class SponsorDB constructor(context: Context) {
    private val waspDB: WaspDb = WaspFactory.openOrCreateDatabase(
            context.filesDir.path,
            "sponsorDB",
            "effervescence17")

    private val hash = waspDB.openOrCreateHash("sponsors")

    companion object : SingletonHolder<SponsorDB, Context>(::SponsorDB)

    fun getAllSponsors() = hash.getAllValues<Sponsor>()

    fun storeSponsors(sponsors: List<Sponsor>) = sponsors.forEach { hash.put(it.id,it) }
}