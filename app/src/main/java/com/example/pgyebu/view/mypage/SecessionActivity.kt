package com.example.pgyebu.view.mypage

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import com.example.pgyebu.MyApplication
import com.example.pgyebu.R
import com.example.pgyebu.Utils.Util
import com.example.pgyebu.databinding.ActivityEditpwBinding
import com.example.pgyebu.databinding.ActivitySecessionBinding
import com.example.pgyebu.view.MainActivity

class SecessionActivity: AppCompatActivity() {

    private val TAG = "SecessionActivity"
    private val viewModel: SecessionViewModel = SecessionViewModel()
    private lateinit var binding: ActivitySecessionBinding
    private val userId = MyApplication.prefs.getString(Util.IS_LOGIN, Util.NOLOGIN)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_secession)

        setOnListener()

        binding.secessionViewModel = viewModel
    }

    private fun setOnListener(){
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.layout.setOnTouchListener { view, motionEvent ->
            Util.hideKeyboard(this)
            return@setOnTouchListener false
        }

        binding.edtUserId.doOnTextChanged { text, start, before, count ->
            println("$TAG: - 현재 탈퇴 입력 텍스트는 ${binding.edtUserId.text}")
            binding.btnSavePw.isEnabled = binding.edtUserId.text.toString() == userId
        }

        viewModel.startMyPageActivityEvent.observe(this) {
            startActivity(Intent(this@SecessionActivity, MainActivity::class.java))
        }
    }

}