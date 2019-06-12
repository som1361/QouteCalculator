package com.example.qoutecalculator.repository

import com.example.qoutecalculator.model.User
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface UserRepository {
    fun getUserById(): Single<User>
    fun saveUser(user: User): Completable
    fun checkIfUserExists(email: String): Single<Boolean>
}