package com.example.fishmarket.utilis

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
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

    fun formatTime(date: Long): String {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return dateFormat.format(date)
    }

    fun formatNumberToRupiah(value: Int, context: Context): String {
        val nf: NumberFormat = NumberFormat.getNumberInstance(Locale.GERMAN)
        val decimalFormat = nf as DecimalFormat
        return context.getString(R.string.rupiah, decimalFormat.format(value))
    }

    fun formatNumberThousand(value: Int): String {
        val nf: NumberFormat = NumberFormat.getNumberInstance(Locale.GERMAN)
        val decimalFormat = nf as DecimalFormat
        return decimalFormat.format(value)
    }

    fun formatDoubleToRupiah(value: Double, context: Context): String {
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

    fun getFirstOfDayTimeInMillis(timeInMillis: Long): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeInMillis
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    fun String.capitalizeWords(): String = split(" ").map { it.capitalize() }.joinToString(" ")

    fun showMessage(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun NavController.navigateSafe(
        @IdRes resId: Int,
        args: Bundle? = null,
        navOptions: NavOptions? = null,
        navExtras: Navigator.Extras? = null
    ) {
        val action = currentDestination?.getAction(resId) ?: graph.getAction(resId)
        if (action != null && currentDestination?.id != action.destinationId) {
            navigate(resId, args, navOptions, navExtras)
        }
    }
}