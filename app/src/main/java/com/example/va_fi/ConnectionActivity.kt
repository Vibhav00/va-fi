package com.example.va_fi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.va_fi.databinding.ActivityConnectionBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ConnectionActivity : AppCompatActivity() {
    private lateinit var activityConnectionBinding: ActivityConnectionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        activityConnectionBinding = ActivityConnectionBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(activityConnectionBinding.root)
        setCountDown()
        startService()
        onClickListners()
    }

    private fun onClickListners() {
        activityConnectionBinding.btnClose.setOnClickListener {
            stopService()
        }
    }

    private fun setCountDown() {
        var count = 2400
        GlobalScope.launch(Dispatchers.Main) {
            while (true) {
                activityConnectionBinding.tvCountdown.text = (count--).toString()
                delay(1000)
            }

        }
    }

    private fun startService() {
        startService(Intent(this@ConnectionActivity, MyService::class.java))
    }

    private fun stopService() {
        stopService(Intent(this@ConnectionActivity, MyService::class.java))
    }
}