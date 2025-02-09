package com.fwahyudianto.militant.foundation.adapter

//  Import Library
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
    //  ListViewHolder constructor
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        val tvName: TextView = itemView.findViewById(R.id.tv_item_name)
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
        val (name, description, photo) = oPlayerList[position]
        holder.imgPhoto.setImageResource(photo)
        holder.tvName.text = name
        holder.tvDescription.text = description

        //  Set OnClick Listener event
        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "You choose " + oPlayerList[holder.adapterPosition].name, Toast.LENGTH_SHORT).show()
        }
    }
}