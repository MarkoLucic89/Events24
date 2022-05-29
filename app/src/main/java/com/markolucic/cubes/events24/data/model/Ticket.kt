package com.markolucic.cubes.events24.data.model

class Ticket(
    var title: String = "",
    var price: String = ""
) {

    constructor(map: Map<String, String>) : this() {
        this.title = map["title"]!!
        this.price = map["price"]!!
    }

}