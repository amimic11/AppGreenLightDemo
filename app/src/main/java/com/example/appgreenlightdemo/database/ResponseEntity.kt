package com.example.appgreenlightdemo.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/***
 * createdBy : Amit
 * description :
 *  this is an entity class which will be used to create a tale in app_database DB.
 */
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
