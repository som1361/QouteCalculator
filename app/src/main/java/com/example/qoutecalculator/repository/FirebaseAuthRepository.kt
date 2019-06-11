package com.example.qoutecalculator.repository

import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Completable

class FirebaseAuthRepository : AuthRepository {
    override fun isAuthenticated() = FirebaseAuth.getInstance().currentUser != null

    override fun login(): Completable = Completable.create { emitter ->
        FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener { user ->
            if (user.isSuccessful) {
                emitter.onComplete()
            } else {
                emitter.onError(user.exception!!)
            }
        }
    }
}
