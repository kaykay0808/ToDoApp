package com.kay.todoapp.data.repository

import androidx.lifecycle.LiveData
import com.kay.todoapp.data.ToDoDao
import com.kay.todoapp.data.models.ToDoData

class ToDoRepository (private val toDoDao: ToDoDao){

    val getAllData: LiveData<List<ToDoData>> = toDoDao.getAllData()

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
}