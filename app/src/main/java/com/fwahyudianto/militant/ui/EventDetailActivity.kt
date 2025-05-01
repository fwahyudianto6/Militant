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
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.fwahyudianto.militant.R
import com.fwahyudianto.militant.data.datasource.local.entity.FavoriteEvent
import com.fwahyudianto.militant.data.response.Event
import com.fwahyudianto.militant.data.response.ListEventsItem
import com.fwahyudianto.militant.databinding.ActivityEventDetailBinding
import com.fwahyudianto.militant.ui.events.EventDetailViewModel
import com.fwahyudianto.militant.ui.events.FavoriteEventsViewModel
import com.fwahyudianto.militant.utils.HelperDateTime
import com.fwahyudianto.militant.utils.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Suppress("DEPRECATION")
class EventDetailActivity : AppCompatActivity() {
    //  Initialize
    private lateinit var oDetailBinding: ActivityEventDetailBinding
    private lateinit var mDetailViewModel: EventDetailViewModel
    private lateinit var mFavoriteViewModel: FavoriteEventsViewModel

    private var mFavorite = Event()
    private var favoriteEvent: FavoriteEvent? = null
    private var bIsFavoriteState = false

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        oDetailBinding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(oDetailBinding.root)
        setSupportActionBar(oDetailBinding.detailEventToolbar)

        val dtEvent = intent.getParcelableExtra<ListEventsItem>("EVENT_DETAIL")
        if (dtEvent == null) {
            showNotification("Event ID not available!", null)
            finish()
            return
        }

        mDetailViewModel = ViewModelProvider(this)[EventDetailViewModel::class.java]
        observeViewModel(dtEvent.id!!)

        oDetailBinding.detailEventToolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val factory = ViewModelFactory.getInstance(application)
        mFavoriteViewModel = viewModels<FavoriteEventsViewModel> { factory }.value

        mFavoriteViewModel.getDetailFavorite(dtEvent.id.toString())
//        mFavoriteViewModel.result.observe(this) { result ->
//            if (result != null) {
//                when (result) {
//                    is Result.Error -> TODO()
//                    Result.Loading -> TODO()
//                    is Result.Success<*> -> TODO()
//                }
//            }
//        }

        oDetailBinding.fabFavorite.setOnClickListener {
            val farEvent = favoriteEvent

            if (farEvent != null) {
                if (!bIsFavoriteState) {
                    val fav = FavoriteEvent(
                        id = farEvent.id,
                        image = farEvent.image,
                        category = farEvent.category,
                        name = farEvent.name,
                        summary = farEvent.summary,
                        isFavorite = true
                    )
                    mFavoriteViewModel.insertFavEvent(fav)
                    showNotification("Favorite data successfully added!", null)
                } else {
                    mFavoriteViewModel.deleteByEventId(farEvent.id!!)
                    showNotification("Favorite data successfully removed!", null)
                }

                mFavoriteViewModel.isFavorited(farEvent.id!!).observe(this) { favorites ->
                    val isFavorited = favorites.isNotEmpty()

                    bIsFavoriteState = isFavorited
                    updateFavoriteIcon(isFavorited)
                }
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

    @SuppressLint("SetTextI18n")
    private fun observeViewModel(eventId: Int) {
        mDetailViewModel.getDetailEvents(eventId.toString())
        mDetailViewModel.mDetailEvents.observe(this) { detailEvent ->
            mFavorite = detailEvent

            if (detailEvent != null) {
                with(oDetailBinding) {
                    val remainingQuota =
                        detailEvent.registrants?.let { detailEvent.quota?.minus(it) }

                    tvDetailEventsName.text = detailEvent.name
                    tvDetailEventsOwner.text =
                        getString(R.string.organizer) + detailEvent.ownerName
                    tvDetailEventsTime.text =
                        getString(R.string.time) + HelperDateTime.formatDateTime(detailEvent.beginTime!!)
                    tvDetailEventsQouta.text = getString(R.string.qouta) + detailEvent.quota
                    tvDetailEventsRemainingQouta.text =
                        getString(R.string.remaining_quota, remainingQuota)
                    tvDetailEventsDescription.text =
                        Html.fromHtml(detailEvent.description, Html.FROM_HTML_MODE_COMPACT)

                    Glide.with(this@EventDetailActivity)
                        .load(detailEvent.imageLogo)
                        .into(oDetailBinding.imgDetailEventPhoto)

                    //  Register Event
                    //  setHideRegister(dtEvent?.endTime)
                    oDetailBinding.btnDetailEventsRegistration.setOnClickListener {
                        val urlRegister = detailEvent.link

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
                            showNotification("Registration link is not available!", null)
                        }
                    }

                    supportActionBar?.title = detailEvent.name
                }

                favoriteEvent = FavoriteEvent(
                    id = detailEvent.id,
                    image = detailEvent.imageLogo,
                    category = detailEvent.category,
                    name = detailEvent.name,
                    summary = detailEvent.summary,
                )

                mFavoriteViewModel.isFavorited(detailEvent.id!!).observe(this) { isFav ->
                    val isFavorited = isFav.isNotEmpty()

                    bIsFavoriteState = isFavorited
                    updateFavoriteIcon(isFavorited)
                }
            } else {
                showNotification("Data not found!", eventId.toString())
            }
        }
        mDetailViewModel.errorMessage.observe(this) { error ->
            Snackbar.make(oDetailBinding.root, error, Snackbar.LENGTH_LONG).show()
        }
    }

    @Suppress("Unused")
    private fun updateFavoriteIcon(isFav: Boolean) {
        oDetailBinding.fabFavorite.setImageResource(
            if (isFav) R.drawable.ic_favorite
            else R.drawable.ic_favorite_outline
        )
    }

    private fun showNotification(message: String, strObject: String?) {
        var message = message
        if (!strObject.isNullOrEmpty()) {
            message = "$message $strObject"
        }

        Snackbar.make(oDetailBinding.root, message, Snackbar.LENGTH_LONG).show()
    }

    companion object {
        const val EVENT_DETAIL = "EVENT_DETAIL"
        const val EVENT_DETAIL_ID = "EVENT_DETAIL_ID"
    }
}