package com.example.va_fi.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(indices = [Index(value = ["url"],
    unique = true)])
data class WifiUrl(
    @PrimaryKey
    val id: Int? = null,
    @ColumnInfo(name = "url")
    val url: String = "",
    val isValid: Boolean = false,
    val createdDate: Long = 0,
    val totalTimeUsed: Long = 0,

    )
