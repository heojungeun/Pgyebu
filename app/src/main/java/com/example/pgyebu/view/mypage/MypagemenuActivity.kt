package com.example.pgyebu.view.mypage

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.pgyebu.MyApplication
import com.example.pgyebu.view.MainActivity
import com.example.pgyebu.R
import com.example.pgyebu.Utils.Util
import com.example.pgyebu.databinding.ActivityMypagemenuBinding
import com.example.pgyebu.network.ApiService
import com.example.pgyebu.view.home.HomeActivity
import com.example.pgyebu.view.setting.MainScreenActivity
import com.example.pgyebu.view.setting.SetStandardActivity

class MypagemenuActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMypagemenuBinding
    var viewModel: MypagemenuViewModel = MypagemenuViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_mypagemenu
        )

        setClickListeners()

        binding.mypagemenuViewModel = viewModel
    }

    private fun setClickListeners(){
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnLogout.setOnClickListener {
            ApiService.logout {
                Toast.makeText(this@MypagemenuActivity, "로그아웃되었습니다.", Toast.LENGTH_SHORT).show()
                MyApplication.prefs.setString(Util.IS_LOGIN, Util.NOLOGIN)
                startActivity(Intent(this@MypagemenuActivity, MainActivity::class.java))
            }
        }

        viewModel.startEditPwActivityEvent.observe(this, Observer {
            startActivity(Intent(this@MypagemenuActivity, EditPwActivity::class.java))
        })

        viewModel.startEditUserInfoActivityEvent.observe(this, Observer {
            startActivity(Intent(this@MypagemenuActivity, EditUserInfoActivity::class.java))
        })

        viewModel.startSecessionActivityEvent.observe(this, Observer {
            startActivity(Intent(this@MypagemenuActivity, SecessionActivity::class.java))
        })

        viewModel.startHomeActivityEvent.observe(this, Observer {
            startActivity(Intent(this@MypagemenuActivity, HomeActivity::class.java))
        })
    }

}