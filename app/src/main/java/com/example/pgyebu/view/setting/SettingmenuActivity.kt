package com.example.pgyebu.view.setting

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.pgyebu.R
import com.example.pgyebu.databinding.ActivitySettingmenuBinding
import com.example.pgyebu.view.home.HomeActivity

class SettingmenuActivity: AppCompatActivity() {

    var viewModel: SettingmenuViewModel = SettingmenuViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding: ActivitySettingmenuBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_settingmenu
        )

        setClickListeners()

        binding.settingmenuViewModel = viewModel
    }

    private fun setClickListeners(){
        viewModel.startSetStandardActivityEvent.observe(this, Observer {
            startActivity(Intent(this@SettingmenuActivity, SetStandardActivity::class.java))
        })

        viewModel.startMainScreenActivityEvent.observe(this, Observer {
            startActivity(Intent(this@SettingmenuActivity, MainScreenActivity::class.java))
        })

        viewModel.startHomeActivityEvent.observe(this, Observer {
            startActivity(Intent(this@SettingmenuActivity, HomeActivity::class.java))
        })
    }

}