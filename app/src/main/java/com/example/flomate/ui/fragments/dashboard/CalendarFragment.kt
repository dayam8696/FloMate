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
import com.example.flomate.ui.dialogue_box.DialogueFragment
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
        navigationVisibility(true)
        setupClickListeners()

        viewModel.getListTest(SharedService.emailId ?: "")

        viewModel.getListTestResponse.observe(viewLifecycleOwner) {
            when (it) {
                ApiResult.Loading -> {
                    // loader
                }

                is ApiResult.Success -> {
                    if (it.data != null) {
                        readDataAndPopulateCalendar(it.data.dateRanges)
                        updateUI(it.data.dateRanges)
                    }
                }

                is ApiResult.Error -> {
                }
            }
        }

    }

    private fun updateUI(dateRanges: List<DateRange>) {

        binding.averagePeriodLength.text = "${calculateAverageDays(dateRanges)} Days"
        binding.averageCycleLength.text = "${calculateAverageDays(dateRanges) + 24} Days"
        binding.totalDaysThisYear.text = "${totalDaysInYear(dateRanges)} Days"
        binding.totalDaysThisMonth.text = "${totalPeriodDaysInCurrentMonth(dateRanges)} Days"

    }


    private fun totalPeriodDaysInCurrentMonth(periodDays: List<DateRange>): Int {
        val calendar = Calendar.getInstance()
        val currentMonth = calendar.get(Calendar.MONTH) + 1 // Adjusted because months start from 0
        val currentYear = calendar.get(Calendar.YEAR)
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        var totalPeriodDays = 0

        for (period in periodDays) {
            val startDate = dateFormat.parse(period.startDate)
            val endDate = dateFormat.parse(period.endDate)
            val startCal = Calendar.getInstance()
            val endCal = Calendar.getInstance()
            startCal.time = startDate
            endCal.time = endDate

            val periodMonth =
                startCal.get(Calendar.MONTH) + 1 // Adjusted because months start from 0
            val periodYear = startCal.get(Calendar.YEAR)

            if (periodMonth == currentMonth && periodYear == currentYear) {
                // Calculate the days only if the period falls within the current month and year
                val daysInPeriod =
                    ((endDate.time - startDate.time) / (1000 * 60 * 60 * 24)).toInt() + 1
                totalPeriodDays += daysInPeriod
            }
        }
        return totalPeriodDays
    }


    private fun totalDaysInYear(dateRanges: List<DateRange>): Int {
        var totalDays = 0
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        for (dateRange in dateRanges) {
            val startDate = dateFormat.parse(dateRange.startDate)
            val endDate = dateFormat.parse(dateRange.endDate)
            val days = ((endDate.time - startDate.time) / (1000 * 60 * 60 * 24)).toInt() + 1
            totalDays += days
        }
        return totalDays
    }

    private fun calculateAverageDays(dateRanges: List<DateRange>): Int {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        var totalDays = 0L
        for (dateRange in dateRanges) {
            val startDate = dateFormat.parse(dateRange.startDate)
            val endDate = dateFormat.parse(dateRange.endDate)
            val difference = endDate!!.time - startDate!!.time
            totalDays += difference / (1000 * 60 * 60 * 24)
        }
        return (totalDays.toDouble() / dateRanges.size).toInt()
    }

    private fun setupClickListeners() {
        binding.updateBtn.setOnClickListener {
            binding.updateBtn.setOnClickListener {
                val dialogFragment = DialogueFragment()
                dialogFragment.callBack = {
                    viewModel.getListTest(SharedService.emailId ?: "")
                }
                dialogFragment.show(childFragmentManager, "dialogFragment")
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