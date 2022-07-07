package com.example.homework_recyclerview.di

import com.example.homework_recyclerview.data.CurrencyRepository
import com.example.homework_recyclerview.presentation.fragments.converter.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {

    factory { CurrencyRepository(get())}
    viewModel{ MainViewModel(get())}
}