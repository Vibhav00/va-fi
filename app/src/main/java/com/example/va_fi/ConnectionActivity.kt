package com.example.va_fi

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.va_fi.databinding.ActivityConnectionBinding
import com.example.va_fi.preferenceutils.PreferenceUtils
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
                if(!checkIfWifiIsActive()){
                    Toast.makeText(this@ConnectionActivity, "Wifi Disconnected ", Toast.LENGTH_SHORT).show()
                    finish()
                    break
                }
            }

        }
    }

    private fun startService() {
        val intent = Intent(this@ConnectionActivity, MyService::class.java)
        intent.putExtra("URL",url)
        PreferenceUtils.getSharedPreferences(this@ConnectionActivity).setActive(true)
        startService(intent)

    }

    private fun stopService() {
        stopService(Intent(this@ConnectionActivity, MyService::class.java))
        PreferenceUtils.getSharedPreferences(this@ConnectionActivity).setActive(false)
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



    /** Function to Check if wifi is active **/
    private fun checkIfWifiIsActive(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.type == ConnectivityManager.TYPE_WIFI

    }


    /** Function to handle on back press  **/
    override fun onBackPressed() {
        super.onBackPressed()
        PreferenceUtils.getSharedPreferences(this@ConnectionActivity).setActive(false)
//        Toast.makeText(this,"disconnected lskdjf ", Toast.LENGTH_SHORT).show()
    }

}