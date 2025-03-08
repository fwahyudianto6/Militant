package com.fwahyudianto.militant.ui.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fwahyudianto.militant.databinding.FragmentEventsBinding

class EventsFragment : Fragment() {
    companion object {
        // fun newInstance() = EventsFragment()
    }

    //  Properties
    private val mEventsViewModel: EventsViewModel by viewModels()
    private var mEventsBinding: FragmentEventsBinding? = null
    private val oBinding get() = mEventsBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mEventsBinding = FragmentEventsBinding.inflate(inflater, container, false)

        // return inflater.inflate(R.layout.fragment_events, container, false)
        return oBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mEventsBinding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //  Set View Model
        mEventsViewModel.dtEvents.observe(viewLifecycleOwner) {
            if (it != null) {
                oBinding.tvEvents.text = it
            }
        }
    }
}