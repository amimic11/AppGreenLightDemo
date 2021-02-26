package com.example.appgreenlightdemo.repository

import com.example.appgreenlightdemo.database.AppDao
import com.example.appgreenlightdemo.database.ResponseEntity
import com.example.appgreenlightdemo.network.*
import com.example.appgreenlightdemo.util.DataState
import com.example.appgreenlightdemo.util.Utils.prepareForDBData
import com.example.appgreenlightdemo.util.Utils.prepareToNetworkModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class MainRepository (
    private val apiService: ApiService,
    private val appDao: AppDao ) {

    suspend fun getNetworkDAta() = flow {
        emit(DataState.Loading)
        try{
            val networkData = apiService.getData()
            appDao.insert(prepareForDBData(networkData))
            val savedData = appDao.getAllData()
            emit(DataState.Success(prepareToNetworkModel(savedData)))
        } catch (e : Exception) {
            when{
                appDao.getAllData().isNotEmpty() -> {
                    val savedData = appDao.getAllData()
                    emit(DataState.Success(prepareToNetworkModel(savedData)))
                }
                else -> emit(DataState.Error(e))
            }
        }
    }

}