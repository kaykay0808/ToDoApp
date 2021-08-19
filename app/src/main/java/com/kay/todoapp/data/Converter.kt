package com.kay.todoapp.data

import androidx.room.TypeConverter
import com.kay.todoapp.data.models.Priority

class Converter {

    // Function that converting our "priorities object" to String
    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    // Function that converting string back to priority object
    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }
}
