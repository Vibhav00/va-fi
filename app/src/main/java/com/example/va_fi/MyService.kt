package com.example.va_fi

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.webkit.WebSettings
import androidx.activity.viewModels
import com.example.va_fi.constants.Constants
import com.example.va_fi.databinding.BackgoundBrouserBinding
import com.example.va_fi.preferenceutils.PreferenceUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MyService : Service() {

    var url: String = ""
    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        set()
        if (intent != null) {
            url = intent.getStringExtra("url").toString()
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        Log.e("vibhav", "webpage is closed ")
        stopSelf()
        super.onDestroy()


    }


    @SuppressLint("JavascriptInterface")
    fun set() {
        val backgroundBrowserBinding = BackgoundBrouserBinding.inflate(LayoutInflater.from(this))
        val wv = backgroundBrowserBinding.wv
//        var url = "http://172.29.48.1:1000/keepalive?0f06040c080208be"
        var url = url
        var settings = wv.settings
        settings.loadWithOverviewMode = true
        settings.useWideViewPort = true
        settings.javaScriptEnabled = true
        settings.cacheMode = WebSettings.LOAD_NO_CACHE
        wv.loadUrl(url)

        GlobalScope.launch(Dispatchers.Main)
        {
            while (true) {
                Log.e("service_running", "webpage is reloaded")
                PreferenceUtils.getSharedPreferences(this@MyService)
                    .setLastActive(System.currentTimeMillis())
                delay(Constants.RELOAD_TIME)
                wv.reload()
            }

        }
    }


}