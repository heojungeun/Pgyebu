package com.example.pgyebu.view.pwsearch

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.pgyebu.R
import com.example.pgyebu.Utils.Util
import com.example.pgyebu.databinding.ActivityPasswordsearchBinding
import com.example.pgyebu.view.MainActivity

class PwSearchActivity: AppCompatActivity() {
    private val TAG = "PwSearchActivity"
    private val viewModel: PwSearchViewModel = PwSearchViewModel()
    private lateinit var binding: ActivityPasswordsearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_passwordsearch)

        setOnListener()

        binding.lifecycleOwner = this
        binding.pwSearchViewModel = viewModel
    }

    private fun setOnListener(){
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.layout.setOnTouchListener { view, motionEvent ->
            Util.hideKeyboard(this)
            return@setOnTouchListener false
        }


//        viewModel.startActivityEvent.observe(this) {
//            startActivity(Intent(this@PwSearchActivity, MainActivity::class.java))
//        }
    }
}