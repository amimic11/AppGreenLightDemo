package com.example.appgreenlightdemo.ui.fragments

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.appgreenlightdemo.database.AppDao
import com.example.appgreenlightdemo.database.ResponseEntity
import com.example.appgreenlightdemo.network.*
import com.example.appgreenlightdemo.repository.MainRepository
import com.example.appgreenlightdemo.util.Utils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

/***
 * createdBy : Amit
 * description :
 *  this is a viewmodel class for country, responsible for handling logic and data for country fragment
 */
class CountryViewModel
@ViewModelInject
constructor( private val mainRepository: MainRepository,
private val appDao: AppDao)
    : ViewModel() {

    private var finalList : ArrayList<NetworkCountry> = ArrayList()

    private val _dataState : MutableLiveData<Utils.DataState<NetworkResponse>> = MutableLiveData()
    val dataState : LiveData<Utils.DataState<NetworkResponse>> get() = _dataState

    /**
     * this function is used to get the response from api and process it in main repository,
     * and get the response and pass it to mutable object.
     */
    fun setStateEvent(mainStateEvent: Utils.MainStateEvent) {
        viewModelScope.launch {
            when(mainStateEvent) {
                is Utils.MainStateEvent.GetPostEvent -> {
                    mainRepository.getNetworkDAta().collect { list ->
                        _dataState.value = list
                    }
                }
                Utils.MainStateEvent.None -> {
                    //show toast...or handle none...
                }
            }
        }
    }
}

