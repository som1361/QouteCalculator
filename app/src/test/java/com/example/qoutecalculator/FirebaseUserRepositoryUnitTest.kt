package com.exallium.rxmvvmapp.data

import com.example.qoutecalculator.repository.FirebaseUserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Answers
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class FirebaseUserRepositoryUnitTest {

    companion object {
        const val USER_ID = "user_id"
    }

    lateinit var testSubject: FirebaseUserRepository

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    lateinit var database: FirebaseDatabase

    @Mock
    lateinit var auth: FirebaseAuth

    @Mock
    lateinit var user: FirebaseUser

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        whenever(user.uid).thenReturn(USER_ID)
        whenever(auth.currentUser).thenReturn(user)
        testSubject = FirebaseUserRepository(database, auth)
    }

    @Test
    fun getUserById_dispose_removeValueEventListener() {
        // WHEN
        val disposable = testSubject.getUserById().subscribe()
        disposable.dispose()

        // THEN
        verify(database.getReference("users")).child(USER_ID).removeEventListener(any<ValueEventListener>())
    }
}