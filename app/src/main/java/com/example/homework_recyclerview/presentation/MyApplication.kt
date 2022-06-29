package com.example.homework_recyclerview.presentation

import android.app.Application
import com.example.convertor.di.mainModule
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin { module { mainModule } }
    }
}