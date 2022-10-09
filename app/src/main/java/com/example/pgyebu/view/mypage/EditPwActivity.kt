package com.example.pgyebu.view.mypage

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import com.example.pgyebu.R
import com.example.pgyebu.Utils.Util
import com.example.pgyebu.databinding.ActivityEditpwBinding

class EditPwActivity: AppCompatActivity() {

    private val TAG = "EditPwActivity"
    private val viewModel: EditPwViewModel = EditPwViewModel()
    private lateinit var binding: ActivityEditpwBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_editpw)

        setOnListener()

        binding.editPwViewModel = viewModel
    }

    private fun setOnListener(){
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.layout.setOnTouchListener { view, motionEvent ->
            Util.hideKeyboard(this)
            return@setOnTouchListener false
        }

        binding.edtOriginPw.doAfterTextChanged { text ->
            println("binding.edtOriginPw.doAfterTextChanged: $text")
            isEdtTextNullorEmpty(origin = text.toString())
        }

        binding.edtNewPw.doAfterTextChanged { text ->
            println("binding.edtNewPw.doAfterTextChanged: $text")
            isEdtTextNullorEmpty(new = text.toString())
        }

        binding.edtNewPwConfirm.doAfterTextChanged { text ->
            println("binding.edtNewPwConfirm.doAfterTextChanged: $text")
            isEdtTextNullorEmpty(newConfirm = text.toString())
            //addDash(text, before)
        }

        viewModel.startMyPageActivityEvent.observe(this) {
            startActivity(Intent(this@EditPwActivity, MypagemenuActivity::class.java))
        }

        viewModel.ToastFailureEvent.observe(this){
            Toast.makeText(this, "잘못된 비밀번호입니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isEdtTextNullorEmpty(origin: String?=null, new: String?=null, newConfirm: String?=null){
        val originCheck = origin ?: binding.edtOriginPw.text.toString()
        val newCheck = new ?: binding.edtNewPw.text.toString()
        val newConfirmCheck = newConfirm ?: binding.edtNewPwConfirm.text.toString()
        println("$TAG: - originCheck: $originCheck\nnewCheck: $newCheck\nnewConfirmCheck: $newConfirmCheck")
        println("$TAG: - new == newconfirm? ${(newCheck == newConfirmCheck)}")
        val check = originCheck.isNotEmpty()
                        && newCheck.isNotEmpty()
                        && newConfirmCheck.isNotEmpty()
                        && (newCheck == newConfirmCheck)
        binding.btnSavePw.isEnabled = check
    }
}