package com.example.appgreenlightdemo.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(responseEntity: ResponseEntity)

    @Query("SELECT * FROM network_response_data ORDER BY id ASC")
    suspend fun getAllData() : List<ResponseEntity>
}