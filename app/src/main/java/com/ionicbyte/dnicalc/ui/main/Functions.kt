package com.ionicbyte.dnicalc.ui.main

class Functions {
    companion object {
        fun calculateDNI(dni: Int): Int {
            return "TRWAGMYFPDXBNJZSQVHLCKET"[dni % 23].toInt()
        }
    }
}