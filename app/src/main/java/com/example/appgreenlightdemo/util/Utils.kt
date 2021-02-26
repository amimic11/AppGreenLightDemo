package com.example.appgreenlightdemo.util

import com.example.appgreenlightdemo.database.ResponseEntity
import com.example.appgreenlightdemo.network.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.Exception

/**
 * createdBy : Amit
 * description : utils object class for app
 */
object Utils {

    /**
     * it will return the NetworkResponse model from database result
     */
    fun prepareToNetworkModel(savedData: List<ResponseEntity>): NetworkResponse {
        var gson = Gson()
        var type = object : TypeToken<List<NetworkCountry>>() {}.type
        val country = gson.fromJson<List<NetworkCountry>>(savedData[0].country, type)
        type = object : TypeToken<List<NetworkZone>>() {}.type
        val zone = gson.fromJson<List<NetworkZone>>(savedData[0].zone, type)

        type = object : TypeToken<List<NetworkCountryRegion>>() {}.type
        val region = gson.fromJson<List<NetworkCountryRegion>>(savedData[0].region, type)

        type = object : TypeToken<List<NetworkArea>>() {}.type
        val area = gson.fromJson<List<NetworkArea>>(savedData[0].area,type)

        type = object : TypeToken<List<NetworkEmployee>>() {}.type
        val employee = gson.fromJson<List<NetworkEmployee>>(savedData[0].employee, type)

        val responseData = ResponseData(country, zone, region, area, employee)
        return NetworkResponse(200, responseData, true)
    }

    /**
     * it will return the responseEntity from networkResponse
     */
    fun prepareForDBData(response: NetworkResponse): ResponseEntity {
        //country...
        var gson = Gson()
        val country = gson.toJson(response.responseData.country)
        gson = Gson()
        val zone = gson.toJson(response.responseData.zone)
        gson = Gson()
        val region = gson.toJson(response.responseData.region)
        gson = Gson()
        val area = gson.toJson(response.responseData.area)
        gson = Gson()
        val employee = gson.toJson(response.responseData.employee)

        return ResponseEntity(0, country, zone, region, area, employee)
    }

    /**
     * this is a sealed class, used for maintaining event in country fragment.
     */
    sealed class MainStateEvent{
        object GetPostEvent : MainStateEvent()

        object None : MainStateEvent()

    }

    /**
     * this is a sealed class used for maintaining the response from network and database.
     */
    sealed class DataState<out R> {
        data class Success<out T>(val data : T) : DataState<T>()
        data class Error(val exception : Exception) : DataState<Nothing>()
        object Loading : DataState<Nothing>()
    }
}