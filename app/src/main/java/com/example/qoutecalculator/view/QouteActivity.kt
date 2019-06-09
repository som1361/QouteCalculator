package com.example.qoutecalculator.view

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.qoutecalculator.R
import com.example.qoutecalculator.model.User
import com.example.qoutecalculator.model.UserDao
import com.example.qoutecalculator.repository.FirebaseAuthRepository
import com.example.qoutecalculator.repository.UserDaoRepository
import com.example.qoutecalculator.utils.hideKeyboard
import com.example.qoutecalculator.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_qoute.*

class QouteActivity : AppCompatActivity() {
    private lateinit var mMainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        mMainViewModel = MainViewModel(FirebaseAuthRepository(), UserDaoRepository(UserDao(this)))
        super.onCreate(savedInstanceState)
        loadView()
        respondToClicks()
        listenToObservables()
    }

    private fun listenToObservables() {
        mMainViewModel.addUserObservable.subscribe({
            hideProgressBar()
            val toast = Toast.makeText(this, "Your Application is successful.", Toast.LENGTH_LONG)
            toast.view.setBackgroundColor(Color.GREEN)
            toast.show()
//            val mBuilder = AlertDialog.Builder(this).setMessage("Your Application is successful.")
//            mBuilder.show()
        })

        mMainViewModel.addUserErrorObservable.subscribe({
            hideProgressBar()
            val toast = Toast.makeText(this, "Your Application is failed.", Toast.LENGTH_LONG)
            toast.view.setBackgroundColor(Color.RED)
            toast.show()
        })

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
          //  configInfoValidation()
        }

        apply_btn.setOnClickListener {
            mobile_editText.hideKeyboard()
            showProgressBar()
            val user = User(
                name_editText.text.toString(),
                mobile_editText.text.toString(), email_editText.text.toString()
            )
            mMainViewModel.addUser(user)
        }
    }

    private fun configInfoValidation() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun loadView() {
        setContentView(R.layout.activity_qoute)
    }
}
