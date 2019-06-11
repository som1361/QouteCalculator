package com.example.qoutecalculator.view

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.qoutecalculator.R
import com.example.qoutecalculator.model.User
import com.example.qoutecalculator.repository.FirebaseAuthRepository
import com.example.qoutecalculator.repository.FirebaseUserRepository
import com.example.qoutecalculator.utils.PMTPayment
import com.example.qoutecalculator.utils.hideKeyboard
import com.example.qoutecalculator.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_qoute.*

class QouteActivity : AppCompatActivity() {
    private lateinit var mMainViewModel: MainViewModel
    private var isAuth: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        mMainViewModel = MainViewModel(FirebaseAuthRepository(), FirebaseUserRepository())
        super.onCreate(savedInstanceState)
        loadView()
        respondToClicks()
        listenToObservables()
    }

    private fun listenToObservables() {
        mMainViewModel.saveUserObservable.subscribe({
            hideProgressBar()
            val toast = Toast.makeText(this, "Your Application is successful.", Toast.LENGTH_LONG)
            toast.view.setBackgroundColor(Color.GREEN)
            toast.show()
//            val mBuilder = AlertDialog.Builder(this).setMessage("Your Application is successful.")
//            mBuilder.show()
        })

        mMainViewModel.saveUserErrorObservable.subscribe({
            hideProgressBar()
            val toast = Toast.makeText(this, "Your Application is failed.", Toast.LENGTH_LONG)
            toast.view.setBackgroundColor(Color.RED)
            toast.show()
        })

        mMainViewModel.getUserObservable.subscribe({
            hideProgressBar()
            if (it != null) {
                showUserInfo(it)
            }
        })
    }

    private fun showUserInfo(user: User) {
        name_editText.setText(user.name)
        mobile_editText.setText(user.phone)
        email_editText.setText(user.email)
        name_editText.isEnabled = false
        mobile_editText.isEnabled = false
        email_editText.isEnabled = false
    }

    fun showProgressBar() {
        qoute_activity_progress_bar.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        qoute_activity_progress_bar.visibility = View.GONE
    }

    private fun respondToClicks() {
        info_edit.setOnClickListener {
            name_editText.isEnabled = true
            mobile_editText.isEnabled = true
            email_editText.isEnabled = true
        }

        finance_details_edit.setOnClickListener {
            onBackPressed()
        }

        apply_btn.setOnClickListener {
            mobile_editText.hideKeyboard()
            showProgressBar()
            val user = User(
                name_editText.text.toString(),
                mobile_editText.text.toString(), email_editText.text.toString()
            )
            if (isAuth == false)
                mMainViewModel.createUser(user)
        }
    }

    private fun loadView() {
        setContentView(R.layout.activity_qoute)
        val bundle = intent.extras
        val nper = bundle.getInt(Constants.NPER)
        val pv = bundle.getInt(Constants.PV)
        isAuth = bundle.getBoolean(Constants.AUTH)
      //  if (isAuth)
            getUserInfo()
        showPaymentDetails(nper, pv)
    }

    private fun getUserInfo() {
        showProgressBar()
        mMainViewModel.getUser()
    }

    private fun showPaymentDetails(nper: Int, pv: Int) {
        term_textview.text = "${nper.toString()} months"
        amount_textview.text = "$${pv.toString()}"
        val rate = R.string.interest_rate.toDouble()
        repayment_textview.text = PMTPayment().calculate(pv.toDouble(), nper.toDouble(), rate)
    }

    override fun onStop() {
        super.onStop()
        mMainViewModel.cancelNetworkConnections()
    }

    object Constants {
        const val AUTH = "auth"
        const val NPER = "nper"
        const val PV = "pv"
    }
}
