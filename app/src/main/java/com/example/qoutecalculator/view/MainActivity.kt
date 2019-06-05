package com.example.qoutecalculator.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import com.example.qoutecalculator.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.authentication_dialog.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadView()
        respondToClicks()
    }

    private fun respondToClicks() {
      calcQouteButton.setOnClickListener { showAuthenticationDialog() }
    }

    private fun showAuthenticationDialog() {
val mAuthDialog = LayoutInflater.from(this).inflate(R.layout.authentication_dialog, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mAuthDialog)
        val mAlertDialog = mBuilder.show()

        application_btn.setOnClickListener {

        }

        login_btn.setOnClickListener {
            
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

