package com.example.appgreenlightdemo.ui.fragments.zone

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appgreenlightdemo.database.AppDao
import com.example.appgreenlightdemo.network.NetworkZone
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch

/**
 * createdBy : Amit
 * description : this is a viewmodel class, responsible for handling the data between main repository and zone fragment.
 */
class ZoneViewModel
@ViewModelInject
constructor( private val appDao: AppDao): ViewModel() {

    var list : ArrayList<NetworkZone> = ArrayList()
    var obsZoneList : MutableLiveData<ArrayList<NetworkZone>> = MutableLiveData()

    /**
     * get the zone list from main repository result and pass it to getZones()
     */
    fun setZoneData(territory : String) {
        viewModelScope.launch {
            val data = appDao.getAllData()
            getZones(data[0].zone, territory)
        }
    }

    /**
     * this function will update the mutable variable for zone list for territory.
     */
    private fun getZones(response: String, territory: String) {
        list = ArrayList()
        val gson = Gson()
        val type = object : TypeToken<List<NetworkZone>>() {}.type
        val zones = gson.fromJson<List<NetworkZone>>(response, type)
        for (data in zones) {
            when{
                data.territory.contains(territory) -> {
                    list.add(data)
                }
            }
        }
        obsZoneList.postValue(list)
    }

    /**
     * sorting list in ascending order.
     */
    fun setListbyASC() {
        val listASC = list.sortedBy { it.zone }
        obsZoneList.postValue(ArrayList(listASC))
    }

    /**
     * sorting list in descending order.
     */
    fun setListbyDSC() {
        val listDSC = list.sortedByDescending { it.zone }
        obsZoneList.postValue(ArrayList(listDSC))
    }


}