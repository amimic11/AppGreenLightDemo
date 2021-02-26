package com.example.appgreenlightdemo.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NetworkResponse(
    @SerializedName("ResponseStatus")
    @Expose
    val responseStatus: Int,

    @SerializedName("ResponseData")
    @Expose
    val responseData: ResponseData,

    @SerializedName("Success")
    @Expose
    val success: Boolean
)

data class ResponseData(
    @SerializedName("country")
    @Expose
    val country: List<NetworkCountry>,

    @SerializedName("zone")
    @Expose
    val zone: List<NetworkZone>,

    @SerializedName("region")
    @Expose
    val region: List<NetworkCountryRegion>,

    @SerializedName("area")
    @Expose
    val area: List<NetworkArea>,

    @SerializedName("employee")
    @Expose
    val employee: List<NetworkEmployee>
)

data class NetworkCountry(
    @SerializedName("country")
    @Expose
    val country: String,

    @SerializedName("territory")
    @Expose
    val territory: String
)

data class NetworkZone(
    @SerializedName("zone")
    @Expose
    val zone: String,

    @SerializedName("territory")
    @Expose
    val territory: String
)

data class NetworkCountryRegion(
    @SerializedName("region")
    @Expose
    val region: String,

    @SerializedName("territory")
    @Expose
    val territory: String,
)

data class NetworkArea(
    @SerializedName("area")
    @Expose
    val area: String,

    @SerializedName("territory")
    @Expose
    val territory: String
)

data class NetworkEmployee(
    @SerializedName("area")
    @Expose
    val area: String,

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("territory")
    @Expose
    val territory: String
)