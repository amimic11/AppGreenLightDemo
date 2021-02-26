package com.example.appgreenlightdemo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.appgreenlightdemo.database.AppDao
import com.example.appgreenlightdemo.database.ResponseEntity

@Database (entities = [ResponseEntity :: class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDao() : AppDao
}