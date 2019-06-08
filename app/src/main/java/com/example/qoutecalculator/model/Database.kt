package com.example.qoutecalculator.model

import android.content.Context

class Database(context: Context) {
    var userDao = UserDao(context)
    companion object {
        @Volatile
        private var instance: Database? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: Database(context).also { instance = it }
        }
    }
}