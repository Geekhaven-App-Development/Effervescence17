package org.effervescence.app17.models

/**
 * Created by akshat on 28/9/17.
 */

data class Sponsor(
        var id: Long = 0,
        var name: String = "",
        var imageUrl: String = "",
        var categories: List<String> = emptyList(),
        var website: String = "",
        var priority: Int = -1
)