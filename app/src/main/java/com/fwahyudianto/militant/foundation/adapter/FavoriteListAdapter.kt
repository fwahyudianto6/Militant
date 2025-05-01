package com.fwahyudianto.militant.foundation.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fwahyudianto.militant.data.response.ListEventsItem
import com.fwahyudianto.militant.databinding.ItemFavoriteEventsListBinding
import com.fwahyudianto.militant.ui.EventDetailActivity

class FavoriteListAdapter :
    ListAdapter<ListEventsItem, FavoriteListAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    class ViewHolder(private val binding: ItemFavoriteEventsListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListEventsItem) {
            binding.tvItemEventsName.text = item.name
            binding.tvItemEventsCategory.text = item.category
            binding.tvItemEventsSummary.text = item.summary
            Glide.with(binding.root.context)
                .load(item.imageLogo)
                .into(binding.imgItemEventsImage)

            binding.root.setOnClickListener {
                val intentDetail = Intent(binding.root.context, EventDetailActivity::class.java)

                intentDetail.putExtra(EventDetailActivity.EVENT_DETAIL, item)
                binding.root.context.startActivity(intentDetail)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemFavoriteEventsListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}