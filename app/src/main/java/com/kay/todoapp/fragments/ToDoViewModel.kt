package com.kay.todoapp.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.kay.todoapp.data.ToDoDatabase
import com.kay.todoapp.data.models.PriorityOrderSetting
import com.kay.todoapp.data.models.ToDoData
import com.kay.todoapp.data.repository.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoViewModel(application: Application) : AndroidViewModel(application) {

    // ToDoDatabase class -> pass in application context -> get reference from our Dao class.
    private val priorityOrderSettingDao = ToDoDatabase.getDatabase(application).priorityOrderSettingDao() // TODO <- part of our priority order insert
    private val toDoDao = ToDoDatabase.getDatabase(application).toDoDao()
    private val repository: ToDoRepository = ToDoRepository(toDoDao, priorityOrderSettingDao) // TODO <- trying to pass inn repository

    val readAllOrder: LiveData<PriorityOrderSetting?> = repository.readNewOrder // TODO <- part of priority order insert
    val getAllData: LiveData<List<ToDoData>> = repository.getAllData // <- reset
    val sortByHighPriority: LiveData<List<ToDoData>> = repository.sortByHighPriority
    val sortByLowPriority: LiveData<List<ToDoData>> = repository.sortByLowPriority

    // Coroutine for database operations.

    fun insertData(toDoData: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(toDoData)
        }
    }

    fun updateData(toDoData: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(toDoData)
        }
    }

    // TODO make a add order function
    fun addOrder(priority: String){
        // made this database object below
        val priorityOrderSetting = PriorityOrderSetting(sortOrder = priority)
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNewOrder(priorityOrderSetting)
        }
    }

    fun deleteItem(toDoData: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(toDoData)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }

    fun searchDatabase(searchQuery: String): LiveData<List<ToDoData>> {
        return repository.searchData(searchQuery)
    }
}
