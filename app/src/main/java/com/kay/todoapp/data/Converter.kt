package com.kay.todoapp.data

import androidx.room.TypeConverter
import com.kay.todoapp.data.models.Priority

class Converter {

    // Function that converting our "priorities object" to String
    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name // <-- Return the name of this enum (High, medium, low)
    }

    // Function that converting string back to priority object
    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }
}
