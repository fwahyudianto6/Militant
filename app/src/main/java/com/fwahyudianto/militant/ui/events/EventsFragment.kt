package com.fwahyudianto.militant.ui.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import com.fwahyudianto.militant.databinding.FragmentEventsBinding

class EventsFragment : Fragment() {
    companion object {
        // fun newInstance() = EventsFragment()
    }

    //  Properties
    private val mEventsViewModel: EventsViewModel by viewModels()
    private var mEventsBinding: FragmentEventsBinding? = null
    private val oBinding get() = mEventsBinding!!
    private lateinit var navController: NavController

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
//        // Get NavController from NavHostFragment
//        val navHostFragment =
//            childFragmentManager.findFragmentById(R.id.event_nav_host) as NavHostFragment
//        navController = navHostFragment.navController
//
//        // Set up Bottom Navigation with NavController
//        oBinding.bottomNavEventsView.setupWithNavController(navController)

//        oBinding.bottomNavEventsView.setOnItemSelectedListener { item ->
//            Log.d("BottomNav", "Clicked: ${item.itemId}")  // Debug Log
//            Log.d("DEV-bottomNavEventsView", "bottomNavEventsView: " + item.itemId + " " + item.title)
//
//            when (item.itemId) {
//                R.id.nav_upcoming_events -> replaceFragment(UpcomingEventsFragment())
//                R.id.nav_finished_events -> replaceFragment(FinishedEventsFragment())
//                else -> return@setOnItemSelectedListener false
//            }
//
//            true
//        }
    }

//    private fun replaceFragment(fragment: Fragment) {
//        parentFragmentManager.beginTransaction()
//            .replace(R.id.event_nav_host, fragment)
//            .addToBackStack(null)
//            .commit()
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        mEventsBinding = null
    }
}