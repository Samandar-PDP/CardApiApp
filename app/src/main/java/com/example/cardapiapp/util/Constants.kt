package com.example.cardapiapp.util

import com.example.cardapiapp.R
import java.util.*
import kotlin.collections.ArrayList

object Constants {
    const val TABLE_NAME = "CardTable"
    const val DB_NAME = "Card.db"
    const val END_POINT = "cardlist"
    fun randomColor(): Int {
        val arrayColor = ArrayList<Int>()
        arrayColor.add(R.color.color1)
        arrayColor.add(R.color.color2)
        arrayColor.add(R.color.color3)
        arrayColor.add(R.color.color5)
        arrayColor.add(R.color.color7)
        arrayColor.add(R.color.color9)

        val random = Random()
        val randomColor = random.nextInt(arrayColor.size)
        return arrayColor[randomColor]
    }
}