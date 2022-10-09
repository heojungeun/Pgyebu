package com.example.pgyebu.view.mypage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import com.example.pgyebu.R
import com.example.pgyebu.Utils.Util
import com.example.pgyebu.databinding.ActivityEdituserinfoBinding
import com.google.android.material.datepicker.DateValidatorPointBackward.before

class EditUserInfoActivity: AppCompatActivity() {

    private val TAG = "EditUserInfoActivity"
    private val viewModel: EditUserInfoViewModel = EditUserInfoViewModel()
    private lateinit var binding: ActivityEdituserinfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edituserinfo)

        setOnListener()

        binding.editUserInfoViewModel = viewModel
    }

    private fun setOnListener(){
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.layout.setOnTouchListener { view, motionEvent ->
            Util.hideKeyboard(this)
            return@setOnTouchListener false
        }

        viewModel.startMyPageActivityEvent.observe(this) {
            finish()
        }

        binding.edtNewName.doAfterTextChanged { text ->
            println("binding.edtNewName.doAfterTextChanged: $text")
            isEdtTextNullorEmpty(name = text.toString())
        }

        binding.edtNewEmail.doAfterTextChanged { text ->
            println("binding.edtNewEmail.doAfterTextChanged: $text")
            isEdtTextNullorEmpty(email = text.toString())
        }

        binding.edtNewBirthdate.doAfterTextChanged { text ->
            println("binding.edtNewBirthdate.doAfterTextChanged: $text")
            isEdtTextNullorEmpty(birth = text.toString())
            //addDash(text, before)
        }
    }

    private fun isEdtTextNullorEmpty(name: String?=null, email: String?=null, birth: String?=null){
        val nameCheck = name ?: binding.edtNewName.text
        val emailCheck = email ?: binding.edtNewEmail.text
        val birthCheck = birth ?: binding.edtNewBirthdate.text
        val check = !nameCheck.isNullOrEmpty() ||
                (!emailCheck.isNullOrEmpty() && emailCheck.contains("@") && emailCheck.contains(".")) ||
                (birthCheck.count() == 8)
        binding.btnSavePw.isEnabled = check
    }

}