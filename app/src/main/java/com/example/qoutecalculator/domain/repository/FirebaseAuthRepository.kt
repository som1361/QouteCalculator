package com.example.qoutecalculator.domain.repository

import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Completable

class FirebaseAuthRepository: AuthRepository {
    override fun isAuthenticated() = FirebaseAuth.getInstance().currentUser != null

    override fun login()= Completable.create { emitter ->
        FirebaseAuth.getInstance().createUserWithEmailAndPassword("", "")
    }

    override fun register(name: String, phone: String, email: String) {

    }

    override fun saveUserData() {
    }
}