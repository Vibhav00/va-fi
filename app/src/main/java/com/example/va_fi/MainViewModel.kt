package com.example.va_fi

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.va_fi.model.WifiUrl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel
@Inject constructor(
    /** add note . delete note , get note , get notes **/
    private val mainRepository: MainRepository
) :ViewModel() {

    fun getWifi()=mainRepository.getWifiUrls()

    fun setWifi(){
        viewModelScope.launch {
            mainRepository.insertWifiUrl(WifiUrl(0,"dfkj"))
        }
    }
    fun deleteWifi(wifiUrl: WifiUrl){
          viewModelScope.launch {
              mainRepository.deleteWifiUrl(wifiUrl)
          }
    }

}