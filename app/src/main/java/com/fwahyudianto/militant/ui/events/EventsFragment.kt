package com.fwahyudianto.militant.ui.events

//  Import Library
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fwahyudianto.militant.databinding.FragmentEventsBinding

/**
 * This software, all associated documentation, and all copies are CONFIDENTIAL INFORMATION of Kalpawreksa Teknologi Indonesia
 * https://www.fwahyudianto.id
 * ® Wahyudianto, Fajar
 * Email 	: me@fwahyudianto.id
 *
 * 	Date			User				Note
 *  -------------------------------------------------------------------------------------------------------------------------
 *  End Revised
 */

class EventsFragment : Fragment() {
    //  Properties
    private val mEventsViewModel: EventsViewModel by viewModels()
    private var mEventsBinding: FragmentEventsBinding? = null
    private val oBinding get() = mEventsBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mEventsBinding = FragmentEventsBinding.inflate(inflater, container, false)

        return oBinding.root
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

    override fun onDestroyView() {
        super.onDestroyView()
        mEventsBinding = null
    }
}