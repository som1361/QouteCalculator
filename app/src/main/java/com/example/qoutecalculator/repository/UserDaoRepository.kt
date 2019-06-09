package com.example.qoutecalculator.repository

import com.example.qoutecalculator.model.User
import com.example.qoutecalculator.model.UserDao
import io.reactivex.Completable
import io.reactivex.Single


class UserDaoRepository(private val userDao: UserDao): UserRepository{

    override fun getUser() : Single<User> = Single.fromCallable({userDao.getUser()})

    override fun addUser(user:User): Completable = Completable.fromCallable{userDao.addUser(user)}

    override fun updateUser(user:User): Completable = Completable.fromCallable{userDao.updateUser(user)}

    override fun replaceUser(user:User): Completable = Completable.fromCallable{userDao.replaceUser(user)}


    companion object {
        @Volatile
        private var instance: UserDaoRepository? = null

        fun getInstance(userDao: UserDao) = instance ?: synchronized(this) {
            instance ?: UserDaoRepository(userDao).also { instance = it }
        }
    }

}