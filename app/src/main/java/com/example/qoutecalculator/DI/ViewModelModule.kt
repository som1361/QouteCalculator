package com.example.qoutecalculator.DI

import com.example.qoutecalculator.repository.AuthRepository
import com.example.qoutecalculator.repository.FirebaseAuthRepository
import com.example.qoutecalculator.repository.FirebaseUserRepository
import com.example.qoutecalculator.repository.UserRepository
import com.example.qoutecalculator.viewmodel.MainViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelModule {

    @Provides
    @Singleton
    fun providesUserRepository(): UserRepository = FirebaseUserRepository()

    @Provides
    @Singleton
    fun providesAuthRepository(): AuthRepository = FirebaseAuthRepository()

    @Provides
    @Singleton
    fun providesMainViewModel(authRepo: AuthRepository, userRepo: UserRepository) = MainViewModel(authRepo, userRepo)
}