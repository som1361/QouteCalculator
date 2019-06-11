package com.example.qoutecalculator.repository

import com.example.qoutecalculator.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.Completable
import io.reactivex.Single


class FirebaseUserRepository : UserRepository {
    private val db: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun getUser(): Single<User> = Single.create { emitter ->
        val dbRef = userDatabaseReference()
        dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(User::class.java)
                emitter.onSuccess(value!!)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })
    }

    override fun saveUser(user: User): Completable = Completable.fromCallable {
        val dbRef = userDatabaseReference()
        dbRef.child("name").setValue(user.name)
        dbRef.child("mobile").setValue(user.mobile)
        dbRef.child("email").setValue(user.email)
    }

    private fun userDatabaseReference() = db.getReference("users").child(auth.currentUser?.uid!!)
}


