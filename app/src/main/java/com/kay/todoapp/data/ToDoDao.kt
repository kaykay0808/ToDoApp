package com.kay.todoapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kay.todoapp.data.models.ToDoData

@Dao
interface ToDoDao {

    // Reading the data
    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<ToDoData>>

    // Inserting the data
    @Insert(onConflict = OnConflictStrategy.IGNORE) // <-- if we add the same item
    suspend fun insertData(toDoData: ToDoData) // <--

    // update
    @Update
    suspend fun updateData(toDoData: ToDoData)

    // Delete single item
    @Delete
    suspend fun deleteItem(toDoData: ToDoData)

    // Delete all items
    @Query("DELETE FROM todo_table")
    suspend fun deleteAll()
}