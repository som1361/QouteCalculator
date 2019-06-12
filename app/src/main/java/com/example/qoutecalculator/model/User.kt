package com.example.qoutecalculator.model

data class User(var name: String?, var mobile: String?, var email: String?, var amount: Int?, var term: Int? ){
    var id = 0
    constructor() : this(null, null, null, null, null)
}