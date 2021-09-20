package com.kay.todoapp.fragments

import android.app.Application
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kay.todoapp.R
import com.kay.todoapp.data.models.Priority
import com.kay.todoapp.data.models.ToDoData

// This is not ideal, dependency injection would be better.

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    /** ======================== List Fragment =================================================== */
    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(false)

    fun checkIfDatabaseEmpty(toDoData: List<ToDoData>) {
        emptyDatabase.value = toDoData.isEmpty()
    }

    /** ======================== Add/Update Fragment ============================================== */


    // function that checks if the input is empty
    fun verifyDataFromUser(title: String, description: String): Boolean {
        return !(title.isEmpty() || description.isEmpty())
    }

    //
    fun parsePriority(priority: String): Priority {
        return when (priority) {
            HIGH_PRIORITY -> {
                Priority.HIGH
            }
             MEDIUM_PRIORITY-> {
                Priority.MEDIUM
            }
            LOW_PRIORITY -> {
                Priority.LOW
            }
            else -> Priority.LOW
        }
    }


    // Function that change color to the spinner
    val listener: AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(p0: AdapterView<*>?) {}

        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            // The logic that check the position of the spinner.
            when (position) {
                0 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(
                            application,
                            R.color.red
                        )
                    )
                }
                1 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(
                            application,
                            R.color.yellow
                        )
                    )
                }
                2 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(
                            application,
                            R.color.green
                        )
                    )
                }
            }
        }
    }

    // Return the priority  to an Int
    fun parsePriorityToInt(priority: Priority): Int {
        return when (priority) {
            Priority.HIGH -> 0
            Priority.MEDIUM -> 1
            Priority.LOW -> 2
        }
    }
    companion object {
        const val HIGH_PRIORITY = "High Priority"
        const val MEDIUM_PRIORITY = "Medium Priority"
        const val LOW_PRIORITY = "Low Priority"
    }
}
