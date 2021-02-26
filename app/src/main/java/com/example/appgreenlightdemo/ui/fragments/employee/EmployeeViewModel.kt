package com.example.appgreenlightdemo.ui.fragments.employee

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appgreenlightdemo.database.AppDao
import com.example.appgreenlightdemo.network.NetworkEmployee
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch

class EmployeeViewModel
@ViewModelInject
constructor( private val appDao: AppDao) : ViewModel(){

    private var finalList : ArrayList<NetworkEmployee> = ArrayList()
    var obsEmployee : MutableLiveData<ArrayList<NetworkEmployee>> = MutableLiveData()

    fun setEmployeeData(territory : String) {
        viewModelScope.launch {
            val data = appDao.getAllData()
            getEmployee(data[0].employee, territory)
        }
    }

    private fun getEmployee(employeeStr: String, territory: String) {
        finalList = ArrayList()
        val gson = Gson()
        val type = object : TypeToken<List<NetworkEmployee>>() {}.type
        val employees = gson.fromJson<List<NetworkEmployee>>(employeeStr, type)
        for (employee in employees) {
            when {
                employee.territory.contains(territory) -> {
                    finalList.add(employee)
                }
            }
        }
        obsEmployee.postValue(finalList)
    }

    fun setListbyASC() {
        val listASC = finalList.sortedBy { it.name }
        obsEmployee.postValue(ArrayList(listASC))
    }

    fun setListbyDSC() {
        val listDSC = finalList.sortedByDescending { it.name }
        obsEmployee.postValue(ArrayList(listDSC))
    }
}