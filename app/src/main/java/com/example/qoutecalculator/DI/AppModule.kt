package com.example.qoutecalculator.DI

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app:Application) {

    @Provides
    @Singleton
    fun providesContext(): Context = app
}