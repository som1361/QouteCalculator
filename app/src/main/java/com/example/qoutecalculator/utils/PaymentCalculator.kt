package com.example.qoutecalculator.utils

class PMTPayment : Payment {
    override fun calculate(pv: Double, nper: Double, annualRate: Double): String {
        return when (annualRate) {
            0.0 -> (-pv / nper).toString()
            else -> {
                val monthlyRate = annualRate.div(100.0).div(12)
                val monthlyPayment = (monthlyRate * pv) / (1 - Math.pow(1 + monthlyRate, -nper))
                return String.format("%.2f", monthlyPayment / 4)
            }
        }
    }
}

interface Payment {
    fun calculate(amount: Double, term: Double, rate: Double): String
}