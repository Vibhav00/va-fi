package com.example.va_fi

import androidx.lifecycle.LiveData
import com.example.va_fi.data_source.WifiUrlDao
import com.example.va_fi.model.WifiUrl
import kotlinx.coroutines.flow.Flow

class MainRepository(private val dao: WifiUrlDao) {


     fun getWifiUrls(): LiveData<List<WifiUrl>> {
        return dao.getWifiUrls()
    }

    suspend fun getWifiUrlById(id: Int): WifiUrl? {
        return dao.getWifiUrlById(id)
    }

    suspend fun insertWifiUrl(WifiUrl: WifiUrl) {
        dao.insertWifiUrl(WifiUrl)
    }

    suspend fun deleteWifiUrl(WifiUrl: WifiUrl) {
        dao.deleteWifiUrl(WifiUrl)
    }
}
