package com.example.qoutecalculator.repository

import com.example.qoutecalculator.model.User
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import io.reactivex.Completable
import io.reactivex.Single

class FirebaseAuthRepository : AuthRepository {

    override fun <T> authenticate(account: T): Single<User> = Single.create { emitter ->
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

    private fun autheticateWithGoogle(acct: GoogleSignInAccount): User {
        val currUser = FirebaseAuth.getInstance()!!.currentUser
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        currUser!!.linkWithCredential(credential)
        FirebaseAuth.getInstance()!!.signInWithCredential(credential)

        val user = User()
        user.email = currUser.email
        user.mobile = currUser.phoneNumber
        user.name = currUser.displayName

        return user
    }
}
