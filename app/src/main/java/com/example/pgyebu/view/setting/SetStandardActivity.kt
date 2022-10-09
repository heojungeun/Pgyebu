package com.example.pgyebu.view.setting

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.pgyebu.R
import com.example.pgyebu.databinding.ActivitySetstandardBinding
import com.example.pgyebu.view.setting.SetStandardViewModel

class SetStandardActivity: AppCompatActivity() {

    private val TAG = "SetStandardActivity"
    var viewModel: SetStandardViewModel = SetStandardViewModel()
    lateinit var binding: ActivitySetstandardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_setstandard
        )
        binding.lifecycleOwner = this

        setClickListeners()

        binding.setStandardViewModel = viewModel
    }

    private fun setClickListeners(){
        binding.btnBack.setOnClickListener {
            finish()
        }

        viewModel.startSettingActivityEvent.observe(this, Observer {
            finish()
            //startActivity(Intent(this@SetStandardActivity, SettingmenuActivity::class.java))
        })

    }

}