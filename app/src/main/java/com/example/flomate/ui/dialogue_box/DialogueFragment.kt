package com.example.flomate.ui.dialogue_box

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.flomate.controller.ApiResult
import com.example.flomate.database.SharedService
import com.example.flomate.databinding.DialogueFragmentBinding
import com.example.flomate.model.addData.AddDataRequest
import com.example.flomate.model.addData.DateRanges
import com.example.flomate.viewModels.DashboardViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DialogueFragment : DialogFragment() {

    private val binding by lazy { DialogueFragmentBinding.inflate(layoutInflater) }

    private val viewModel: DashboardViewModel by activityViewModels()

    lateinit var callBack: (() -> Unit)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDateFilter()

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.submit.setOnClickListener {
            hitApi()
        }
    }

    private fun hitApi() {
        viewModel.setDates(
            AddDataRequest(
                email = SharedService.emailId ?: "",
                dateRanges = DateRanges(
                    endDate = binding.endDate.text.toString(),
                    startDate = binding.startDate.text.toString()
                )
            )
        )

        viewModel.setDatesResponse.observe(viewLifecycleOwner) {
            when (it) {
                ApiResult.Loading -> {
                    // loader
                }

                is ApiResult.Success -> {
                    if (it.data != null) {
                        Toast.makeText(requireContext(), "Data Updated", Toast.LENGTH_SHORT).show()
                        dismiss()
                    }
                }

                is ApiResult.Error -> {

                }
            }
        }

    }

    private fun setupDateFilter() {
        val currentDate = Calendar.getInstance()
        binding.endDate.text =
            "${currentDate.get(Calendar.DAY_OF_MONTH)}-${currentDate.get(Calendar.MONTH) + 1}-${
                currentDate.get(Calendar.YEAR)
            }"

        binding.startDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    val calendar = Calendar.getInstance()
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                    val formattedDate = dateFormat.format(calendar.time)

                    binding.startDate.text = formattedDate

                },
                currentDate.get(Calendar.YEAR),
                currentDate.get(
                    if (currentDate.get(Calendar.MONTH) == 0) 11 else currentDate.get(
                        Calendar.MONTH
                    ) - 1
                ),
                currentDate.get(Calendar.DAY_OF_MONTH)
            )

            datePickerDialog.show()
        }

        binding.endDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    val calendar = Calendar.getInstance()
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                    val formattedDate = dateFormat.format(calendar.time)

                    binding.endDate.text = formattedDate

                },
                currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH),
                currentDate.get(Calendar.DAY_OF_MONTH)
            )

            datePickerDialog.show()
        }

    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        callBack.invoke()
    }

}