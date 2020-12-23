package com.newproject.apputils



class Spinner {

    var ID: String= ""
    var title: String
    var tzId: String = ""
    var duration: Long = 0
    var price: Double = 0.toDouble()

    var isSelected: Boolean = false

    constructor(ID: String, title: String) {
        this.ID = ID
        this.title = title
    }

    constructor(ID: String, title: String, tzId: String) {
        this.ID = ID
        this.title = title
        this.tzId = tzId
    }

    constructor(ID: String, title: String, duration: Long, price: Double) {
        this.ID = ID
        this.title = title
        this.duration = duration
        this.price = price
    }

    constructor(title: String) {
        this.title = title
    }

    fun setSelected(isSelected: Boolean): Spinner {
        this.isSelected = isSelected
        return this
    }
}