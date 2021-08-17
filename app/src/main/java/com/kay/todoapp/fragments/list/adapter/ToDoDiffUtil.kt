package com.kay.todoapp.fragments.list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.kay.todoapp.data.models.ToDoData

// This class finds the difference between two list and provides the updated list as an output.
// This class is used to notify updates to a RecyclerView Adapter.

class ToDoDiffUtil(
    private val oldList: List<ToDoData>,
    private val newList: List<ToDoData>
) : DiffUtil.Callback() {

    // it returns the size of the old list
    override fun getOldListSize(): Int {
        return oldList.size
    }

    // return the size of the new list
    override fun getNewListSize(): Int {
        return newList.size
    }

    // Called by the DiffUtil to decide whether two object represent the same Item.
    // If your items have unique Ids, this method should check their Id equality.
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    // Checks whether two items have the same data. You can change its behaviour depending on your UI.
    // This method is called by DiffUtil only if "areItemsTheSame()" returns true.
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
                && oldList[oldItemPosition].tittle == newList[newItemPosition].tittle
                && oldList[oldItemPosition].description == newList[newItemPosition].description
                && oldList[oldItemPosition].priority == newList[newItemPosition].priority
    }
}