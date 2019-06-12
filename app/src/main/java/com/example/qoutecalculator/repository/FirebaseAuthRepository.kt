package com.example.qoutecalculator.repository

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import io.reactivex.Completable

class FirebaseAuthRepository : AuthRepository {

    override fun <T> authenticate(account: T): Completable = Completable.fromCallable {
        if (account is GoogleSignInAccount)
            autheticateWithGoogle(account)
    }

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

    private fun autheticateWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        FirebaseAuth.getInstance()!!.currentUser!!.linkWithCredential(credential)
        FirebaseAuth.getInstance()!!.signInWithCredential(credential)
    }
}
