package com.example.appgreenlightdemo.ui.fragments.region

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appgreenlightdemo.database.AppDao
import com.example.appgreenlightdemo.network.NetworkCountryRegion
import com.example.appgreenlightdemo.repository.MainRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * createdBy : Amit
 * description : this is a viewmodel class for android, responsible for maintaining the data between region fragment and main repository
 */
class RegionViewModel
@ViewModelInject
constructor( private val mainRepository: MainRepository) : ViewModel() {

    private var list : ArrayList<NetworkCountryRegion> = ArrayList()
    var obsRegion : MutableLiveData<ArrayList<NetworkCountryRegion>> = MutableLiveData()

    /**
     * fetches the data from main repository and pass the regions to getRegion()
     */
    fun getRegionData(territory : String) {
        viewModelScope.launch {
            mainRepository.allData().collect {
                getRegions(it[0].region, territory)
            }
        }
    }

    /**
     * it process the regions data and get the list for territory,
     */
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

    /**
     * sort the list in ascending order.
     */
    fun setListbyASC() {
        val listASC = list.sortedBy { it.region }
        obsRegion.postValue(ArrayList(listASC))
    }

    /**
     * sort the list in descending order.
     */
    fun setListbyDSC() {
        val listDSC = list.sortedByDescending { it.region }
        obsRegion.postValue(ArrayList(listDSC))
    }
}