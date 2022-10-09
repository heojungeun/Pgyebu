package com.example.pgyebu.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pgyebu.MyApplication
import com.example.pgyebu.R
import com.example.pgyebu.Utils.Util

class SplashActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // 로그인 페이지 공사 중일 때 임시용
        //MyApplication.prefs.setString(Util.IS_LOGIN, "heo01")
        //MyApplication.prefs.setString(Util.IS_LOGIN, Util.NOLOGIN)

        try {
            Thread.sleep(1000)
        }catch (e:InterruptedException) {
            e.printStackTrace()
        }
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}