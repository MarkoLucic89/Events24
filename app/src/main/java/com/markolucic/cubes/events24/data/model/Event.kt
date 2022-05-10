package com.markolucic.cubes.events24.data.model

import com.google.firebase.Timestamp
import java.util.*

class Event(
    var title: String = "",
    var date: String = "",
    var time: String = "",
    var type: String = "",
    var imageBig: String = "",
    var imageSmall: String = "",
    var location: String = "",
    var author: String = "",
    var dateOfCreation: Date = Date(),
    var clickCounter:Long = 0
) {

    constructor(map: Map<String, Any>) : this(
        title = map["title"].toString(),
        date = map["date"].toString(),
        time = map["time"].toString(),
        type = map["type"].toString(),
        imageBig = map["imageBig"].toString(),
        imageSmall = map["imageSmall"].toString(),
        location = map["location"].toString(),
        author = map["author"].toString(),
        dateOfCreation = (map["dateOfCreation"] as Timestamp).toDate(),
        clickCounter = map["clickCounter"] as Long
    )



}