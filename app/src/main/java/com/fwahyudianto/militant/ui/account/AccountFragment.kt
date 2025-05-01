package com.fwahyudianto.militant.ui.account

//  Import Library
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            if (isListenerActive) {
                mAccountViewModel.saveThemeSetting(isChecked)
            }

            Log.d(
                "Militan-AccountFragment",
                "User ganti tema ke: ${if (isChecked) "Dark" else "Light"}"
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mAccountBinding = null
    }
}