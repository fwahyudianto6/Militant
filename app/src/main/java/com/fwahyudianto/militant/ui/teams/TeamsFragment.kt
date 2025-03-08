package com.fwahyudianto.militant.ui.teams

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fwahyudianto.militant.databinding.FragmentTeamsBinding

class TeamsFragment : Fragment() {
    companion object {
        // fun newInstance() = TeamsFragment()
    }

    //  Properties
    private val mTeamsViewModel: TeamsViewModel by viewModels()
    private var mTeamsBinding: FragmentTeamsBinding? = null
    private val mBinding get() = mTeamsBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mTeamsBinding = FragmentTeamsBinding.inflate(inflater, container, false)

        return mBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mTeamsBinding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //  Set View Model
        mTeamsViewModel.dtTeams.observe(viewLifecycleOwner) {
            if (it != null) {
                mBinding.tvTeams.text = it
            }
        }
    }
}