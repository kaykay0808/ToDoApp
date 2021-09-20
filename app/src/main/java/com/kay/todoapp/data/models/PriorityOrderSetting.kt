package com.kay.todoapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sort_order_table")

data class PriorityOrderSetting (
    @PrimaryKey
    val key: Int = 0,
    val sortOrder: String

)
