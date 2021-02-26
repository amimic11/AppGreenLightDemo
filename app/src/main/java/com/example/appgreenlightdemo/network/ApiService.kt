package com.example.appgreenlightdemo.network

import retrofit2.http.GET


/***
 * createdBy : Amit
 * description :
 *  Interface class, which is used by retrofit for getting response from the server/network.
 */
interface ApiService {

    /**
     * returns response call for assignment endpoint..
     */
    @GET("assignment")
    suspend fun getData() : NetworkResponse
}