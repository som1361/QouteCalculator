package com.example.qoutecalculator.viewmodel

import com.example.qoutecalculator.model.User
import com.example.qoutecalculator.repository.AuthRepository
import com.example.qoutecalculator.repository.UserRepository
import com.example.qoutecalculator.utils.PMTPayment
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
    lateinit var saveUserObservable: CompletableSubject
    lateinit var saveUserErrorObservable: PublishSubject<Exception>
    lateinit var authUserObservable: CompletableSubject
    lateinit var authUserErrorObservable: PublishSubject<Exception>

    constructor(mAuthRepository: AuthRepository, mUserRepository: UserRepository) : this() {
        authUserObservable = CompletableSubject.create()
        authUserErrorObservable = PublishSubject.create()
        authRepository = mAuthRepository
        userRepository = mUserRepository
        getUserObservable = PublishSubject.create()
        getUserErrorObservable = PublishSubject.create()
        saveUserObservable = CompletableSubject.create()
        saveUserErrorObservable = PublishSubject.create()
    }

    fun createUser(user: User) {
        val disposable = authRepository.login()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableCompletableObserver() {
                override fun onComplete() {
                    saveUser(user)
                }

                override fun onError(e: Throwable) {
                    saveUserErrorObservable.onNext(e as Exception)
                }
            })
        compositeDisposable.add(disposable)
    }

    fun saveUser(user: User) {
        val disposable = userRepository.saveUser(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableCompletableObserver() {
                override fun onComplete() {
                    saveUserObservable.onComplete()
                }

                override fun onError(e: Throwable) {
                    saveUserErrorObservable.onNext(e as Exception)
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


    fun cancelNetworkConnections() {
        compositeDisposable.clear()
    }

    fun calculatePayment(pv: Double, nper: Double, annualrate: Double): CharSequence? {
        return PMTPayment().calculate(pv, nper, annualrate)
    }

    fun <T> authenticateUser(account: T?) {
        val disposable = authRepository.authenticate(account)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<User>() {
                override fun onSuccess(user: User) {
                    saveUser(user)
                }

                override fun onError(e: Throwable) {
                    authUserErrorObservable.onNext(e as Exception)
                }
            })
        compositeDisposable.add(disposable)
    }

}