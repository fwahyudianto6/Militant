package com.fwahyudianto.militant.foundation.adapter

//  Import Library
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fwahyudianto.militant.data.model.Event
import com.fwahyudianto.militant.databinding.ItemEventsListBinding

/**
 *  This software, all associated documentation, and all copies are CONFIDENTIAL INFORMATION of Kalpawreksa Teknologi Indonesia
 *  https://www.fwahyudianto.id
 *  ® Wahyudianto, Fajar
 *  Email 	: me@fwahyudianto.id
 *
 * 	Date			User				Note
 *  -------------------------------------------------------------------------------------------------------------------------
 *  End Revised
 */

//  Events List Adapter constructor
@Suppress("Unused")
class EventListAdapter(private val oEventList: ArrayList<Event>) :
    RecyclerView.Adapter<EventListAdapter.ListViewHolder>() {

    //  ListViewHolder constructor
    class ListViewHolder(var binding: ItemEventsListBinding) : RecyclerView.ViewHolder(binding.root)

    //  Implement member RecyclerView.Adapter
    override fun onCreateViewHolder(pViewGroup: ViewGroup, pViewType: Int): ListViewHolder {
        val bindingView = ItemEventsListBinding.inflate(
            LayoutInflater.from(pViewGroup.context),
            pViewGroup,
            false
        )

        return ListViewHolder(bindingView)
    }

    //  Implement member RecyclerView.Adapter
    override fun getItemCount(): Int = oEventList.size

    //  Implement member RecyclerView.Adapter
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (image, category, name, summary) = oEventList[position]
        val maxLength = 75

//        holder.binding.imgItemEventsImage.setImageResource(image)
        Glide.with(holder.itemView.context)
            .load(image)
            .into(holder.binding.imgItemEventsImage)
        holder.binding.tvItemEventsCategory.text = category
        holder.binding.tvItemEventsName.text = name
        holder.binding.tvItemEventsSummary.text = if (summary.length > maxLength) {
            summary.substring(0, maxLength) + " ..."
        } else {
            summary
        }
    }
}