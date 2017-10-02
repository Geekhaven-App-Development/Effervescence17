package org.effervescence.app17.models

/**
 * Created by odin on 2/10/17.
 */
data class Person(
        var id: Long = 0,
        var name: String = "",
        var imageUrl: String = "",
        var priority: Int = -1
)