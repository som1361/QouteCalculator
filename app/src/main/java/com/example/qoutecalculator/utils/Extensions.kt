package com.example.qoutecalculator.utils

import android.content.Context
import android.graphics.Color
import android.util.Patterns
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.qoutecalculator.model.User
import com.google.firebase.database.DataSnapshot

fun View.hideKeyboard(): Boolean {
    try {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (ignored: RuntimeException) { }
    return false
}

fun DataSnapshot.toUser(): User =
    User(this.child("name")as String,
        this.child("mobile")as String,
        this.child("email")as String,
        this.child("amount")as Int,
        this.child("term")as Int,
        this.child("type")as Int)

fun String.isValidEmail(): Boolean
        = this.isNotEmpty() &&
        Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun showSuccessMessage(context:Context, message: Int){
    val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
    toast.view.setBackgroundColor(Color.GRAY)
    toast.setGravity(Gravity.CENTER, 0, 0);
    toast.show()
}

fun showFailMessage(context:Context, message: Int){
    val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
    toast.view.setBackgroundColor(Color.RED)
    toast.setGravity(Gravity.CENTER, 0, 0);
    toast.show()
}