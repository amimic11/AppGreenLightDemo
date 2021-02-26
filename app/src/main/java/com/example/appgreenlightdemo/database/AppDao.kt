package com.example.appgreenlightdemo.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/***
 * createdBy : Amit
 * description :
 *  this is an interface class that will be used by room database and view models,
 *  the dao basically handles query for room database
 */
@Dao
interface AppDao {

    /**
     * insert value to app_database.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(responseEntity: ResponseEntity)


    /**
     * fetch all available value from app_database.
     */
    @Query("SELECT * FROM network_response_data ORDER BY id ASC")
    suspend fun getAllData() : List<ResponseEntity>
}