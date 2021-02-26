package com.example.appgreenlightdemo.ui.fragments

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.appgreenlightdemo.database.AppDao
import com.example.appgreenlightdemo.database.ResponseEntity
import com.example.appgreenlightdemo.network.*
import com.example.appgreenlightdemo.repository.MainRepository
import com.example.appgreenlightdemo.util.DataState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch


class CountryViewModel
@ViewModelInject
constructor( private val mainRepository: MainRepository,
private val appDao: AppDao)
    : ViewModel() {

    private var finalList : ArrayList<NetworkCountry> = ArrayList()

    private val _dataState : MutableLiveData<DataState<NetworkResponse>> = MutableLiveData()
    val dataState : LiveData<DataState<NetworkResponse>> get() = _dataState

    fun setStateEvent(mainStateEvent: MainStateEvent) {
        viewModelScope.launch {
            when(mainStateEvent) {
                is MainStateEvent.GetPostEvent -> {
                    mainRepository.getNetworkDAta().collect { list ->
                        _dataState.value = list
                    }
                }
                MainStateEvent.None -> {
                    //show toast...
                }
            }
        }
    }
}

sealed class MainStateEvent{
    object GetPostEvent : MainStateEvent()

    object None : MainStateEvent()

}