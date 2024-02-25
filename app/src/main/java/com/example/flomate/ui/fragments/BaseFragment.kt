package com.example.flomate.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import com.example.flomate.databinding.BaseFragmentBinding
import com.example.flomate.ui.activities.DashboardActivity

open class BaseFragment : Fragment() {
    private val binding by lazy { BaseFragmentBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root

    }

    fun showToast(string: String) {
        Toast.makeText(requireContext(), string, Toast.LENGTH_SHORT).show()
    }

    fun navigateTo(id: Int) {
        findNavController().navigate(id)
    }

    fun navigationVisibility(isVisible :Boolean){

        (activity as DashboardActivity).navigationVisibility(isVisible)

    }
}
