package com.kay.todoapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kay.todoapp.data.models.PriorityOrderSetting

@Dao
interface PriorityOrderSettingDao {

    // inserting the new order
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(priorityOrderSetting: PriorityOrderSetting)

    // reading the data??
    @Query("SELECT * FROM sort_order_table ORDER BY `key` ASC LIMIT 1")
    fun getOrder(): LiveData<PriorityOrderSetting?>
}
