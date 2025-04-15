package com.fwahyudianto.militant.ui.events

//  Import Library
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.fwahyudianto.militant.data.response.ListEventsItem
import com.fwahyudianto.militant.databinding.FragmentUpcomingEventsBinding
import com.fwahyudianto.militant.foundation.adapter.EventListApiAdapter
import com.fwahyudianto.militant.ui.EventDetailActivity
import com.google.android.material.snackbar.Snackbar

/**
 *  This software, all associated documentation, and all copies are CONFIDENTIAL INFORMATION of Kalpawreksa Teknologi Indonesia
 *  https://www.fwahyudianto.id
 *  ® Wahyudianto, Fajar
 *  Email 	: me@fwahyudianto.id
 */

class UpcomingEventsFragment : Fragment() {
    private lateinit var mRecyleViewEvent: RecyclerView

    //  Properties
    private val mUpcomingEventsViewModel: UpcomingEventsViewModel by viewModels()
    private var mUpcomingEventsBinding: FragmentUpcomingEventsBinding? = null
    private val oBinding get() = mUpcomingEventsBinding!!

    private var mUpcoming = listOf<ListEventsItem>()

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

        mRecyleViewEvent = oBinding.upcomingEventsRvitem

        mRecyleViewEvent.layoutManager = LinearLayoutManager(this.requireContext())
        mRecyleViewEvent.setHasFixedSize(true)

        mUpcomingEventsViewModel.mUpcomingEvents.observe(viewLifecycleOwner) { lsEvents ->
            mUpcoming = lsEvents
            getUpcomingEvents(mUpcoming)
        }

        showLoading(
            mUpcomingEventsViewModel.isLoadingUpcoming,
            mUpcomingEventsBinding!!.upcomingRvShimmer,
            mUpcomingEventsBinding!!.upcomingEventsRvitem
        )

        mUpcomingEventsViewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            Snackbar.make(oBinding.root, error, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun showLoading(
        isLoading: LiveData<Boolean>,
        shimmerView: ShimmerFrameLayout,
        contentView: View,
        targetConstraintLayout: ConstraintLayout? = null,
        anchorView: View? = null,
        topAnchorLoading: View? = null,
        topAnchorLoaded: View? = null
    ) {
        isLoading.observe(viewLifecycleOwner) { loading ->
            if (loading) {
                shimmerView.visibility = View.VISIBLE
                shimmerView.startShimmer()
                contentView.visibility = View.GONE

                if (targetConstraintLayout != null && anchorView != null && topAnchorLoading != null) {
                    ConstraintSet().apply {
                        clone(targetConstraintLayout)
                        connect(
                            anchorView.id,
                            ConstraintSet.TOP,
                            topAnchorLoading.id,
                            ConstraintSet.BOTTOM
                        )
                        applyTo(targetConstraintLayout)
                    }
                }
            } else {
                shimmerView.stopShimmer()
                shimmerView.visibility = View.GONE
                contentView.visibility = View.VISIBLE

                if (targetConstraintLayout != null && anchorView != null && topAnchorLoaded != null) {
                    ConstraintSet().apply {
                        clone(targetConstraintLayout)
                        connect(
                            anchorView.id,
                            ConstraintSet.TOP,
                            topAnchorLoaded.id,
                            ConstraintSet.BOTTOM
                        )
                        applyTo(targetConstraintLayout)
                    }
                }
            }
        }
    }

    private fun getUpcomingEvents(event: List<ListEventsItem>) {
        val listEventAdapter = EventListApiAdapter(event)
        mRecyleViewEvent.adapter = listEventAdapter

        listEventAdapter.setOnItemClickCallback(object : EventListApiAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ListEventsItem) {
                sendSelectedEvent(data)
            }
        })
    }

    @Suppress("Unused")
    private fun sendSelectedEvent(event: ListEventsItem) {
        val oIntEventDetail = Intent(requireContext(), EventDetailActivity::class.java)

        oIntEventDetail.putExtra(EventDetailActivity.EVENT_DETAIL, event)
        startActivity(oIntEventDetail)
    }
}