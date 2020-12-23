package com.newproject.interfaces

import androidx.cardview.widget.CardView

interface CardAdapter {

    public var MAX_ELEVATION_FACTOR: Int
        get() = 8
        set(value) = TODO()

    fun getBaseElevation(): Float

    fun getCardViewAt(position: Int): CardView?

    fun getCount(): Int
}