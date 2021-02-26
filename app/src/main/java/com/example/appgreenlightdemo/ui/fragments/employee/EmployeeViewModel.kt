package com.example.appgreenlightdemo.ui.fragments.employee

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appgreenlightdemo.database.AppDao
import com.example.appgreenlightdemo.network.NetworkEmployee
import com.example.appgreenlightdemo.repository.MainRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * createdBy : Amit
 * description : this is viewmodel class for employee ,for maintaining and processing data data for employee fragment.
 *
 */
class EmployeeViewModel
@ViewModelInject
constructor( private val mainRepository: MainRepository) : ViewModel(){

    private var finalList : ArrayList<NetworkEmployee> = ArrayList()
    var obsEmployee : MutableLiveData<ArrayList<NetworkEmployee>> = MutableLiveData()

    /**
     * this function will get the employee list from main repository and pass the result to get employee
     */
    fun setEmployeeData(territory : String) {
        viewModelScope.launch {
            mainRepository.allData().collect {
                getEmployee(it[0].employee, territory)
            }
        }
    }

    /**
     * it will pass the employee list related to territory to mutable variable
     */
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

    /**
     * it will sort employee list in ascending order.
     */
    fun setListbyASC() {
        val listASC = finalList.sortedBy { it.name }
        obsEmployee.postValue(ArrayList(listASC))
    }

    /**
     * it will sort employee list in descending order.
     */
    fun setListbyDSC() {
        val listDSC = finalList.sortedByDescending { it.name }
        obsEmployee.postValue(ArrayList(listDSC))
    }
}