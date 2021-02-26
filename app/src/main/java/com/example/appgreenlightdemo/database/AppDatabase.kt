package com.example.appgreenlightdemo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.appgreenlightdemo.database.AppDao
import com.example.appgreenlightdemo.database.ResponseEntity

/***
 * createdBy : Amit
 * description :
 *  this is a database class for application,
 *  used to maintain the versions and database implementation.
 */
@Database (entities = [ResponseEntity :: class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDao() : AppDao
}