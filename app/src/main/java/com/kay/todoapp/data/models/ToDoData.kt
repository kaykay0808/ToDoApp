package com.kay.todoapp.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "todo_table")
@Parcelize // <- pass data to other fragments ?
data class ToDoData(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var tittle: String,
    var priority: Priority,
    var description: String
) : Parcelable
