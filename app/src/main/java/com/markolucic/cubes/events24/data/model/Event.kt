package com.markolucic.cubes.events24.data.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp
import java.util.*

@Entity
class Event(
    @PrimaryKey
    @NonNull
    var id: String = "",

    @ColumnInfo
    var title: String = "",

    @ColumnInfo
    var date: String = "",

    @Ignore
    var time: String = "",

    @ColumnInfo
    var type: String = "",

    @ColumnInfo
    var imageBig: String = "",

    @Ignore
    var imageSmall: String = "",

    @Ignore
    var location: String = "",

    @Ignore
    var author: String = "",

    @Ignore
    var dateOfCreation: Date = Date(),

    @Ignore
    var clickCounter: Long = 0,

    @Ignore

    var tickets: ArrayList<Ticket> = ArrayList(),

    @Ignore
    var aboutArtist: AboutArtist = AboutArtist(),

    @Ignore
    var newsList: ArrayList<News> = ArrayList(),

    @Ignore
    var pictures: ArrayList<String> = ArrayList(),
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

        //PICTURES

        if (map.containsKey("pictures")) {
            this.pictures = map["pictures"] as ArrayList<String>
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