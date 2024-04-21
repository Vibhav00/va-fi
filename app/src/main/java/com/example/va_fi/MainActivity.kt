package com.example.va_fi

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.va_fi.databinding.ActivityMainBinding
import com.example.va_fi.databinding.AddLinkBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup


@AndroidEntryPoint
class MainActivity() : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)

        if (Build.VERSION.SDK_INT > 9) {
            val policy = ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
        setOnclickListners()
//        check()
    }

//    private fun check(){
//        mainViewModel.setWifi()
//        mainViewModel.getWifi().observe(
//this, Observer {
//             Toast.makeText(this@MainActivity,it.toString(),Toast.LENGTH_SHORT).show()
//            }
//        )
//    }

    private fun setOnclickListners() {
        activityMainBinding.btnConnect.setOnClickListener {
//            val intent = Intent(this, ConnectionActivity::class.java)
//            intent.putExtra("url",url)
//            startActivity(intent)
//            finish()
        }
        activityMainBinding.btnAdd.setOnClickListener {
            createInputDialog()
        }
    }


    private fun createInputDialog() {
        val addLinkBinding = AddLinkBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(this)
        builder.setView(addLinkBinding.root)
        val dialog = builder.create()
        dialog.show()

        addLinkBinding.apply {
            connectBtn.setOnClickListener {
                GlobalScope.launch(Dispatchers.Main) {
                    val text = addLinkBinding.nameEt.text.toString()
                    if(!validateInput(text)){
                        dialog.dismiss()
                        return@launch
                    }else if(!validatejsup(text)){
                        dialog.dismiss()
                        return@launch
                    }
                    val intent = Intent(this@MainActivity, ConnectionActivity::class.java)
                    intent.putExtra("url",text)
                    startActivity(intent)
                }

            }
            cancelBnt.setOnClickListener {
                dialog.dismiss()
            }
        }


    }

    private fun validateInput(text: String): Boolean {
        return text.isNotEmpty()

    }

    private fun validatejsup(text: String): Boolean {
        try{
            Log.e("jsup", "statted ")
            val j = Jsoup.connect(text).get();
            Log.e("jsup", "ended")
            Log.e("jsup", j.toString())
            return j.toString()
                .contains("This browser window is used to keep your authentication session active.")

        }catch (e:Exception){
            return  false
        }



    }
}