package com.example.qoutecalculator.application

import android.app.Application
import com.example.qoutecalculator.DI.AppComponent
import com.example.qoutecalculator.DI.AppModule
import com.example.qoutecalculator.DI.DaggerAppComponent

class QouteApplication : Application() {

    lateinit var qouteComponent: AppComponent

    private fun initDagger(app: QouteApplication): AppComponent =
        DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .build()

    override fun onCreate() {
        super.onCreate()
        qouteComponent = initDagger(this)

    }
}