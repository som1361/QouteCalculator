package com.example.qoutecalculator.viewmodel

import com.example.qoutecalculator.model.User
import com.example.qoutecalculator.repository.AuthRepository
import com.example.qoutecalculator.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.CompletableSubject
import io.reactivex.subjects.PublishSubject

class MainViewModel() {
    private lateinit var userRepository: UserRepository
    private lateinit var authRepository: AuthRepository
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    lateinit var getUserObservable: PublishSubject<User>
    lateinit var getUserErrorObservable: PublishSubject<Exception>
    lateinit var addUserObservable: CompletableSubject
    lateinit var addUserErrorObservable: PublishSubject<Exception>
    lateinit var replaceUserObservable: CompletableSubject
    lateinit var replaceUserErrorObservable: PublishSubject<Exception>
    lateinit var updateUserObservable: CompletableSubject
    lateinit var updateUserErrorObservable: PublishSubject<Exception>

    constructor(mAuthRepository: AuthRepository, mUserRepository: UserRepository) : this() {
        authRepository = mAuthRepository
        userRepository = mUserRepository
        getUserObservable = PublishSubject.create()
        getUserErrorObservable = PublishSubject.create()
        addUserObservable = CompletableSubject.create()
        addUserErrorObservable = PublishSubject.create()
        replaceUserObservable = CompletableSubject.create()
        replaceUserErrorObservable = PublishSubject.create()
        updateUserObservable = CompletableSubject.create()
        updateUserErrorObservable = PublishSubject.create()
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

    fun addUser(user: User) {
        val disposable = userRepository.addUser(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableCompletableObserver() {
                override fun onComplete() {
                    addUserObservable.onComplete()
                }

                override fun onError(e: Throwable) {
                    addUserErrorObservable.onNext(e as Exception)
                }
            })
        compositeDisposable.add(disposable)
    }

    fun getUser() {
        val disposable = userRepository.getUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<User>() {
                override fun onError(e: Throwable) {
                    getUserErrorObservable.onNext(e as Exception)
                }

                override fun onSuccess(t: User) {
                    getUserObservable.onNext(t)
                }
            })
        compositeDisposable.add(disposable)
    }

    fun updateUser(user: User) {
        val disposable = userRepository.updateUser(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableCompletableObserver() {
                override fun onComplete() {
                    updateUserObservable.onComplete()
                }

                override fun onError(e: Throwable) {
                    addUserErrorObservable.onNext(e as Exception)
                }
            })
        compositeDisposable.add(disposable)
    }

    fun replaceUser(user: User) {
        val disposable = userRepository.addUser(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableCompletableObserver() {
                override fun onComplete() {
                    replaceUserObservable.onComplete()
                }

                override fun onError(e: Throwable) {
                    replaceUserErrorObservable.onNext(e as Exception)
                }
            })
        compositeDisposable.add(disposable)
    }

}