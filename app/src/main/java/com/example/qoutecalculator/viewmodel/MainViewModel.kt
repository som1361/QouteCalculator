package com.example.qoutecalculator.viewmodel

import com.example.qoutecalculator.model.LoginModel

class MainViewModel() {
    private lateinit var loginModel: LoginModel

    constructor(mloginModel: LoginModel) : this() {
        loginModel = mloginModel
    }

    fun loginUser() {

    }

    fun registerUser() {
    }
}