package com.kay.todoapp.fragments.update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.kay.todoapp.R
import com.kay.todoapp.data.models.Priority
import com.kay.todoapp.databinding.FragmentUpdateBinding
import com.kay.todoapp.fragments.SharedViewModel

class UpdateFragment : Fragment() {

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<UpdateFragmentArgs>()

    private val mSharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)

        binding.currentTitleEt.setText(args.currentItem.tittle)
        binding.currentDescriptionEt.setText(args.currentItem.description)
        binding.currentPrioritiesSpinner.setSelection(parsePriority(args.currentItem.priority))
        binding.currentPrioritiesSpinner.onItemSelectedListener = mSharedViewModel.listener

        // Set Menu
        setHasOptionsMenu(true)

        return binding.root

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    private fun parsePriority(priority: Priority): Int{
        return when(priority){
            Priority.HIGH -> 0
            Priority.MEDIUM -> 1
            Priority.LOW -> 2
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}