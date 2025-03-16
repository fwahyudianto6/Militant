package com.fwahyudianto.militant.ui.news

//  Import Library
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fwahyudianto.militant.databinding.FragmentNewsBinding

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

class NewsFragment : Fragment() {
    companion object {
        // fun newInstance() = NewsFragment()
    }

    //  Properties
    private val mNewsViewModel: NewsViewModel by viewModels()
    private var mNewsBinding: FragmentNewsBinding? = null
    private val oBinding get() = mNewsBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mNewsBinding = FragmentNewsBinding.inflate(layoutInflater, container, false)

        return oBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mNewsBinding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //  Set View Model
        mNewsViewModel.dtNews.observe(viewLifecycleOwner) {
            if (it != null) {
                oBinding.tvNews.text = it
            }
        }
    }
}