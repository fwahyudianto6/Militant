package com.fwahyudianto.militant.ui

//  Import Library
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fwahyudianto.militant.R
import com.fwahyudianto.militant.data.model.Player
import com.fwahyudianto.militant.databinding.ActivityPlayerDetailBinding

/**
 * This software, all associated documentation, and all copies are CONFIDENTIAL INFORMATION of Kalpawreksa Teknologi Indonesia
 * https://www.fwahyudianto.id
 * ® Wahyudianto, Fajar
 * Email 	: me@fwahyudianto.id
 *
 * 	Date			User				Note
 *  -------------------------------------------------------------------------------------------------------------------------
 *  2025-02-19      fwahyudianto        Enhance:
 *                                      -   implement View Binding
 *                                      -   implement Action Share
 *  End Revised
 */

class PlayerDetailActivity : AppCompatActivity() {
    //  Initialize
    private lateinit var oBinding: ActivityPlayerDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        oBinding = ActivityPlayerDetailBinding.inflate(layoutInflater)
        setContentView(oBinding.root)

        val dtPlayer = intent.getParcelableExtra<Player>("PLAYER_DETAIL")

        oBinding.imgPhoto.setImageResource(dtPlayer?.iPlayerPhoto!!)
        oBinding.tvNickName.text = dtPlayer?.strPlayerName.toString()
        oBinding.tvNumber.text = dtPlayer?.iTshirt.toString()
        oBinding.tvNumberTshirt.text = dtPlayer?.iTshirt.toString()
        oBinding.tvFullName.text = dtPlayer?.strPlayerFullName.toString()
        oBinding.tvFullNameDescription.text = dtPlayer?.strPlayerName.toString()
        oBinding.tvDescription.text = dtPlayer?.strPlayerDescription.toString()

        val detailText =
            "⚡ " + dtPlayer?.strPlayerName.toString() + " - Bintang AC Milan ❤\uFE0F\uD83D\uDDA4! ⚡\n #ACMilan #Rossoneri\""
        oBinding.btnShare.setOnClickListener {
            //  Toast.makeText(this, "Share: " + dtPlayer?.strPlayerName.toString(), Toast.LENGTH_SHORT).show()
            shareContent(detailText)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.sv_detail)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    //  Share to External
    private fun shareContent(content: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, content)
        }
        startActivity(Intent.createChooser(intent, "Bagikan melalui"))
    }
}