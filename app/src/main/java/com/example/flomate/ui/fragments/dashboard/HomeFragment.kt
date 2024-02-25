package com.example.flomate.ui.fragments.dashboard

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.flomate.R
import com.example.flomate.controller.ApiResult
import com.example.flomate.database.SharedService
import com.example.flomate.databinding.HomeFragmentBinding
import com.example.flomate.model.data_list.DateRange
import com.example.flomate.ui.activities.LoginActivity
import com.example.flomate.ui.fragments.BaseFragment
import com.example.flomate.viewModels.DashboardViewModel
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.abs

class HomeFragment : BaseFragment() {

    private val binding by lazy { HomeFragmentBinding.inflate(layoutInflater) }
    private val viewModel: DashboardViewModel by activityViewModels()
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        firebaseAuth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationVisibility(true)
        setupClickListeners()

        viewModel.getHomeListTest(SharedService.emailId ?: "")

        viewModel.getHomeListTestResponse.observe(viewLifecycleOwner) {
            when (it) {
                ApiResult.Loading -> {
                    // loader
                }

                is ApiResult.Success -> {
                    if (it.data != null) {
                        calculateDaysAndUpdateUI(it.data.dateRanges.firstOrNull())
                    }
                }

                is ApiResult.Error -> {
                }
            }
        }

    }

    private fun calculateDaysAndUpdateUI(dateRange: DateRange?) {
        if (dateRange == null) {
            binding.day.text = "Today, Day 0"
            return
        }

        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val currentDate = Calendar.getInstance().time
        val endDate = dateFormat.parse(dateRange.endDate)

        if (endDate != null) {
            val difference = (endDate.time - currentDate.time) / (1000 * 60 * 60 * 24)
            val daysRemaining = abs(difference).toInt()
            binding.day.text = "Today, Day $daysRemaining"
            when (daysRemaining) {
                in 0..5 -> {
                    binding.phase.text = "You're in the Menstrual phase"
                    binding.DietBtn.text = "Iron-rich foods"
                    binding.FitnessBtn.text = "Light exercises, yoga"
                    binding.MindfulnessBtn.text = "Meditation, deep breathing"
                }

                in 6..13 -> {
                    binding.phase.text = "You're in the Follicular phase"
                    binding.DietBtn.text = " High protein, veggies"
                    binding.FitnessBtn.text = "Cardio, strength training"
                    binding.MindfulnessBtn.text = "Yoga, meditation, walks"
                }

                14 -> {
                    binding.phase.text = "You're in the Ovulatory phase"
                    binding.DietBtn.text = " Leafy greens, fruits"
                    binding.FitnessBtn.text = " Cardio, HIIT workouts"
                    binding.MindfulnessBtn.text = "Positive affirmations, visualization"
                }

                in 15..28 -> {
                    binding.phase.text = "You're in the Luteal phase"
                    binding.DietBtn.text = "Complex carbs, protein"
                    binding.FitnessBtn.text = " Moderate intensity exercises"
                    binding.MindfulnessBtn.text = "Relaxation techniques, journaling"
                }

                else -> {
                    binding.phase.text = "Your period is about to start"
                    binding.DietBtn.text = "Hydrating foods, iron-rich"
                    binding.FitnessBtn.text = "Gentle yoga, walking"
                    binding.MindfulnessBtn.text = "Deep breathing, gentle stretches"
                }
            }
        }


    }

    private fun setupClickListeners() {
        binding.WorkoutBtn.setOnClickListener {
            navigateTo(R.id.action_homeFragment_to_workoutFragment)
        }
        binding.FocusBtn.setOnClickListener {
            navigateTo(R.id.action_homeFragment_to_focusOnFragment)
        }
        binding.logOutBtn.setOnClickListener {
            showSignOutDialog()
        }
    }

    private fun showSignOutDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext())

        dialogBuilder.setMessage("Are you sure you want to sign out?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, _ ->
                if (firebaseAuth.currentUser != null) {
                    firebaseAuth.signOut()
                    SharedService.isLogin = false
                    SharedService.emailId = null
                    val intent = Intent(context, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    showToast("Signed Out")
                    dialog.dismiss()
                } else showToast("Something went wrong!!")

            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }

        val alert = dialogBuilder.create()
        alert.setTitle("Sign Out")
        alert.show()
    }

}