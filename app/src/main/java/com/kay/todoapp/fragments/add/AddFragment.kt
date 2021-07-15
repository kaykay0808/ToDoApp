package com.kay.todoapp.fragments.add

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.kay.todoapp.R
import com.kay.todoapp.databinding.FragmentAddBinding

class AddFragment : Fragment() {
    // TODO: set up the view binding
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(inflater, container, false)

        // Set Menu
        setHasOptionsMenu(true)


        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}