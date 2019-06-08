package com.example.qoutecalculator.model

data class User( var name: String?, var phone: String?, var email: String?){
    var id = 0
    constructor() : this(null, null, null)
}