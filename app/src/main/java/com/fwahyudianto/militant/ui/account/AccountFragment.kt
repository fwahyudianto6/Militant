package com.fwahyudianto.militant.ui.account

//  Import Library
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.fwahyudianto.militant.R
import com.fwahyudianto.militant.databinding.FragmentAccountBinding
import com.fwahyudianto.militant.utils.SettingPreferences
import com.fwahyudianto.militant.utils.ViewModelFactory
import com.fwahyudianto.militant.utils.dataStore

class AccountFragment : Fragment() {
    private var mAccountBinding: FragmentAccountBinding? = null
    private val oBinding get() = mAccountBinding!!

    private var isListenerActive = false
    private var isSwitchingTheme = false

    private val mAccountViewModel: AccountViewModel by viewModels {
        ViewModelFactory.getInstance(
            SettingPreferences.getInstance(requireContext().dataStore)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        mAccountBinding = FragmentAccountBinding.inflate(inflater, container, false)

        return oBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //  User Photo
        val urlUserPhoto = resources.getString(R.string.developer_photo)
        Glide.with(this).load(urlUserPhoto).into(oBinding.accountCivUser)

        // Clear old listener
        oBinding.accountSwtTheme.setOnCheckedChangeListener(null)

        //  Theme toggle
        mAccountViewModel.getThemeSettings().observe(viewLifecycleOwner) { isDark ->
            isListenerActive = false
            oBinding.accountSwtTheme.isChecked = isDark
            isListenerActive = true
        }

        // Handle toggle switch
        oBinding.accountSwtTheme.setOnCheckedChangeListener { _, isChecked ->
            if (isListenerActive && !isSwitchingTheme) {
                isSwitchingTheme = true

                mAccountViewModel.saveThemeSetting(isChecked)

                AppCompatDelegate.setDefaultNightMode(
                    if (isChecked) AppCompatDelegate.MODE_NIGHT_YES
                    else AppCompatDelegate.MODE_NIGHT_NO
                )

                val fadeOut = AlphaAnimation(1f, 0f).apply {
                    duration = 300
                    fillAfter = true
                }

                oBinding.root.startAnimation(fadeOut)

                fadeOut.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation?) {}
                    override fun onAnimationEnd(animation: Animation?) {
                        Handler(Looper.getMainLooper()).postDelayed({
                            //  Recreate Activity
                            activity?.recreate()
                        }, 100)
                    }

                    override fun onAnimationRepeat(animation: Animation?) {}
                })
            }

            Log.d(
                "Militan-AccountFragment",
                "Change to theme : ${if (isChecked) "Dark" else "Light"}"
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mAccountBinding = null
    }
}