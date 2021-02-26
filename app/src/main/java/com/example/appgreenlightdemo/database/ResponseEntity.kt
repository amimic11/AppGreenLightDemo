package com.example.appgreenlightdemo.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "network_response_data")
data class ResponseEntity(
    @PrimaryKey(autoGenerate = false)
    val id : Int,
    val country : String,
    val zone : String,
    val region : String,
    val area : String,
    val employee : String
)
