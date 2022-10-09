package com.example.pgyebu.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.databinding.DataBindingUtil
import com.example.pgyebu.databinding.ActivityMainBinding
import androidx.lifecycle.Observer
import com.example.pgyebu.MyApplication
import com.example.pgyebu.R
import com.example.pgyebu.Utils.Util
import com.example.pgyebu.entity.BasicResponse
import com.example.pgyebu.network.EcoEventObject
import com.example.pgyebu.view.home.HomeActivity
import com.example.pgyebu.view.pwsearch.PwSearchActivity
import com.example.pgyebu.view.signup.SignupActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {

    private var viewModel: MainViewModel = MainViewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        val isLogin = MyApplication.prefs.getString(Util.IS_LOGIN, Util.NOLOGIN)
        if (isLogin != Util.NOLOGIN){
            // 홈 화면으로 이동
            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
        }

        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )

        setOnListener()

        binding.mainViewModel = viewModel
    }

    private fun setOnListener(){
        binding.layout.setOnTouchListener { view, motionEvent ->
            Util.hideKeyboard(this)
            return@setOnTouchListener false
        }

        viewModel.startSignUpActivityEvent.observe(this, Observer {
            startActivity(Intent(this@MainActivity, SignupActivity::class.java))
        })

        viewModel.startLogInActivityEvent.observe(this, Observer {
            checkAll()
        })

        viewModel.startPwSearchActivityEvent.observe(this) {
            startActivity(Intent(this@MainActivity, PwSearchActivity::class.java))
        }
    }

    private fun checkAll(){
        if (viewModel.input_id.value.isNullOrEmpty() || viewModel.input_pw.value.isNullOrEmpty()) {
            println("아이디나 비밀번호를 입력하지 않았습니다.")
            Toast.makeText(this@MainActivity,"아이디나 비밀번호를 입력하지 않았습니다.", Toast.LENGTH_SHORT).show()
        } else {
            login()
        }
    }

    private fun login(){
        val userid = binding.editId.text.toString()
        val pw = binding.editPw.text.toString()
        val body = HashMap<String, String>()
        body.put("userId", userid)
        body.put("password", pw)
        val call = EcoEventObject.getRetrofitService
        call.login(body).enqueue(object: Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.body()?.status != "OK") {
                    println("Response: ${response.body()!!.data} | ${response.body()!!.message}")
                    Toast.makeText(this@MainActivity,"잘못된 아이디나 비밀번호입니다.", Toast.LENGTH_SHORT).show()
                }else {
                    // Myapplication에 아이디 저장
                    MyApplication.prefs.setString(Util.IS_LOGIN,userid)
                    // 홈 화면으로 이동
                    startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@MainActivity,"잘못된 아이디나 비밀번호입니다.", Toast.LENGTH_SHORT).show()
            }
        })

    }

    override fun onBackPressed() {
//        super.onBackPressed()
    }
}