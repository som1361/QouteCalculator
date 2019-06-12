package com.example.qoutecalculator.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import com.example.qoutecalculator.R
import com.example.qoutecalculator.repository.FirebaseAuthRepository
import com.example.qoutecalculator.repository.FirebaseUserRepository
import com.example.qoutecalculator.utils.showFailMessage
import com.example.qoutecalculator.utils.showSuccessMessage
import com.example.qoutecalculator.viewmodel.MainViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.authentication_dialog.view.*

class MainActivity : AppCompatActivity() {
    private var userState: Int = 0
    private lateinit var mMainViewModel: MainViewModel
    private var mAuth: FirebaseAuth? = null
    private var currentUser: FirebaseUser? = null

    val GOOGLE_SIGN_IN: Int = 1
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var mGoogleSignInOptions: GoogleSignInOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        mMainViewModel = MainViewModel(FirebaseAuthRepository(), FirebaseUserRepository())

        FirebaseApp.initializeApp(this)
        super.onCreate(savedInstanceState)
        loadView()
        listenToObservables()
        respondToClicks()
    }

    override fun onStart() {
        super.onStart()
        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth?.currentUser
    }

    private fun respondToClicks() {
        calcQouteButton.setOnClickListener {
            currentUser.let {
                if (it == null || it!!.isAnonymous) {
                    if (it == null) userState = QouteActivity.Constants.NEW_USER
                    else {userState = QouteActivity.Constants.ANONYMOUS_USER
                    }
                    calcQouteButton.setText(R.string.apply_now)
                    showAuthenticationDialog()
                } else {
                    userState = QouteActivity.Constants.AUTHENTICATED_USER
                    goToQouteActivity()
                }
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun listenToObservables() {
        mMainViewModel.authUserObservable.subscribe {
            userState = QouteActivity.Constants.AUTHENTICATED_USER
            goToQouteActivity()
        }
        mMainViewModel.authUserErrorObservable.subscribe( {
            showFailMessage(this, R.string.auth_user_failed)
        })

        mMainViewModel.saveUserObservable.subscribe({
            userState = QouteActivity.Constants.AUTHENTICATED_USER
            goToQouteActivity()
        })

        mMainViewModel.saveUserErrorObservable.subscribe({
            showSuccessMessage(this,R.string.save_auth_user_failed)
        })


    }

    private fun goToQouteActivity() {
        val bundle = Bundle()
        bundle.putInt(QouteActivity.Constants.USERSTATE, userState)
        bundle.putInt(QouteActivity.Constants.NPER, term_SeekBar.progress)
        bundle.putInt(QouteActivity.Constants.PV, amount_SeekBar.progress)
        val intent = Intent(this, QouteActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun showAuthenticationDialog() {
        val mAuthDialogView = LayoutInflater.from(this).inflate(R.layout.authentication_dialog, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mAuthDialogView)
        val mAuthDialog = mBuilder.show()

        mAuthDialogView.login_btn.setOnClickListener {
            mAuthDialog.dismiss()
            configureGoogleSignIn()
            signIn()
        }

        mAuthDialogView.application_btn.setOnClickListener {
            mAuthDialog.dismiss()
            goToQouteActivity()
        }


    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN && resultCode == Activity.RESULT_OK) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)
            mMainViewModel.authenticateUser(account)
        }
    }

    private fun loadView() {
        setContentView(R.layout.activity_main)
        configSeekBar()
    }

    private fun configSeekBar() {
        amount_SeekBar.setIndicatorTextFormat("$\${PROGRESS}")
        term_SeekBar.setIndicatorTextFormat("\${PROGRESS} months")
    }

    private fun configureGoogleSignIn() {

        mGoogleSignInOptions = GoogleSignInOptions.Builder(
            GoogleSignInOptions.DEFAULT_SIGN_IN
        ).requestIdToken(
            getString(R.string.server_client_id)
        ).requestEmail().build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions)

    }

    override fun onStop() {
        super.onStop()
        mMainViewModel.cancelNetworkConnections()
    }
}

