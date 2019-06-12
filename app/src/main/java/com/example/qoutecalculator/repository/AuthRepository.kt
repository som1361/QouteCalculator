package com.example.qoutecalculator.repository

import com.example.qoutecalculator.model.User
import io.reactivex.Completable
import io.reactivex.Single

interface AuthRepository {
    fun isAuthenticated(): Boolean
    fun login(): Completable
    fun <T> authenticate(t: T): Single<User>
}