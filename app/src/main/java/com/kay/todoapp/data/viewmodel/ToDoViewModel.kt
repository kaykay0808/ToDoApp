package com.kay.todoapp.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.kay.todoapp.data.ToDoDao
import com.kay.todoapp.data.ToDoDatabase
import com.kay.todoapp.data.models.ToDoData
import com.kay.todoapp.data.repository.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoViewModel (application: Application):  AndroidViewModel(application) {

    private val toDoDao = ToDoDatabase.getDatabase(application).toDoDao()
    private val repository: ToDoRepository = ToDoRepository(toDoDao)

    val gelAllData: LiveData<List<ToDoData>>

    init {
        var repository = ToDoRepository(toDoDao)
        gelAllData = repository.getAllData

    }

    fun insertData(toDoData: ToDoData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(toDoData)
        }
    }

    fun updateData(toDoData: ToDoData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(toDoData)
        }
    }

    fun deleteItem(toDoData: ToDoData){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteItem(toDoData)
        }
    }

    fun deleteAll(){
        viewModelScope.launch() {
            repository.deleteAll()
        }
    }
}