package com.kay.todoapp.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kay.todoapp.R
import com.kay.todoapp.data.models.ToDoData
import com.kay.todoapp.databinding.FragmentListBinding
import com.kay.todoapp.fragments.SharedViewModel
import com.kay.todoapp.fragments.SharedViewModel.Companion.HIGH_PRIORITY
import com.kay.todoapp.fragments.SharedViewModel.Companion.LOW_PRIORITY
import com.kay.todoapp.fragments.SharedViewModel.Companion.RESET
import com.kay.todoapp.fragments.ToDoViewModel
import com.kay.todoapp.fragments.list.adapter.ListAdapter
import com.kay.todoapp.utils.hideKeyboard
import com.kay.todoapp.utils.observeOnce

class ListFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    private val adapter: ListAdapter by lazy { ListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Setup RecyclerView
        setupRecyclerView()

        // Observe LiveData
        mToDoViewModel.readAllOrder.observe(
            viewLifecycleOwner,
            { order ->
                when (order?.sortOrder) {
                    HIGH_PRIORITY -> {
                        mToDoViewModel.sortByHighPriority.observe(
                            viewLifecycleOwner,
                            {
                                setupList(it)
                            }
                        )
                    }
                    LOW_PRIORITY -> {
                        mToDoViewModel.sortByLowPriority.observe(
                            viewLifecycleOwner,
                            {
                                setupList(it)
                            }
                        )
                    }
                    else -> {
                        mToDoViewModel.getAllData.observe(
                            viewLifecycleOwner,
                            {
                                setupList(it)
                            }
                        )
                    }
                }
            }
        )

        mSharedViewModel.emptyDatabase.observe(
            viewLifecycleOwner,
            {
                showEmptyDatabaseViews(it)
            }
        )

        // Set Menu (link to out menu on actionbar.)
        setHasOptionsMenu(true)

        // Hide soft keyboard
        requireActivity().hideKeyboard()

        // Navigation with floating button to add fragment
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
    }

    private fun setupList(data: List<ToDoData>) {
        mSharedViewModel.checkIfDatabaseEmpty(data)
        adapter.setData(data)
        binding.recyclerView.scheduleLayoutAnimation()
    }

    // Menu Settings.
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(
            2,
            StaggeredGridLayoutManager.VERTICAL // <-- Change between LinearLayoutManager and GridLayoutManager and StaggeredGridLayoutManager
        )

        // Swipe to delete
        swipeToDelete(recyclerView)
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = adapter.dataList[viewHolder.adapterPosition]
                // Delete Item
                mToDoViewModel.deleteItem(deletedItem)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
                Toast.makeText(
                    requireContext(),
                    "Successfully Removed: '${deletedItem.tittle}'",
                    Toast.LENGTH_SHORT
                ).show()
                // Restore the swiped Item
                restoreDeletedData(viewHolder.itemView, deletedItem)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    // Restore item after swiping
    private fun restoreDeletedData(view: View, deletedItem: ToDoData) {
        val snackBar = Snackbar.make(
            view, "Deleted '${deletedItem.tittle}'",
            Snackbar.LENGTH_LONG
        )
        // set an action button on our snack bar
        snackBar.setAction("Undo") {
            mToDoViewModel.insertData(deletedItem)
        }
        snackBar.show()
    }

    private fun showEmptyDatabaseViews(emptyDatabase: Boolean) {
        if (emptyDatabase) {
            binding.noDataImageView.visibility = View.VISIBLE
            binding.noDataTextView.visibility = View.VISIBLE
        } else {
            binding.noDataImageView.visibility = View.INVISIBLE
            binding.noDataTextView.visibility = View.INVISIBLE
        }
    }

    // Our toolbar menu (DELETE ALL <-> PRIORITIES)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // Delete all
            R.id.menu_delete_all -> confirmRemoval()

            // From priority high to low
            R.id.menu_priority_high -> {
                mToDoViewModel.sortByHighPriority.observe(
                    viewLifecycleOwner,
                    { adapter.setData(it) }
                )
                savePrioritiesToDatabase(HIGH_PRIORITY)
            }

            // From priority low to high
            R.id.menu_priority_low -> {
                mToDoViewModel.sortByLowPriority.observe(
                    viewLifecycleOwner,
                    { adapter.setData(it) }
                )
                savePrioritiesToDatabase(LOW_PRIORITY)
            }

            // Reset Order
            R.id.menu_reset -> {
                mToDoViewModel.getAllData.observe(
                    viewLifecycleOwner,
                    { adapter.setData(it) }
                )
                savePrioritiesToDatabase(RESET)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Save priorities
    private fun savePrioritiesToDatabase(priority: String) {
        mToDoViewModel.addOrder(priority) // <- // TODO pass this to the viewModel?
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchThroughDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchThroughDatabase(query)
        }
        return true
    }

    //
    private fun searchThroughDatabase(query: String) {
        val searchQuery = "%$query%"

        mToDoViewModel.searchDatabase(searchQuery).observeOnce(
            viewLifecycleOwner,
            { list ->
                list?.let {
                    Log.d("ListFragment", "searchThroughDatabase")
                    adapter.setData(it)
                }
            }
        )
    }

    // Show AlertDialog to confirm removal of all items from database table
    private fun confirmRemoval() {
        // Alert dialog that show yes or no
        val builder = AlertDialog.Builder(requireContext())
        // positive button
        builder.setPositiveButton("YES") { _, _ ->
            mToDoViewModel.deleteAll()
            Toast.makeText(
                requireContext(),
                "Successfully removed everything!",
                Toast.LENGTH_SHORT
            ).show()
        }
        // negative button
        builder.setNegativeButton("NO") { _, _ -> }
        builder.setTitle("Delete everything?")
        builder.setMessage("Are you sure you want to remove everything?")
        builder.create().show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
