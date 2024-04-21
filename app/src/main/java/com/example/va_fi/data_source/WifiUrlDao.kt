package com.example.va_fi.data_source

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.va_fi.model.WifiUrl


@Dao
interface WifiUrlDao {

    /** get all WifiUrls  **/
    @Query("SELECT * FROM WifiUrl")
    fun getWifiUrls(): LiveData<List<WifiUrl>>

    /** get WifiUrl by id **/
    @Query("SELECT * FROM WifiUrl WHERE id = :id")
    suspend fun getWifiUrlById(id: Int): WifiUrl?

    /** insert WifiUrls & update **/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWifiUrl(wifiUrl: WifiUrl)


    /** delete a unique WifiUrl  **/
    @Delete
    suspend fun deleteWifiUrl(wifiUrl: WifiUrl)
}