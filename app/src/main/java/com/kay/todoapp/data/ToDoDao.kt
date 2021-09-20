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

    // Reading the live data
    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<ToDoData>>

    // Inserting the data
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(toDoData: ToDoData)

    // update
    @Update
    suspend fun updateData(toDoData: ToDoData)

    // Delete single item
    @Delete
    suspend fun deleteItem(toDoData: ToDoData)

    // Delete all items
    @Query("DELETE FROM todo_table")
    suspend fun deleteAll()

    // Search
    @Query("SELECT * FROM todo_table WHERE tittle LIKE :searchQuery")
    fun searchDataBase(searchQuery: String): LiveData<List<ToDoData>>

    // Sort the high priorities first
    @Query("SELECT * From todo_table ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
    fun sortByHighPriority(): LiveData<List<ToDoData>>

    // Sort the low priorities first
    @Query("SELECT * From todo_table ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END")
    fun sortByLowPriority(): LiveData<List<ToDoData>>

}
