package com.example.qoutecalculator.utils

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
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
    User(this.child("name")as String, this.child("mobile")as String, this.child("email")as String )