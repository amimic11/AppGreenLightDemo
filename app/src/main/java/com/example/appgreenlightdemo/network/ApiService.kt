package com.example.appgreenlightdemo.network

import retrofit2.http.GET


interface ApiService {

    @GET("assignment")
    suspend fun getData() : NetworkResponse
}