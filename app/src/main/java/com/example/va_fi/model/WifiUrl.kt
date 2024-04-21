package com.example.va_fi.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class WifiUrl(
    @PrimaryKey
    val id: Int? = null,
    val url: String = "",
    val isValid: Boolean = false,
    val createdDate: Long = 0,
    val totalTimeUsed: Long = 0,

    )
