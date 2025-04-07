package com.fwahyudianto.militant.foundation.adapter

//  Import Library
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fwahyudianto.militant.R
import com.fwahyudianto.militant.data.response.ListEventsItem
import com.fwahyudianto.militant.databinding.ItemEventsListBinding

/**
 *  This software, all associated documentation, and all copies are CONFIDENTIAL INFORMATION of Kalpawreksa Teknologi Indonesia
 *  https://www.fwahyudianto.id
 *  ® Wahyudianto, Fajar
 *  Email 	: me@fwahyudianto.id
 */

class EventListApiAdapter(private val eventsList: List<ListEventsItem>) :
    RecyclerView.Adapter<EventListApiAdapter.ListViewHolder>() {
    //  Initial Properties
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemEventsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(eventsList[position])
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(eventsList[holder.bindingAdapterPosition]) }
    }

    override fun getItemCount(): Int = eventsList.size

    class ListViewHolder(private var binding: ItemEventsListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ListEventsItem) {
            binding.finishedEvents = data
            binding.executePendingBindings()
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ListEventsItem)
    }

    companion object {
        @JvmStatic
        @BindingAdapter("setAvatar")
        fun setAvatar(imgLogo: ImageView, url: String) {
            Glide.with(imgLogo)
                .load(url)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .fallback(R.drawable.ic_launcher_foreground)
                .into(imgLogo)
        }
    }
}