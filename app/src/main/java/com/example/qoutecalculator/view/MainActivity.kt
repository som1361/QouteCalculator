package com.example.qoutecalculator.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import com.example.qoutecalculator.R
import com.example.qoutecalculator.viewmodel.MainViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.authentication_dialog.view.*

class MainActivity : AppCompatActivity() {
    private lateinit var mMainViewModel: MainViewModel
    private var mAuth: FirebaseAuth? = null
    private var currentUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        mMainViewModel = MainViewModel()
        FirebaseApp.initializeApp(this)
        super.onCreate(savedInstanceState)
        loadView()
        respondToClicks()
    }

    override fun onStart() {
        super.onStart()
        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth?.currentUser
    }

    private fun respondToClicks() {
        calcQouteButton.setOnClickListener {
            //currentUser.let { if (it != null) goToQouteActivity() else showAuthenticationDialog() }
            showAuthenticationDialog()
        }
    }

    private fun goToQouteActivity() {
        val intent = Intent(this, QouteActivity::class.java)
        startActivity(intent)
    }

    private fun showAuthenticationDialog() {
        val mAuthDialogView = LayoutInflater.from(this).inflate(R.layout.authentication_dialog, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mAuthDialogView)
        val mAuthDialog = mBuilder.show()

        mAuthDialogView.login_btn.setOnClickListener {
            mAuthDialog.dismiss()
            mMainViewModel.loginUser()

        }

        mAuthDialogView.application_btn.setOnClickListener {
            mAuthDialog.dismiss()
            goToQouteActivity()
        }


    }

    private fun loadView() {
        setContentView(R.layout.activity_main)
        configSeekBar()
    }

    private fun configSeekBar() {
        salarySeekBar.setIndicatorTextFormat("$\${PROGRESS}")
        timeSeekBar.setIndicatorTextFormat("\${PROGRESS} months")
    }


}

