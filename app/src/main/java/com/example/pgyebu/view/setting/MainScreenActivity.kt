package com.example.pgyebu.view.setting

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.pgyebu.R
import com.example.pgyebu.databinding.ActivityMainscreenBinding
import com.example.pgyebu.view.setting.MainScreenViewModel

class MainScreenActivity: AppCompatActivity() {

    var viewModel: MainScreenViewModel = MainScreenViewModel()
    lateinit var binding: ActivityMainscreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_mainscreen
        )
        binding.lifecycleOwner = this

        setClickListeners()

        binding.mainScreenViewModel = viewModel
    }

    private fun setClickListeners(){
        binding.btnBack.setOnClickListener {
            finish()
        }

        viewModel.startSettingActivityEvent.observe(this, Observer {
            finish()
            //startActivity(Intent(this@MainScreenActivity, SettingmenuActivity::class.java))
        })
    }

}