package com.ionicbyte.dnicalc

import com.ionicbyte.dnicalc.ui.main.Functions
import org.junit.Test

import org.junit.Assert.*

class ExampleUnitTest {
    @Test
    fun dni_calc_is_correct() {
        assertEquals('Z', Functions.calculateDNI(12345678).toChar())
        assertFalse(Functions.calculateDNI(12345678).toChar() == 'A')
    }
}