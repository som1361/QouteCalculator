package com.example.qoutecalculator.repository

import com.example.qoutecalculator.model.User

interface UserRepository {
    fun getUser(): User
    fun addUser(user: User)
    fun updateUser(user:User)
}