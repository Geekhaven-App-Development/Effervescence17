package org.effervescence.app17.models

import android.hardware.SensorAdditionalInfo

/**
 * Created by betterclever on 16/09/17.
 */
data class Event(
        val id: Long = 0,
        val name: String = "",
        val description: String = "",
        val location: String = "",
        val timestamp: Long = 0,
        val imageUrl: String = "",
        val categories: List<String> = emptyList(),
        val additionalInfo: List<String> = emptyList(),
        val facebookEventLink: String = "",
        val organizers: List<Organizer> = emptyList()
)

data class Organizer(
        val name: String = "",
        val phoneNumber: Long = 0
)

// https://api.myjson.com/bins/942p1
