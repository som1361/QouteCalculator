package com.example.qoutecalculator.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import com.example.qoutecalculator.model.User
import com.example.qoutecalculator.repository.FirebaseAuthRepository
import com.example.qoutecalculator.repository.FirebaseUserRepository
import com.example.qoutecalculator.utils.*
import com.example.qoutecalculator.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_qoute.*
import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.example.qoutecalculator.R


class QouteActivity : AppCompatActivity() {
    private lateinit var mMainViewModel: MainViewModel
    private var userState: Int = 0
    private var pv: Int = 0
    private var nper: Int = 0
    private lateinit var email: String

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
            showSuccessMessage(this, R.string.app_success)
        })

        mMainViewModel.saveUserErrorObservable.subscribe({
            hideProgressBar()
            showFailMessage(this, R.string.app_failed)
        })

        mMainViewModel.getUserByIdObservable.subscribe({
            hideProgressBar()
            if (it != null) {
                showUserInfo(it)
            }
        })

        mMainViewModel.getUserByIdErrorObservable.subscribe({
            hideProgressBar()
            showFailMessage(this, R.string.get_user_failed)
        })

        mMainViewModel.userExistObservable.subscribe({
            if (it == true && email != email_editText.text.toString()) {
                hideProgressBar()
                showFailMessage(this, R.string.email_exist_error)
            }
            else {
                val user = User(
                    name_editText.text.toString(),
                    mobile_editText.text.toString(), email_editText.text.toString()
                    , pv, nper
                )

                when (userState) {
                    Constants.NEW_USER -> mMainViewModel.createUser(user)
                    Constants.ANONYMOUS_USER -> mMainViewModel.saveUser(user)
                }
            }
        })

        mMainViewModel.userExistErrorObservable.subscribe({
            hideProgressBar()
            showFailMessage(this, R.string.check_email_failed)
        })
    }

    private fun showUserInfo(user: User) {
        name_editText.setText(user.name)
        mobile_editText.setText(user.mobile)
        email_editText.setText(user.email)
        email = user.email!!
        disableEditInfo()
    }

    private fun disableEditInfo() {
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
            enableEditInfo()
        }

qoute_layout.setOnTouchListener(object: View.OnTouchListener {
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        return true
    }
})



        finance_details_edit.setOnClickListener {
            onBackPressed()
        }

        apply_btn.setOnClickListener {
            mobile_editText.hideKeyboard()
            if (!isFormValid())
                showFailMessage(this, com.example.qoutecalculator.R.string.invalid_form)
            else mMainViewModel.checkIfUserExists(email_editText.text.toString())
        }
    }

    private fun isFormValid() = email_editText.text.toString().isValidEmail()

    private fun enableEditInfo() {
        name_editText.isEnabled = true
        mobile_editText.isEnabled = true
        email_editText.isEnabled = true
    }

    private fun loadView() {
        setContentView(R.layout.activity_qoute)
        val bundle = intent.extras
        nper = bundle.getInt(Constants.NPER)
        pv = bundle.getInt(Constants.PV)
        userState = bundle.getInt(Constants.USERSTATE)
        if (userState != Constants.NEW_USER)
            getUserInfo()
        else
            disableEditInfo()
        showPaymentDetails(nper, pv)
    }

    private fun getUserInfo() {
        showProgressBar()
        mMainViewModel.getUserById()
    }

    private fun showPaymentDetails(nper: Int, pv: Int) {
        term_textview.text = "${nper.toString()} months"
        amount_textview.text = "$${pv.toString()}"
        repayment_textview.text = mMainViewModel.calculatePayment(
            pv.toDouble(),
            nper.toDouble(),
            Constants.RATE
        )
    }

    override fun onStop() {
        super.onStop()
        mMainViewModel.cancelNetworkConnections()
    }

    object Constants {
        const val USERSTATE = "userstate"
        const val NPER = "nper"
        const val PV = "pv"
        const val RATE = 3.7
        const val NEW_USER = 0
        const val ANONYMOUS_USER = 1
        const val AUTHENTICATED_USER = 2
    }
}
