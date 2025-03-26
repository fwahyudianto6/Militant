package com.fwahyudianto.militant.ui

//  Import Library
import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.fwahyudianto.militant.R
import com.fwahyudianto.militant.data.response.ListEventsItem
import com.fwahyudianto.militant.databinding.ActivityEventDetailBinding

@Suppress("DEPRECATION")
class EventDetailActivity : AppCompatActivity() {
    //  Initialize
    private lateinit var oDetailBinding: ActivityEventDetailBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        oDetailBinding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(oDetailBinding.root)
        setSupportActionBar(oDetailBinding.detailEventToolbar)

        val dtEvent = intent.getParcelableExtra<ListEventsItem>("EVENT_DETAIL")
        val remainingQuota = if (dtEvent != null) {
            dtEvent.quota - dtEvent.registrants
        } else 0

        //  Log.d("DEV-onCreate", dtEvent.toString())
        Glide.with(this)
            .load(dtEvent?.imageLogo)
            .into(oDetailBinding.imgDetailEventPhoto)
        oDetailBinding.tvDetailEventsName.text = dtEvent?.name
        oDetailBinding.tvDetailEventsOwner.text = "Organizer: " + dtEvent?.ownerName
        oDetailBinding.tvDetailEventsTime.text = "Time: " + dtEvent?.beginTime
        oDetailBinding.tvDetailEventsQouta.text = "Qouta: " + dtEvent?.quota
        oDetailBinding.tvDetailEventsRemainingQouta.text = "Remaining Qouta: $remainingQuota"
        oDetailBinding.tvDetailEventsDescription.text =
            Html.fromHtml(dtEvent?.description, Html.FROM_HTML_MODE_COMPACT)

        supportActionBar?.title = dtEvent?.name

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.events_detail_layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    companion object {
        const val EVENT_DETAIL = "EVENT_DETAIL"
    }
}