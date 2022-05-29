package com.markolucic.cubes.events24.data.model

import com.google.firebase.Timestamp
import java.util.*
import kotlin.collections.ArrayList

class Event(
    var id: String = "",
    var title: String = "",
    var date: String = "",
    var time: String = "",
    var type: String = "",
    var imageBig: String = "",
    var imageSmall: String = "",
    var location: String = "",
    var author: String = "",
    var dateOfCreation: Date = Date(),
    var clickCounter: Long = 0,
    var tickets: ArrayList<Ticket> = ArrayList(),
    var aboutArtist: AboutArtist = AboutArtist(),
    var newsList: ArrayList<News> = ArrayList(),
) {

    constructor(id: String, map: Map<String, Any>) : this(
        id = id,
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
    ) {


        //TICKETS

        if (map.containsKey("tickets")) {

            val maps: ArrayList<Map<String, String>> =
                map["tickets"] as ArrayList<Map<String, String>>

            for (map in maps) {
                this.tickets.add(Ticket(map))
            }
        }

        //ABOUT AUTHOR

        if (map.containsKey("aboutArtist")) {
            val map: Map<String, String> = map["aboutArtist"] as Map<String, String>
            this.aboutArtist = AboutArtist(map)
        }

        //NEWSLIST

        if (map.containsKey("news")) {
            val maps: ArrayList<Map<String, String>> =
                map["news"] as ArrayList<Map<String, String>>

            for (map in maps) {
                this.newsList.add(News(map))
            }
        }
    }

//    constructor(id: String, map: Map<String, Any>) : this(
//        id = id,
//        title = map["title"].toString(),
//        date = map["date"].toString(),
//        time = map["time"].toString(),
//        type = map["type"].toString(),
//        imageBig = map["imageBig"].toString(),
//        imageSmall = map["imageSmall"].toString(),
//        location = map["location"].toString(),
//        author = map["author"].toString(),
//        dateOfCreation = (map["dateOfCreation"] as Timestamp).toDate(),
//        clickCounter = map["clickCounter"] as Long
//    )

//    constructor(id: String, map: Map<String, Any>) : this( map) {
//        this.id = id
//    }

}