package com.markolucic.cubes.events24.data.model

class AboutArtist(
    var description: String = "",
    var imageUrl: String = "",
) {

    constructor(map: Map<String, String>) : this() {
        description = map["description"]!!
        imageUrl = map["imageUrl"]!!
    }

}