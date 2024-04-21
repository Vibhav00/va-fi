package com.example.va_fi.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.va_fi.model.WifiUrl


@Database(
    entities = [WifiUrl::class],
    version = 1
)
abstract class MainDatabase:RoomDatabase() {

    abstract val mwifiUrlDao : WifiUrlDao

    companion object {
        /** Name of the Database **/
        const val DATABASE_NAME = "wif_db"
    }
}