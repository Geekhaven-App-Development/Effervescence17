package org.effervescence.app17.rest

import org.effervescence.app17.models.Event
import retrofit2.Call

/**
 * Created by betterclever on 16/09/17.
 */

interface EventDataDownloader {
    fun fetchLatestData(): Call<List<Event>>
}