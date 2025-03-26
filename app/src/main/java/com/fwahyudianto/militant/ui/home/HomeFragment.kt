package com.fwahyudianto.militant.ui.home

//  Import Library
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.fwahyudianto.militant.R
import com.fwahyudianto.militant.data.model.Event
import com.fwahyudianto.militant.data.response.ListEventsItem
import com.fwahyudianto.militant.databinding.FragmentHomeBinding
import com.fwahyudianto.militant.foundation.adapter.EventListApiAdapter
import com.fwahyudianto.militant.ui.EventDetailActivity

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

class HomeFragment : Fragment() {
    private lateinit var mRecyleViewEvent: RecyclerView

    //  private val mArrEventsList = ArrayList<Event>()
    private var mEvent = listOf<ListEventsItem>()

    private var mHomeBinding: FragmentHomeBinding? = null
    private val oBinding get() = mHomeBinding!!
    private val mHomeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)

        mRecyleViewEvent = oBinding.rvEvents
        mRecyleViewEvent.setHasFixedSize(true)

        if (savedInstanceState == null) {
            mRecyleViewEvent.layoutManager = LinearLayoutManager(this.requireContext())

            mHomeViewModel.mEvent.observe(viewLifecycleOwner) { event ->
                mEvent = event

                getEvents(mEvent)
            }
            mHomeViewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
        }

        return oBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //  Image Slideshow
        val imgList = ArrayList<SlideModel>() // Create image list
        imgList.add(
            SlideModel(
                "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/event/dos-elevaite_x_dicoding_live_3_data_driven_future_kuasai_data_science_dengan_platform_azure_mc_140325162625.jpg",
                "elevAIte x Dicoding Live #3 - Data Driven Future: Kuasai Data Science dengan Platform Azure"
            )
        )
        imgList.add(
            SlideModel(
                "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/event/dos-devcoach_188_soft_skill_bangun_mindset_positif_untuk_skill_progresif_mc_140325115131.png",
                "DevCoach 188: Soft Skill | Bangun Mindset Positif untuk Skill Progresif!"
            )
        )
        mHomeBinding?.imgsBanners?.setImageList(imgList, ScaleTypes.FIT)
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

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            mHomeBinding!!.pgrbarHome.visibility = View.VISIBLE
            mHomeBinding!!.rvEvents.alpha = 0.0F
        } else {
            mHomeBinding!!.pgrbarHome.visibility = View.GONE
            mHomeBinding!!.rvEvents.alpha = 1F
        }
    }

    private fun getEvents(event: List<ListEventsItem>) {
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