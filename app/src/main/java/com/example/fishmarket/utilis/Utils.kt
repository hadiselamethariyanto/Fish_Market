package com.example.fishmarket.utilis

import java.util.*

object Utils {

    private const val char = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"
    private const val randomStrengthLength = 12
    private var random = Random()

    fun getRandomString(): String {
        val randStr = StringBuffer()
        for (i in 0 until randomStrengthLength) {
            val number = getRandomNumber()
            val ch = char[number]
            randStr.append(ch)
        }
        return randStr.toString()
    }

    private fun getRandomNumber(): Int {
        val randomInt: Int = random.nextInt(char.length)
        return if (randomInt - 1 == -1) {
            randomInt
        } else {
            randomInt - 1
        }
    }
}