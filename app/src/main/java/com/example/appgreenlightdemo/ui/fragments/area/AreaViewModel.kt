package com.example.appgreenlightdemo.ui.fragments.area

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appgreenlightdemo.database.AppDao
import com.example.appgreenlightdemo.network.NetworkArea
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch

class AreaViewModel
@ViewModelInject
constructor( private val appDao: AppDao) : ViewModel() {

    private var finalList : ArrayList<NetworkArea> = ArrayList()
    var obsAreas : MutableLiveData<List<NetworkArea>> = MutableLiveData()

    fun setAreaData(territory : String) {
        viewModelScope.launch {
            val data = appDao.getAllData()
            getAreas(data[0].area, territory)
        }
    }

    private fun getAreas(areaStr: String, territory: String) {
        finalList = ArrayList()
        val gson = Gson()
        val type = object : TypeToken<List<NetworkArea>>() {}.type
        val areas = gson.fromJson<List<NetworkArea>>(areaStr, type)
        for (area in areas) {
            when{
                area.territory.contains(territory) -> {
                    finalList.add(area)
                }
            }
        }
        obsAreas.postValue(finalList)
    }

    fun setListbyASC() {
        val listASC = finalList.sortedBy { it.area }
        obsAreas.postValue(ArrayList(listASC))
    }

    fun setListbyDSC() {
        val listDSC = finalList.sortedByDescending { it.area }
        obsAreas.postValue(ArrayList(listDSC))
    }
}