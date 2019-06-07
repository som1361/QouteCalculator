package com.example.qoutecalculator.viewmodel

import android.accounts.AuthenticatorException
import com.example.qoutecalculator.domain.repository.FirebaseAuthRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.CompletableSubject

class MainViewModel() {
    private lateinit var firebaseAuthRepository: FirebaseAuthRepository
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    lateinit var resultObservable: CompletableSubject
    lateinit var resultErrorObservable: CompletableSubject

    constructor(mfirebaseAuthRepository: FirebaseAuthRepository) : this() {
        firebaseAuthRepository = mfirebaseAuthRepository
        resultObservable = CompletableSubject.create()
        resultErrorObservable = CompletableSubject.create()
    }

    fun loginUser() {
        val disposable: Disposable = firebaseAuthRepository.login()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableCompletableObserver() {
                override fun onComplete() {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onError(e: Throwable) {
                    resultErrorObservable.onError(e as AuthenticatorException)
                }
            })
        compositeDisposable.add(disposable)
    }

    fun registerUser(name: String, phone: String, email: String) {

    }
}