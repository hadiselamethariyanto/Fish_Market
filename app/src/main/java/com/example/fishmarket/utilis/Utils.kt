package com.example.fishmarket.utilis

import android.content.Context
import com.example.fishmarket.R
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
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

    fun formatDate(date: Long): String {
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return dateFormat.format(date)
    }

    fun formatTime(date:Long):String{
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return dateFormat.format(date)
    }

    fun formatNumberToRupiah(value: Int, context: Context): String {
        val nf: NumberFormat = NumberFormat.getNumberInstance(Locale.GERMAN)
        val decimalFormat = nf as DecimalFormat
        return context.getString(R.string.rupiah, decimalFormat.format(value))
    }

    fun formatDoubleToRupiah(value: Double, context: Context):String{
        val nf: NumberFormat = NumberFormat.getNumberInstance(Locale.GERMAN)
        val decimalFormat = nf as DecimalFormat
        return context.getString(R.string.rupiah, decimalFormat.format(value))
    }

    fun getEndOfDayTimeInMillis(timeInMillis: Long): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeInMillis
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.timeInMillis
    }

    fun String.capitalizeWords(): String = split(" ").map { it.capitalize() }.joinToString(" ")
}