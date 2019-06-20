package com.example.qoutecalculator.utils

import android.content.Context
import android.graphics.Color
import android.support.v4.util.PatternsCompat
import android.view.Gravity
import android.widget.Toast

fun String.isValidEmail(): Boolean
        = this.isNotEmpty() &&
        PatternsCompat.EMAIL_ADDRESS.matcher(this).matches()

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