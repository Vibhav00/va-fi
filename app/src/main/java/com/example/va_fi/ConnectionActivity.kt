package com.example.va_fi

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.va_fi.databinding.ActivityConnectionBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ConnectionActivity : AppCompatActivity() {
    private lateinit var activityConnectionBinding: ActivityConnectionBinding
    private var url:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        activityConnectionBinding = ActivityConnectionBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(activityConnectionBinding.root)
        url = intent.getStringExtra("URL").toString()
        this.setCountDown()
        this.startService()
        this.onClickListener()
    }

    private fun onClickListener() {
        activityConnectionBinding.btnClose.setOnClickListener {
            stopService()
        }
    }


    private fun setCountDown() {
        var count = 2400
        GlobalScope.launch(Dispatchers.Main) {
            while (true) {

                if(count==2100)
                    count=2400
                activityConnectionBinding.tvCountdown.text = (count--).toString()
                delay(1000)
            }

        }
    }

    private fun startService() {
        val intent = Intent(this@ConnectionActivity, MyService::class.java)
        intent.putExtra("URL",url)
        startService(intent)

    }

    private fun stopService() {
        stopService(Intent(this@ConnectionActivity, MyService::class.java))
        finish()
    }


    @SuppressLint("JavascriptInterface")
    private  fun setWebView(){
//        val wv = activityConnectionBinding.wv
//        var url = "http://172.29.48.1:1000/keepalive?0e010b00030a04b2"
//        var settings = wv.settings
//        settings.javaScriptEnabled = true;
//        wv.loadUrl(url)
//
//        GlobalScope.launch {
//            Log.e("jsoup","stated ")
//            val j = Jsoup.connect("http://172.29.48.1:1000/keepalive?0e010b00030a04b2").get();
//            Log.e("jsoup","ended")
//            Log.e("vibhav",j.toString())
//        }
    }





}