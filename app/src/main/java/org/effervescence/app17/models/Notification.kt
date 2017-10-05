package org.effervescence.app17.models

/**
 * Created by sashank on 1/10/17.
 */

data class Notification(
        var description: String = "",
        var senderName: String="",
        var timestamp: Long = 0,
        var title: String = ""
)