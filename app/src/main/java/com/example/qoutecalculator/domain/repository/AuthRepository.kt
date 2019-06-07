package com.example.qoutecalculator.domain.repository

import io.reactivex.Completable

interface AuthRepository {
    fun isAuthenticated(): Boolean
    fun login(): Completable
    fun register(name: String, phone: String, email: String)
    fun saveUserData()
}