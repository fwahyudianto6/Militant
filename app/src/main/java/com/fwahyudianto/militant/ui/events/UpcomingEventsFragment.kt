package com.fwahyudianto.militant.ui.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fwahyudianto.militant.databinding.FragmentUpcomingEventsBinding

class UpcomingEventsFragment : Fragment() {
    companion object {
        // fun newInstance() = UpcomingEventsFragment()
    }

    //  Properties
    private val mUpcomingEventsViewModel: UpcomingEventsViewModel by viewModels()
    private var mUpcomingEventsBinding: FragmentUpcomingEventsBinding? = null
    private val oBinding get() = mUpcomingEventsBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mUpcomingEventsBinding = FragmentUpcomingEventsBinding.inflate(inflater, container, false)

        return oBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mUpcomingEventsBinding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //  Set View Model
        mUpcomingEventsViewModel.dtUpcomingEvents.observe(viewLifecycleOwner) {
            if (it != null) {
                oBinding.tvUpcomingEvents.text = it
            }
        }
    }
}