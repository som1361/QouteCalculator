package com.example.qoutecalculator.viewmodel

import com.example.qoutecalculator.model.User
import com.example.qoutecalculator.repository.AuthRepository
import com.example.qoutecalculator.repository.UserRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.CompletableSubject

class MainViewModel() {
    private lateinit var userRepository: UserRepository
    private lateinit var authRepository: AuthRepository
//    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
//    lateinit var resultObservable: CompletableSubject
//    lateinit var resultErrorObservable: CompletableSubject

    constructor(mAuthRepository: AuthRepository, mUserRepository: UserRepository) : this() {
        authRepository = mAuthRepository
        userRepository = mUserRepository
//        resultObservable = CompletableSubject.create()
//        resultErrorObservable = CompletableSubject.create()
    }

    fun authenticateUser() {
        val user = getUser()
//        val disposable: Disposable = firebaseAuthRepository.login()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeWith(object : DisposableCompletableObserver() {
//                override fun onComplete() {
//                }
//
//                override fun onError(e: Throwable) {
//                    resultErrorObservable.onError(e as AuthenticatorException)
//                }
//            })
//        compositeDisposable.add(disposable)
    }

    fun addUser(user: User) = userRepository.addUser(user)

    fun getUser() = userRepository.getUser()

    fun updateUser(user: User) = userRepository.updateUser(user)}