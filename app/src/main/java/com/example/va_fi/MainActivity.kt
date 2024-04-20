package com.example.va_fi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.va_fi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)
        setOnclickListners()
    }

    fun setOnclickListners() {
        activityMainBinding.btnConnect.setOnClickListener {
            val intent = Intent(this, ConnectionActivity::class.java)
            startActivity(intent)
//            finish()
        }
    }
}