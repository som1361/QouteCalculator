package com.example.qoutecalculator.repository

import com.example.qoutecalculator.model.User
import io.reactivex.Completable
import io.reactivex.Single

interface UserRepository {
    fun getUser(): Single<User>

    fun addUser(user: User): Completable

    fun updateUser(user:User): Completable
}