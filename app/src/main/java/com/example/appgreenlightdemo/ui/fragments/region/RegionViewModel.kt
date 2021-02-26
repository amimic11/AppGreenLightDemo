package com.example.appgreenlightdemo.ui.fragments.region

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appgreenlightdemo.database.AppDao
import com.example.appgreenlightdemo.network.NetworkCountryRegion
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch

class RegionViewModel
@ViewModelInject
constructor( private val appDao: AppDao) : ViewModel() {

    private var list : ArrayList<NetworkCountryRegion> = ArrayList()
    var obsRegion : MutableLiveData<ArrayList<NetworkCountryRegion>> = MutableLiveData()

    fun getRegionData(territory : String) {
        viewModelScope.launch {
            val data = appDao.getAllData()
            getRegions(data[0].region, territory)
        }
    }

    private fun getRegions(regionStr: String, territory: String) {
        list = ArrayList()
        val gson = Gson()
        val type = object : TypeToken<List<NetworkCountryRegion>>() {}.type
        val regions = gson.fromJson<List<NetworkCountryRegion>>(regionStr, type)

        for (region in regions) {
            when{
                region.territory.contains(territory) -> {
                    list.add(region)
                }
            }
        }
        obsRegion.postValue(list)
    }

    fun setListbyASC() {
        val listASC = list.sortedBy { it.region }
        obsRegion.postValue(ArrayList(listASC))
    }

    fun setListbyDSC() {
        val listDSC = list.sortedByDescending { it.region }
        obsRegion.postValue(ArrayList(listDSC))
    }
}