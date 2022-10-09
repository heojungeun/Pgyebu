package com.example.pgyebu.view.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.pgyebu.view.MainActivity
import com.example.pgyebu.R
import com.example.pgyebu.Utils.Util
import com.example.pgyebu.databinding.ActivitySignupBinding
import com.example.pgyebu.entity.BasicResponse
import com.example.pgyebu.entity.AnyResponse
import com.example.pgyebu.network.EcoEventObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity: AppCompatActivity() {

    var viewModel: SignUpViewModel = SignUpViewModel()
    lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_signup
        )

        setOnListener()

        binding.signUpViewModel = viewModel
    }

    private fun setOnListener(){
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.layout.setOnTouchListener { view, motionEvent ->
            Util.hideKeyboard(this)
            return@setOnTouchListener false
        }

        viewModel.startSignUpActivityEvent.observe(this, Observer {
            checkAll()
        })
    }

    private fun checkAll(){
        if (binding.editId.text.isNullOrEmpty() ||
                binding.editEmail.text.isNullOrEmpty() ||
                binding.editName.text.isNullOrEmpty() ||
                binding.editPw.text.isNullOrEmpty() ||
                binding.editPwConfirm.text.isNullOrEmpty()) {
                    Toast.makeText(this@SignupActivity, "모든 칸을 입력해주세요.", Toast.LENGTH_SHORT).show()
        } else if(binding.editPw.text.toString() != binding.editPwConfirm.text.toString()){
            Toast.makeText(this@SignupActivity, "비밀번호 확인이 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
        }
        else {
            val call = EcoEventObject.getRetrofitService
            val body = HashMap<String, String>()
            body["id"] = binding.editId.text.toString()
            body["password"] = binding.editPw.text.toString()
            body["name"] = binding.editName.text.toString()
            body["email"] = binding.editEmail.text.toString()
            call.signUp(body).enqueue(object: Callback<AnyResponse>{
                override fun onResponse(
                    call: Call<AnyResponse>,
                    response: Response<AnyResponse>
                ) {
                    if (response.body()!!.data.toString().isDigitsOnly()) {
                        println("Response: ${response.body()!!.data} | ${response.body()!!.message}")
                        Toast.makeText(this@SignupActivity,"회원가입 과정 진행 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    }else {
                        Toast.makeText(this@SignupActivity, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT)
                            .show()
                        startActivity(Intent(this@SignupActivity, MainActivity::class.java))
                    }
                }

                override fun onFailure(call: Call<AnyResponse>, t: Throwable) {
                    t.printStackTrace()
                }
            })

        }
    }

}