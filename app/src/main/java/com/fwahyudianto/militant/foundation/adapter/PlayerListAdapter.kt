package com.fwahyudianto.militant.foundation.adapter

//  Import Library
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fwahyudianto.militant.R
import com.fwahyudianto.militant.data.model.Player

/**
 * This software, all associated documentation, and all copies are CONFIDENTIAL INFORMATION of Kalpawreksa Teknologi Indonesia
 * https://www.fwahyudianto.id
 * ® Wahyudianto, Fajar
 * Email 	: me@fwahyudianto.id
 */

//  Player List Adapter constructor
class PlayerListAdapter(private val oPlayerList: ArrayList<Player>) : RecyclerView.Adapter<PlayerListAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: Player)
    }

    //  ListViewHolder constructor
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNo: TextView = itemView.findViewById(R.id.tv_item_no)
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        val tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        val tvFullName: TextView = itemView.findViewById(R.id.tv_item_fullname)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_item_description)
    }

    //  Implement member RecyclerView.Adapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_hall_of_fame_list, parent, false)

        return ListViewHolder(view)
    }

    //  Implement member RecyclerView.Adapter
    override fun getItemCount(): Int = oPlayerList.size

    //  Implement member RecyclerView.Adapter
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (no, photo, name, full_name, description) = oPlayerList[position]
        val maxLength = 85

        holder.imgPhoto.setImageResource(photo)
        holder.tvNo.text = no.toString()
        holder.tvName.text = name
        holder.tvFullName.text = full_name
        holder.tvDescription.text = if (description.length > maxLength) {
            description.substring(0, maxLength) + " ..."
        } else {
            description
        }

        //  Set OnClick Listener event
//        holder.itemView.setOnClickListener {
//            Toast.makeText(holder.itemView.context, "You choose " + oPlayerList[holder.adapterPosition].name, Toast.LENGTH_SHORT).show()
//        }

        //  Set OnClick Listener event
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(oPlayerList[holder.adapterPosition]) }
    }

    //  Method setOnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}