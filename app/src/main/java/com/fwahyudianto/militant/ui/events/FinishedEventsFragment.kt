package com.fwahyudianto.militant.ui.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fwahyudianto.militant.databinding.FragmentFinishedEventsBinding

class FinishedEventsFragment : Fragment() {
    companion object {
//        fun newInstance() = FinishedEventsFragment()
    }

    //  Properties
    private val mFinishedEventsViewModel: FinishedEventsViewModel by viewModels()
    private var mFinishedEventsBinding: FragmentFinishedEventsBinding? = null
    private val oBinding get() = mFinishedEventsBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mFinishedEventsBinding = FragmentFinishedEventsBinding.inflate(inflater, container, false)

        return oBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mFinishedEventsBinding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //  Set View Model
        mFinishedEventsViewModel.dtFinishedEvents.observe(viewLifecycleOwner) {
            if (it != null) {
                oBinding.tvFinishedEvents.text = it
            }
        }
    }
}