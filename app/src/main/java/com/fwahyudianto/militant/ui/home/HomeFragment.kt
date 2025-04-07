package com.fwahyudianto.militant.ui.home

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
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.facebook.shimmer.ShimmerFrameLayout
import com.fwahyudianto.militant.R
import com.fwahyudianto.militant.data.model.Event
import com.fwahyudianto.militant.data.response.ListEventsItem
import com.fwahyudianto.militant.databinding.FragmentHomeBinding
import com.fwahyudianto.militant.foundation.adapter.EventListApiAdapter
import com.fwahyudianto.militant.ui.EventDetailActivity
import com.google.android.material.snackbar.Snackbar

/**
 *  This software, all associated documentation, and all copies are CONFIDENTIAL INFORMATION of Kalpawreksa Teknologi Indonesia
 *  https://www.fwahyudianto.id
 *  ® Wahyudianto, Fajar
 *  Email 	: me@fwahyudianto.id
 */

class HomeFragment : Fragment() {
    private lateinit var mRecyleViewEvent: RecyclerView

    //  private val mArrEventsList = ArrayList<Event>()
    private var mEvent = listOf<ListEventsItem>()
    private var mUpcoming = listOf<ListEventsItem>()

    private var mHomeBinding: FragmentHomeBinding? = null
    private val oBinding get() = mHomeBinding!!
    private val mHomeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)

        mRecyleViewEvent = oBinding.homeEventsRvfinished
        mRecyleViewEvent.setHasFixedSize(true)

        if (savedInstanceState == null) {
            mRecyleViewEvent.layoutManager = LinearLayoutManager(this.requireContext())

            mHomeViewModel.mUpcomingEvents.observe(viewLifecycleOwner) { lsImages ->
                val imgList = ArrayList<SlideModel>()
                mUpcoming = lsImages

                for (i in mUpcoming.indices) {
                    imgList.add(SlideModel(mUpcoming[i].mediaCover, mUpcoming[i].name))
                }
                mHomeBinding?.homeImgsUpcoming?.setImageList(imgList, ScaleTypes.FIT)
            }
            mHomeViewModel.mFinishedEvents.observe(viewLifecycleOwner) { lsEvents ->
                mEvent = lsEvents
                getFinishedEvents(mEvent)
            }

            showLoading(
                mHomeViewModel.isLoadingUpcoming,
                mHomeBinding!!.homeImgsShimmer, mHomeBinding!!.homeImgsUpcoming,
                mHomeBinding!!.root, mHomeBinding!!.homeEventsTvsubtitle2,
                mHomeBinding!!.homeImgsShimmer, mHomeBinding!!.homeImgsUpcoming
            )

            showLoading(
                mHomeViewModel.isLoadingFinished,
                mHomeBinding!!.homeRvShimmer, mHomeBinding!!.homeEventsRvfinished
            )
        }

        return oBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mHomeViewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            Snackbar.make(oBinding.root, error, Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mHomeBinding = null
    }

    //  Get Events
    @Suppress("Unused")
    private fun getEvent(): ArrayList<Event> {
        val dtImage = resources.getStringArray(R.array.arrEventsImage)
        val dtCategory = resources.getStringArray(R.array.arrEventsCategory)
        val dtName = resources.getStringArray(R.array.arrEventsName)
        val dtSummary = resources.getStringArray(R.array.arrEventsSummary)
        val lsEvents = ArrayList<Event>()

        for (i in dtName.indices) {
            val oEvent = Event(dtImage[i], dtCategory[i], dtName[i], dtSummary[i])
            lsEvents.add(oEvent)
        }

        return lsEvents
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

    private fun getFinishedEvents(event: List<ListEventsItem>) {
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
//        Log.d("DEV-sendSelectedEvent", event.toString())
        val oIntEventDetail = Intent(requireContext(), EventDetailActivity::class.java)

        oIntEventDetail.putExtra(EventDetailActivity.EVENT_DETAIL, event)
        startActivity(oIntEventDetail)
    }
}