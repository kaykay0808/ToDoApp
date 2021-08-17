package com.kay.todoapp.data.repository

import androidx.lifecycle.LiveData
import com.kay.todoapp.data.ToDoDao
import com.kay.todoapp.data.models.ToDoData

class ToDoRepository (private val toDoDao: ToDoDao){

    val getAllData: LiveData<List<ToDoData>> = toDoDao.getAllData()
    val sortByHighPriority:LiveData<List<ToDoData>> = toDoDao.sortByHighPriority()
    val sortByLowPriority:LiveData<List<ToDoData>> = toDoDao.sortByLowPriority()

    // Insert Data
    suspend fun insertData(toDoData: ToDoData){
        toDoDao.insertData(toDoData)
    }

    // update
    suspend fun updateData(toDoData: ToDoData){
        toDoDao.updateData(toDoData)
    }

    // delete
    suspend fun deleteItem(toDoData: ToDoData){
        toDoDao.deleteItem(toDoData)
    }

    // delete all
    suspend fun deleteAll(){
        toDoDao.deleteAll()
    }

    // Search
    fun searchData(searchQuery: String): LiveData<List<ToDoData>> {
        return toDoDao.searchDataBase(searchQuery)
    }

}