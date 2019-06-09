package com.example.qoutecalculator.utils

import java.text.DecimalFormat

class PMTPayment : Payment {
    override fun calculate(pv: Double, nper: Double, rate: Double): String {
        return when (rate) {
            0.0 -> (-pv / nper).toString()
            else -> {
                val rate = rate / 100.0 / 12.0
                val denominator = Math.pow(1 + rate, nper) - 1
                val value = pv * Math.pow(rate + 1, nper) * rate / (1 - Math.pow(rate + 1, nper))
                return String.format("%.2f", value)
            }
            //pv * Math.pow(rate + 1, nper) * rate / (1 - Math.pow(rate + 1, nper))
        }
    }
}

interface Payment {
    fun calculate(amount: Double, term: Double, rate: Double): String
}