package com.fwahyudianto.militant.foundation.adapter

//  Import Library
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fwahyudianto.militant.data.model.Player
import com.fwahyudianto.militant.databinding.ItemHallOfFameListBinding
import java.util.Locale

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
    override fun onCreateViewHolder(pViewGroup: ViewGroup, pViewType: Int): ListViewHolder {
        val bindingView = ItemHallOfFameListBinding.inflate(
            LayoutInflater.from(pViewGroup.context),
            pViewGroup,
            false
        )

        return ListViewHolder(bindingView)
    }

    //  Implement member RecyclerView.Adapter
    override fun getItemCount(): Int = oPlayerList.size

    //  Implement member RecyclerView.Adapter
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (no, photo, name, fullName, description) = oPlayerList[position]
        val maxLength = 85

        holder.binding.imgItemPhoto.setImageResource(photo)
        holder.binding.tvItemNo.text = String.format(Locale.getDefault(), "%d", no)
        holder.binding.tvItemName.text = name
        holder.binding.tvItemFullname.text = fullName
        holder.binding.tvItemDescription.text = if (description.length > maxLength) {
            description.substring(0, maxLength) + " ..."
        } else {
            description
        }

        //  Set OnClick Listener event
        holder.itemView.setOnClickListener {
            @Suppress("DEPRECATION")
            onItemClickCallback.onItemClicked(oPlayerList[holder.adapterPosition])
        }
    }

    //  Method setOnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}