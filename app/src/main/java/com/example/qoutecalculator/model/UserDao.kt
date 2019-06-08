package com.example.qoutecalculator.model

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

val DB_NAME = "Users"
val TABLE_NAME = "User"
var COL_ID = "id"
val COL_NAME = "name"
val COL_PHONE = "phone"
val COL_EMAIL = "email"


class UserDao(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable =
            "CREATE TABLE $TABLE_NAME($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_NAME VARCHAR(256), $COL_PHONE VARCHAR(256), $COL_EMAIL VARCHAR(256));"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun addUser(user: User) {
        val db = this.writableDatabase
        var contentValues = ContentValues()
        contentValues.put(COL_NAME, user.name)
        contentValues.put(COL_PHONE, user.phone)
        contentValues.put(COL_EMAIL, user.email)

        db.insert(TABLE_NAME, null, contentValues)
    }


    fun getUser(): User {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME;"
        val result = db.rawQuery(query, null)
        result.moveToFirst()
        var user = User()
        user.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
        user.name = result.getString(result.getColumnIndex(COL_NAME))
        user.phone = result.getString(result.getColumnIndex(COL_PHONE))
        user.email = result.getString(result.getColumnIndex(COL_EMAIL))

        result.close()
        db.close()
        return user
    }

    fun updateUser(user: User) {
       val db = this.writableDatabase
        var contentValues = ContentValues()
        contentValues.put(COL_NAME, user.name)
        contentValues.put(COL_PHONE, user.phone)
        contentValues.put(COL_EMAIL, user.email)

        db.update(TABLE_NAME, contentValues, null, null  )
    }



}