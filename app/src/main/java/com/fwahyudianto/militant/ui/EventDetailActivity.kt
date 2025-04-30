package com.fwahyudianto.militant.ui

//  Import Library
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.fwahyudianto.militant.R
import com.fwahyudianto.militant.data.response.ListEventsItem
import com.fwahyudianto.militant.databinding.ActivityEventDetailBinding
import com.fwahyudianto.militant.ui.events.EventDetailViewModel
import com.fwahyudianto.militant.utils.HelperDateTime
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Suppress("DEPRECATION")
class EventDetailActivity : AppCompatActivity() {
    //  Initialize
    private lateinit var oDetailBinding: ActivityEventDetailBinding
    private lateinit var mViewModel: EventDetailViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        oDetailBinding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(oDetailBinding.root)
        setSupportActionBar(oDetailBinding.detailEventToolbar)

        val dtEvent = intent.getParcelableExtra<ListEventsItem>("EVENT_DETAIL")
        if (dtEvent == null) {
            Snackbar.make(oDetailBinding.root, "ID Event tidak tersedia!", Snackbar.LENGTH_LONG)
                .show()
            finish()
            return
        }

        mViewModel = ViewModelProvider(this)[EventDetailViewModel::class.java]
        mViewModel.mDetailEvents.observe(this) { showEventDetail(dtEvent) }
        mViewModel.errorMessage.observe(this) { error ->
            Snackbar.make(oDetailBinding.root, error, Snackbar.LENGTH_LONG).show()
        }

        mViewModel.getDetailEvents(dtEvent.id.toString())

        supportActionBar?.title = dtEvent.name

        //  Register Event
        //  setHideRegister(dtEvent?.endTime)
        oDetailBinding.btnDetailEventsRegistration.setOnClickListener {
            val urlRegister = dtEvent.link

            if (!urlRegister.isNullOrBlank()) {
                val intBrowser = Intent(Intent.ACTION_VIEW, urlRegister.toUri())
                intBrowser.setPackage("com.android.chrome")

                try {
                    startActivity(intBrowser)
                } catch (e: ActivityNotFoundException) {
                    intBrowser.setPackage(null)
                    startActivity(intBrowser)
                }
            } else {
                Snackbar.make(
                    oDetailBinding.root,
                    "Registration link is not available!",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.events_detail_layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    @Suppress("Unused")
    private fun setHideRegister(strEndTime: String? = null) {
        strEndTime?.let { dtEventEndTime ->
            try {
                val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                val dtEventEnd: Date =
                    format.parse(dtEventEndTime) ?: Date(0)
                val dtCurrent = Date()

                if (dtCurrent.after(dtEventEnd)) {
                    oDetailBinding.btnDetailEventsRegistration.visibility = View.GONE
                }
            } catch (e: Exception) {
                Log.e("EventDetailActivity", "Error parsing endTime: ${e.message}")
                oDetailBinding.btnDetailEventsRegistration.visibility = View.GONE
            }
        }
    }

    private fun showEventDetail(event: ListEventsItem) {
        val remainingQuota = event.registrants?.let { event.quota?.minus(it) }

        oDetailBinding.apply {
            tvDetailEventsName.text = event.name
            tvDetailEventsOwner.text = getString(R.string.organizer) + event.ownerName
            tvDetailEventsTime.text =
                getString(R.string.time) + HelperDateTime.formatDateTime(event.beginTime!!)
            tvDetailEventsQouta.text = getString(R.string.qouta) + event.quota
            tvDetailEventsRemainingQouta.text = getString(R.string.remaining_quota, remainingQuota)
            tvDetailEventsDescription.text =
                Html.fromHtml(event.description, Html.FROM_HTML_MODE_COMPACT)

            Glide.with(this@EventDetailActivity)
                .load(event.imageLogo)
                .into(oDetailBinding.imgDetailEventPhoto)
        }
    }

    private fun updateFavoriteIcon(isFav: Boolean) {
        oDetailBinding.fabFavorite.setImageResource(
            if (isFav) R.drawable.ic_favorite
            else R.drawable.ic_favorite_outline
        )
    }

    companion object {
        const val EVENT_DETAIL = "EVENT_DETAIL"
        const val EVENT_DETAIL_ID = "EVENT_DETAIL_ID"
    }
}