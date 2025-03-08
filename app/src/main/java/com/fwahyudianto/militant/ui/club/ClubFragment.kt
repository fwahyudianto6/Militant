package com.fwahyudianto.militant.ui.club

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fwahyudianto.militant.databinding.FragmentClubBinding

class ClubFragment : Fragment() {
    companion object {
        // fun newInstance() = ClubFragment()
    }

    //  Properties
    private val mClubViewModel: ClubViewModel by viewModels()
    private var mClubBinding: FragmentClubBinding? = null
    private val oBinding get() = mClubBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mClubBinding = FragmentClubBinding.inflate(inflater, container, false)

        return oBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mClubBinding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //  Set View Model
        mClubViewModel.dtClub.observe(viewLifecycleOwner) {
            if (it != null) {
                oBinding.tvClub.text = it
            }
        }
    }
}