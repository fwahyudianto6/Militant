package com.fwahyudianto.militant.ui.home

//  Import Library
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.fwahyudianto.militant.R
import com.fwahyudianto.militant.data.model.Event
import com.fwahyudianto.militant.databinding.FragmentHomeBinding
import com.fwahyudianto.militant.foundation.adapter.EventListAdapter

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
    private val mArrEventsList = ArrayList<Event>()

    private var mHomeBinding: FragmentHomeBinding? = null
    private val oBinding get() = mHomeBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)

        mRecyleViewEvent = oBinding.rvEvents
        mRecyleViewEvent.setHasFixedSize(true)

        if (savedInstanceState == null) {
            mArrEventsList.clear()
            mArrEventsList.addAll(getEvent())

            mRecyleViewEvent.layoutManager = LinearLayoutManager(this.requireContext())

            val lsEventAdapter = EventListAdapter(mArrEventsList)
            mRecyleViewEvent.adapter = lsEventAdapter
        }

        return oBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        homeViewModel.text.observe(viewLifecycleOwner) {
            oBinding.tvHome.text = it
        }

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
}