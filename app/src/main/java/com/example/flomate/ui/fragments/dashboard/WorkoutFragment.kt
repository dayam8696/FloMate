package com.example.flomate.ui.fragments.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.flomate.databinding.WorkoutFragmentBinding
import com.example.flomate.ui.fragments.BaseFragment
import com.example.flomate.utils.Constants

class WorkoutFragment :BaseFragment() {
    private val binding by lazy { WorkoutFragmentBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationVisibility(false)
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl(Constants.WORK_OUT)
    }
}