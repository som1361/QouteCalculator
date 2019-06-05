package com.example.qoutecalculator.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.qoutecalculator.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadView()
        respondToClicks()
    }

    private fun respondToClicks() {

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

