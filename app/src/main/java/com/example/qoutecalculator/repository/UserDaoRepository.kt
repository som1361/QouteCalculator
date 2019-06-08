package com.example.qoutecalculator.repository

import com.example.qoutecalculator.model.User
import com.example.qoutecalculator.model.UserDao

class UserDaoRepository(private val userDao: UserDao): UserRepository{
    override fun getUser() = userDao.getUser()

    override fun addUser(user:User)= userDao.addUser(user)

    override fun updateUser(user:User) = userDao.updateUser(user)

    companion object {
        @Volatile
        private var instance: UserDaoRepository? = null

        fun getInstance(userDao: UserDao) = instance ?: synchronized(this) {
            instance ?: UserDaoRepository(userDao).also { instance = it }
        }
    }

}