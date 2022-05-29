package com.markolucic.cubes.events24.data.model

class News(
    var imageUrl: String = "",
    var date: String = "",
    var title: String = "",
    var description: String = "",
) {

    constructor(map: Map<String, String>):this() {
            imageUrl = map["imageUrl"]!!
            date = map["date"]!!
            title = map["title"]!!
            description = map["description"]!!
    }
}