package com.example.flomate.ui.Fragments.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.EventDay
import com.example.caringcatalysts.R
import com.example.flomate.controller.ApiResult
import com.example.caringcatalysts.databinding.FragmentCalendarBinding
import com.example.caringcatalysts.ui.fragments.BaseFragment
import com.example.caringcatalysts.viewModels.DashboardViewModel
import java.util.Calendar


class CalendarFragment : BaseFragment() {
    private val binding by lazy { FragmentCalendarBinding.inflate(layoutInflater) }

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

        viewModel.getListTest()

        viewModel.getListTestResponse.observe(viewLifecycleOwner) {
            when (it) {
                ApiResult.Loading -> {
                    // loader
                }

                is ApiResult.Success -> {
                    showToast("Data Came")
                }

                is ApiResult.Error -> {
                    showToast("Something Went Wrong!!")
                }
            }
        }

        val startDate = Calendar.getInstance()
        startDate.set(2024, Calendar.FEBRUARY, 10)

        val endDate = Calendar.getInstance()
        endDate.set(2024, Calendar.FEBRUARY, 15)

        setEventsBetweenDates(startDate, endDate, R.drawable.blood_drop_icon, binding.calendarView)

    }

    private fun setEventsBetweenDates(
        startDate: Calendar,
        endDate: Calendar,
        resourceId: Int,
        binding: CalendarView
    ) {
        val highlightedDays = mutableListOf<EventDay>()

        val calendar = startDate.clone() as Calendar
        while (calendar <= endDate) {
            val highlightedDay = EventDay(
                calendar.clone() as Calendar,
                resourceId
            )
            highlightedDays.add(highlightedDay)
            calendar.add(Calendar.DATE, 1)
        }

        binding.setEvents(highlightedDays)
    }


}