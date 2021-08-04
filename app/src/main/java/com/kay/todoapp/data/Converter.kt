package com.kay.todoapp.data

import androidx.room.TypeConverter
import com.kay.todoapp.data.models.Priority

class Converter {

    // Function that converting our "priorities object" to String
    @TypeConverter
    fun fromPriority(priority: Priority): String{
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }

}