package com.fwahyudianto.militant.foundation.adapter

//  Import Library
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fwahyudianto.militant.data.model.Player
import com.fwahyudianto.militant.databinding.ItemHallOfFameListBinding

/**
 * This software, all associated documentation, and all copies are CONFIDENTIAL INFORMATION of Kalpawreksa Teknologi Indonesia
 * https://www.fwahyudianto.id
 * ® Wahyudianto, Fajar
 * Email 	: me@fwahyudianto.id
 *
 * 	Date			User				Note
 *  -------------------------------------------------------------------------------------------------------------------------
 *  2025-02-19      fwahyudianto        #1 Enhance: implement View Binding
 *  End Revised
 */

//  Player List Adapter constructor
class PlayerListAdapter(private val oPlayerList: ArrayList<Player>) :
    RecyclerView.Adapter<PlayerListAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: Player)
    }

    //  ListViewHolder constructor
    class ListViewHolder(var binding: ItemHallOfFameListBinding) :
        RecyclerView.ViewHolder(binding.root)

    //  Implement member RecyclerView.Adapter
    override fun onCreateViewHolder(p_oViewGroup: ViewGroup, p_iViewType: Int): ListViewHolder {
        val bindingView = ItemHallOfFameListBinding.inflate(
            LayoutInflater.from(p_oViewGroup.context),
            p_oViewGroup,
            false
        )

        return ListViewHolder(bindingView)
    }

    //  Implement member RecyclerView.Adapter
    override fun getItemCount(): Int = oPlayerList.size

    //  Implement member RecyclerView.Adapter
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (no, photo, name, full_name, description) = oPlayerList[position]
        val maxLength = 85

        holder.binding.imgItemPhoto.setImageResource(photo)
        holder.binding.tvItemNo.text = no.toString()
        holder.binding.tvItemName.text = name
        holder.binding.tvItemFullname.text = full_name
        holder.binding.tvItemDescription.text = if (description.length > maxLength) {
            description.substring(0, maxLength) + " ..."
        } else {
            description
        }

        //  Set OnClick Listener event
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(oPlayerList[holder.adapterPosition]) }
    }

    //  Method setOnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}