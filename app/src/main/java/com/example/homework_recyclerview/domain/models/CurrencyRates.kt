package com.example.homework_recyclerview.domain.models

data class CurrencyRates(
    val base: String,
    val date: String,
    val rates: Map<String, Double>,
    val success: Boolean,
    val timestamp: Int
)