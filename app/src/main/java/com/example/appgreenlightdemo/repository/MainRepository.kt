package com.example.appgreenlightdemo.repository

import com.example.appgreenlightdemo.database.AppDao
import com.example.appgreenlightdemo.database.ResponseEntity
import com.example.appgreenlightdemo.network.*
import com.example.appgreenlightdemo.util.DataState
import com.example.appgreenlightdemo.util.Utils.prepareForDBData
import com.example.appgreenlightdemo.util.Utils.prepareToNetworkModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

/***
 * createdBy : Amit
 * description : It will responsible for handling network calls and database queries.
 */
class MainRepository (
    private val apiService: ApiService,
    private val appDao: AppDao ) {

    /**
     * when this function is called, it will make a network call and fetches the updated data,
     * if the network call fail or some other exception occur,
     * then it will check if database have any value, in case if any value found it will pass the stored value to emit
     */
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

    /**
     * this function will simply return all the data stored in app_database
     */
    suspend fun allData() : Flow<List<ResponseEntity>> = flow<List<ResponseEntity>> {
        emit(appDao.getAllData())
    }

}