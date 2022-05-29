package com.markolucic.cubes.events24.data.model

class Media {
    var image: String = ""
    var clickUrl: String? = ""
    var type: String? = ""

    constructor(map: Map<String, String>) {
        image = map["image"]!!
        clickUrl = map["clickUrl"]!!
        type = map["type"]!!
    }
}