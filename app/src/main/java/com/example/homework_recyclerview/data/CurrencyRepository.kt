package com.example.homework_recyclerview.data

import com.example.homework_recyclerview.data.network.CurrencyService
import com.example.homework_recyclerview.domain.models.CurrencyRates
import retrofit2.Retrofit

class CurrencyRepository(private val retrofit: Retrofit) {

    private val api = retrofit.create(CurrencyService::class.java)

    suspend fun getCurrencyRates(): CurrencyRates{
        return api.getCurrencyRates()
    }
}