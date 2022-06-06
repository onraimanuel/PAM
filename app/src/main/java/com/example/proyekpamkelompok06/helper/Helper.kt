package com.example.proyekpamkelompok06.helper

import java.text.NumberFormat
import java.util.*

class Helper {
    fun gantiRupiah(string: Int):String{
        return NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(Integer.valueOf(string))
    }

    fun gantiRupiahInt(value: Int):String{
        return NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(value)
    }
}