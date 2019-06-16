package com.example.qoutecalculator

import com.example.qoutecalculator.utils.isValidEmail
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class EmailValidatorTest {

    @Test
    fun emailValidator_CorrectEmailSimple_ReturnsTrue() {
        assertTrue("name@email.com".isValidEmail())
    }
    @Test
    fun emailValidator_correctEmailSubdomain_ReturnsTrue() {
     assertTrue("name@email.com.au".isValidEmail())
    }
    @Test
    fun emailValidator_incorrectEmailWithoutTLD_ReturnsFalse() {
        assertFalse("name@email".isValidEmail())
    }
    @Test
    fun emailValidator_incorrectEmailDoubleDots_ReturnsFalse() {
        assertFalse("name@email..com".isValidEmail())
    }
    @Test
    fun emailValidator_incorrectEmailNoUsername_ReturnsFalse() {
        assertFalse("@email.com".isValidEmail())
    }
    @Test
    fun emailValidator_incorrectEmailEmptyInput_ReturnsFalse() {
        assertFalse("".isValidEmail())
    }
}