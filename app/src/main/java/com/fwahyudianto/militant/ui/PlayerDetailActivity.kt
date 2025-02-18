package com.fwahyudianto.militant.ui

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fwahyudianto.militant.R
import com.fwahyudianto.militant.data.model.Player

/**
 * This software, all associated documentation, and all copies are CONFIDENTIAL INFORMATION of Kalpawreksa Teknologi Indonesia
 * https://www.fwahyudianto.id
 * ® Wahyudianto, Fajar
 * Email 	: me@fwahyudianto.id
 */

class PlayerDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_player_detail)

        val data = intent.getParcelableExtra<Player>("PlayerDetail")
        Log.d("Player's Detail Data", data?.iNoTshirt.toString())

        val imgPhoto: ImageView = findViewById(R.id.img_photo)
        val tvName: TextView = findViewById(R.id.tv_nick_name)
        val tvNo: TextView = findViewById(R.id.tv_number)
        val tvTshirt: TextView = findViewById(R.id.tv_number_tshirt)
        val tvFullName: TextView = findViewById(R.id.tv_full_name)
        val tvFullNameDescription: TextView = findViewById(R.id.tv_full_name_description)
        val tvDescription: TextView = findViewById(R.id.tv_description)

        imgPhoto.setImageResource(data?.iPlayerPhoto!!)
        tvNo.text = data?.iNoTshirt.toString()
        tvTshirt.text = data?.iNoTshirt.toString()
        tvName.text = data?.strPlayerName.toString()
        tvFullName.text = data?.strPlayerFullName.toString()
        tvFullNameDescription.text = data?.strPlayerName.toString()
        tvDescription.text = data?.strPlayerDescription.toString()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.sv_detail)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}