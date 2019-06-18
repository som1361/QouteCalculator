package com.example.qoutecalculator.DI

import com.example.qoutecalculator.view.MainActivity
import com.example.qoutecalculator.view.QouteActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(target: MainActivity)
    fun inject(target: QouteActivity)
}