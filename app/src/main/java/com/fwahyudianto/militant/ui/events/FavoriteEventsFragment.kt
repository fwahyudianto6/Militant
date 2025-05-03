package com.fwahyudianto.militant.ui.events

//  Import Library
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fwahyudianto.militant.data.response.ListEventsItem
import com.fwahyudianto.militant.databinding.FragmentFavoriteEventsBinding
import com.fwahyudianto.militant.foundation.adapter.FavoriteListAdapter
import com.fwahyudianto.militant.utils.ViewModelFactory

class FavoriteEventsFragment : Fragment() {
    //  private lateinit var mRecyleViewEvent: RecyclerView
    private lateinit var mFavoriteEventsViewModel: FavoriteEventsViewModel
    private lateinit var mAdapter: FavoriteListAdapter

    //  Properties
    private var mFavoriteEventsBinding: FragmentFavoriteEventsBinding? = null
    private val oBinding get() = mFavoriteEventsBinding!!
    //  private var mFavorite = listOf<ListEventsItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mFavoriteEventsBinding = FragmentFavoriteEventsBinding.inflate(inflater, container, false)

        val factory = ViewModelFactory.getInstance(requireActivity().application)
        mFavoriteEventsViewModel = viewModels<FavoriteEventsViewModel> { factory }.value

        return oBinding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        mFavoriteEventsBinding = null
    }

    private fun setupRecyclerView() {
        mAdapter = FavoriteListAdapter()
        oBinding.favoriteEventsRvitem.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = this@FavoriteEventsFragment.mAdapter
        }
    }

    private fun observeViewModel() {
        mFavoriteEventsViewModel.getFavEvents().observe(viewLifecycleOwner) { events ->
            val items = arrayListOf<ListEventsItem>()
            events.map {
                val item = ListEventsItem(
                    id = it.id,
                    name = it.name, category = it.category,
                    imageLogo = it.image,
                    summary = it.summary
                )
                items.add(item)
            }
            mAdapter.submitList(items)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
    }
}