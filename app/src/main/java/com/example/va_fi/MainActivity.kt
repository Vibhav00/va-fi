package com.example.va_fi

import android.app.AlertDialog
import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.va_fi.adapter.AdapterWifi
import com.example.va_fi.databinding.ActivityMainBinding
import com.example.va_fi.databinding.AddLinkBinding
import com.example.va_fi.databinding.AllListBinding
import com.example.va_fi.databinding.StillConnectedBinding
import com.example.va_fi.model.WifiUrl
import com.example.va_fi.preferenceutils.PreferenceUtils
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), AdapterWifi.OnClickWifiItem {



    private lateinit var toolbar: Toolbar
    private lateinit var navigationview: NavigationView
    private lateinit var drawer: DrawerLayout


    /** main activity binding **/
    private lateinit var activityMainBinding: ActivityMainBinding

    /** list of wifi dialog **/

    private lateinit var dialog: AlertDialog

    /** getting view model by dependency injection  **/
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {




        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)

        drawer = findViewById(R.id.drawer)
        navigationview =findViewById(R.id.NavigationView)
        toolbar= findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.OpenDrawer,R.string.CloseDrawer)

        drawer.addDrawerListener(toggle)
        toggle.syncState()
        if (Build.VERSION.SDK_INT > 9) {
            val policy = ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
        checkIfActive()
        setOnClickListener()
        checkWifiActive()

    }


    /** function to handle click listeners **/
    private fun setOnClickListener() {
        activityMainBinding.btnConnect.setOnClickListener {
            val text = PreferenceUtils.getSharedPreferences(this@MainActivity).getLastUrl()
            startService(text, null)
        }
        activityMainBinding.btnAdd.setOnClickListener {
            createInputDialog()
        }
        activityMainBinding.btnList.setOnClickListener {
            displayList()
        }
    }


    /** starting service from input text **/
    private fun createInputDialog() {
        val addLinkBinding = AddLinkBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(this)
        builder.setView(addLinkBinding.root)
        val dialog = builder.create()
        dialog.show()

        addLinkBinding.apply {
            connectBtn.setOnClickListener {
                val text = addLinkBinding.nameEt.text.toString()
                startService(text, dialog)

            }
            cancelBnt.setOnClickListener {
                dialog.dismiss()
            }
        }

    }

    /** displaying the list of different urls in rv  **/
    private fun displayList() {
        val allListBinding = AllListBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(this)
        builder.setView(allListBinding.root)
        dialog = builder.create()
        dialog.show()
        allListBinding.rvListWifi.layoutManager = LinearLayoutManager(this)
        mainViewModel.getWifi().observe(
            this, Observer {
                if (it.isEmpty()) {
                    dialog.dismiss()
                    Toast.makeText(this@MainActivity, "No URL Available", Toast.LENGTH_SHORT)
                        .show()
                    return@Observer
                }

                allListBinding.rvListWifi.adapter = AdapterWifi(it, this)
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }
        )
    }


    /** validating if input is correct or not   **/
    private fun validateInput(text: String): Boolean {
        if(text.isEmpty()){
            Toast.makeText(this,"Url must not be empty ",Toast.LENGTH_SHORT).show()
            return false
        }
        if(text.contains("http:")){
              if(!(text.contains("http://172.29.48.1:1000/keepalive?") || text.contains("http://172.16.0.1:1000/keepalive?")  )){
                  Toast.makeText(this,"Url must be correct ",Toast.LENGTH_SHORT).show()
                  return  false
              }

              if(text.split("?")[1].isEmpty()){
                  Toast.makeText(this,"Url must be correct  ",Toast.LENGTH_SHORT).show()

                  return  false
              }
        }else{
            return  false
        }

        return true

    }

    /** validating if the url is correct or not  **/
    private fun validateJsoup(text: String): Boolean {
        try {
            val j = Jsoup.connect(text).get()
            return j.toString()
                .contains("This browser window is used to keep your authentication session active.")

        } catch (e: Exception) {
            Toast.makeText(this," Your URL is not correct!",Toast.LENGTH_SHORT).show()
            return false
        }
    }


    /** checking if the url is still active  **/
    private fun checkIfActive() {
        try {
            val lastTime = PreferenceUtils.getSharedPreferences(this).getLastActive()
            val currentTime = System.currentTimeMillis()
            Toast.makeText(
                this,
                ((currentTime - lastTime) / (60_000)).toString(),
                Toast.LENGTH_SHORT
            ).show()
            if ((currentTime - lastTime) / (60_000) < 40) {
                startDialog()
                Toast.makeText(this, "Still Active", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "not Active ", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }

    }


    /** custom dialog for Still connected situations   **/

    private fun startDialog() {
        val stillConnectedBinding = StillConnectedBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(this)
        builder.setView(stillConnectedBinding.root)
        val dialog = builder.create()
        dialog.show()
        stillConnectedBinding.apply {
            btnStart.setOnClickListener {
                val text = PreferenceUtils.getSharedPreferences(this@MainActivity).getLastUrl()
                startService(text, dialog)

            }
            btnCancel.setOnClickListener {
                dialog.dismiss()
            }
        }
    }


    /** on click handling for rv **/
    override fun onClick(wifiUrl: WifiUrl) {
        startService(wifiUrl.url, dialog)
    }

    override fun onClickDlt(wifiUrl: WifiUrl) {
        mainViewModel.deleteWifi(wifiUrl)
    }

    /** function to start next activity with the validation   **/
    private fun startService(textString: String, customDialog: AlertDialog?) {
        GlobalScope.launch(Dispatchers.Main) {
            var text = textString
            if (!validateInput(text)) {
                return@launch
            } else if (!validateJsoup(text)) {
                return@launch
            }
            mainViewModel.setWifi(text)
            PreferenceUtils.getSharedPreferences(this@MainActivity).setLastUrl(text)
            customDialog?.dismiss()
            val intent = Intent(this@MainActivity, ConnectionActivity::class.java)
            intent.putExtra("URL", text)
            startActivity(intent)
        }
    }


    /** function to check if wifi is on **/
    private  fun checkWifiActive():Boolean{
        val wifiMgr = getSystemService(WIFI_SERVICE) as WifiManager
        return if (wifiMgr.isWifiEnabled) { // Wi-Fi adapter is ON
            val wifiInfo = wifiMgr.connectionInfo
            // Not connected to an access point
            wifiInfo.networkId != -1
            // Connected to an access point
        } else {
            false // Wi-Fi adapter is OFF
        }
    }


    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START)
        } else{
            super.onBackPressed()
        }
    }

//    http://172.29.48.1:1000/keepalive?0e010b00030a04b2

}
