package com.example.flomate.ui.fragments.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.flomate.R
import com.example.flomate.databinding.HomeFragmentBinding
import com.example.flomate.ui.fragments.BaseFragment
import com.example.flomate.viewModels.DashboardViewModel

class HomeFragment : BaseFragment() {

    private val binding by lazy { HomeFragmentBinding.inflate(layoutInflater) }
    private val viewModel: DashboardViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        navigationVisibility(true)
        binding.WorkoutBtn.setOnClickListener {
           navigateTo(R.id.action_homeFragment_to_workoutFragment)
        }
        binding.FocusBtn.setOnClickListener {
            navigateTo(R.id.action_homeFragment_to_focusOnFragment)
        }



    }

}