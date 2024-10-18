package com.edsondev26.folkloripedia.ui.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.edsondev26.folkloripedia.databinding.FragmentEventsBinding
import com.edsondev26.folkloripedia.ui.events.adapter.EventAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EventsFragment : Fragment() {
    private val eventViewModel: EventViewModel by viewModels()

    private var _binding: FragmentEventsBinding? = null
    private val binding get() = _binding!!

    private lateinit var eventAdapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventAdapter = EventAdapter()
        initUI()
    }

    private fun initUI() {
        initListeners()
    }

    private fun initListeners() {
        binding.spMonths.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val monthsAbbreviation = listOf(
                    "JAN", "FEB", "MAR", "APR", "MAY", "JUN",
                    "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"
                )
                val selectedMonth = monthsAbbreviation[position]
                getEvents(selectedMonth)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }

    fun getEvents(month: String){
        eventViewModel.fetchEvents(month)

        CoroutineScope(Dispatchers.Main).launch {
            eventViewModel.isLoading.collect { isLoading ->
                if (isLoading) {
                    binding.pbItemInfo.visibility = View.VISIBLE
                    binding.rvListedEvents.visibility = View.GONE
                } else {
                    binding.pbItemInfo.visibility = View.GONE
                    binding.rvListedEvents.visibility = View.VISIBLE
                }
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            eventViewModel.eventItems.collect { events ->
                eventAdapter.updateEventList(events)
            }
        }

        binding.rvListedEvents.apply {
            layoutManager = GridLayoutManager(context, 1)
            adapter = eventAdapter
        }
    }
}