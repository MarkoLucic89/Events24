package com.markolucic.cubes.events24.data.model

import com.google.firebase.Timestamp
import java.util.*

class Author(
    var name: String = "",
    var surname: String = "",
    var image: String = "",
) {

    constructor(map: Map<String, Any>) : this(
        map["name"].toString(),
        map["surname"].toString(),
        map["image"].toString()
    )



}