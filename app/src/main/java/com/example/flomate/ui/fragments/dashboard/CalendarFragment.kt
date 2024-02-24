package com.example.flomate.ui.fragments.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.applandeo.materialcalendarview.EventDay
import com.example.flomate.R
import com.example.flomate.controller.ApiResult
import com.example.flomate.database.SharedService
import com.example.flomate.databinding.FragmentCalendarBinding
import com.example.flomate.model.data_list.DateRange
import com.example.flomate.ui.fragments.BaseFragment
import com.example.flomate.viewModels.DashboardViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


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

        viewModel.getListTest(SharedService.emailId ?: "")

        viewModel.getListTestResponse.observe(viewLifecycleOwner) {
            when (it) {
                ApiResult.Loading -> {
                    // loader
                }

                is ApiResult.Success -> {
                    if (it.data != null) {
                        readDataAndPopulateCalendar(it.data.dateRanges)
                    }
                }

                is ApiResult.Error -> {
                    showToast("Something Went Wrong!!")
                }
            }
        }


    }

    private fun readDataAndPopulateCalendar(dateRanges: List<DateRange>) {
        val allHighlightedDays = mutableListOf<EventDay>()

        dateRanges.forEach { range ->
            val startDate = Calendar.getInstance()
            startDate.time =
                SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(range.startDate)!!

            val endDate = Calendar.getInstance()
            endDate.time =
                SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(range.endDate)!!

            setEventsBetweenDates(
                startDate,
                endDate,
                R.drawable.blood_drop_icon,
                allHighlightedDays
            )
        }

        binding.calendarView.setEvents(allHighlightedDays)
    }

    private fun setEventsBetweenDates(
        startDate: Calendar,
        endDate: Calendar,
        resourceId: Int,
        existingEvents: MutableList<EventDay>
    ) {
        val calendar = startDate.clone() as Calendar
        while (calendar <= endDate) {
            val highlightedDay = EventDay(
                calendar.clone() as Calendar,
                resourceId
            )
            existingEvents.add(highlightedDay)
            calendar.add(Calendar.DATE, 1)

            if (calendar.get(Calendar.MONTH) != startDate.get(Calendar.MONTH)) {
                break
            }
        }
    }


}