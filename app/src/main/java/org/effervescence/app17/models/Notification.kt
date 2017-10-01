package org.effervescence.app17.models

/**
 * Created by sashank on 1/10/17.
 */

data class Notification(
        val eventId: Long = 0,
        val title: String = "",
        val description: String = "",
        val senderName: String="",
        val timestamp: Long = 0
)