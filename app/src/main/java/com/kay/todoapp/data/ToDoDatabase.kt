package com.kay.todoapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kay.todoapp.data.models.ToDoData

// Contains the database holder and serves as the main access pont for the underlying connection your app's persisted, relational data.

@Database(entities = [ToDoData::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class) // <-- this is for our converter.
abstract class ToDoDatabase : RoomDatabase() {

    abstract fun toDoDao(): ToDoDao

    companion object {

        @Volatile // <-- Writes to this field are immediately made visible to other threads.
        private var INSTANCE: ToDoDatabase? = null

        fun getDatabase(context: Context): ToDoDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ToDoDatabase::class.java,
                    "todo_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
