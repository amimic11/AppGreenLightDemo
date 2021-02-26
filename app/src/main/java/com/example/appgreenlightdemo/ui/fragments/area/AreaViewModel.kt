package com.example.appgreenlightdemo.ui.fragments.area

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appgreenlightdemo.database.AppDao
import com.example.appgreenlightdemo.network.NetworkArea
import com.example.appgreenlightdemo.repository.MainRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/***
 * createdBy : Amit
 * description :
 * this class process data for Area fragment and handles data between repository and fragments.
 */
class AreaViewModel
@ViewModelInject
constructor( private val mainRepository: MainRepository) : ViewModel() {

    private var finalList : ArrayList<NetworkArea> = ArrayList()
    var obsAreas : MutableLiveData<List<NetworkArea>> = MutableLiveData()

    /**
     * getting area list data from repository...
     */
    fun setAreaData(territory : String) {
        viewModelScope.launch {
            mainRepository.allData().collect {
                getAreas(it[0].area, territory)
            }
        }
    }

    /**
     * get the area for territory from area list
     */
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

    /**
     * sort list in ascending order.
     */
    fun setListbyASC() {
        val listASC = finalList.sortedBy { it.area }
        obsAreas.postValue(ArrayList(listASC))
    }

    /**
     * sort list in deadcending order.
     */
    fun setListbyDSC() {
        val listDSC = finalList.sortedByDescending { it.area }
        obsAreas.postValue(ArrayList(listDSC))
    }
}