package com.example.qoutecalculator.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.qoutecalculator.R
import com.example.qoutecalculator.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_qoute.*

class QouteActivity : AppCompatActivity() {
    private lateinit var mMainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        mMainViewModel = MainViewModel()
        super.onCreate(savedInstanceState)
        loadView()
        respondToClicks()
    }

    private fun respondToClicks() {
        info_edit_btn.setOnClickListener {
            name_editText.isEnabled = true
            phone_editText.isEnabled = true
            email_editText.isEnabled = true
            configInfoValidation()
        }

        apply_btn.setOnClickListener {
            mMainViewModel.registerUser(name_editText.text.toString(),
                phone_editText.text.toString(), email_editText.text.toString() )
        }
    }

    private fun configInfoValidation() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun loadView() {
        setContentView(R.layout.activity_qoute)
    }
}
