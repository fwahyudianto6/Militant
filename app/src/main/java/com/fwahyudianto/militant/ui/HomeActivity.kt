package com.fwahyudianto.militant.ui

//  Import Library
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.fwahyudianto.militant.R
import com.fwahyudianto.militant.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import de.hdodenhof.circleimageview.CircleImageView

/**
 * This software, all associated documentation, and all copies are CONFIDENTIAL INFORMATION of Kalpawreksa Teknologi Indonesia
 * https://www.fwahyudianto.id
 * ® Wahyudianto, Fajar
 * Email 	: me@fwahyudianto.id
 *
 * 	Date			User				Note
 *  -------------------------------------------------------------------------------------------------------------------------
 *  2025-03-15      fwahyudianto        Enhance: Implement NavOptions on Bottom Navigation View
 *  End Revised
 */

class HomeActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mHomeBinding: ActivityHomeBinding

    //  Properties
    private lateinit var civNavbarPhoto: CircleImageView
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(mHomeBinding.root)
        setSupportActionBar(mHomeBinding.appBarHome.toolbar)

        mHomeBinding.appBarHome.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }

        //  Set Nav Photo User
        civNavbarPhoto = mHomeBinding.navView.getHeaderView(0).findViewById(R.id.iv_navbar_photo)
        Glide.with(this)
            .load(resources.getString(R.string.developer_photo))
            .into(civNavbarPhoto)

        val navController = findNavController(R.id.nav_host_fragment_content_home)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_home, R.id.nav_club, R.id.nav_news, R.id.nav_events, R.id.nav_teams),
            mHomeBinding.drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        mHomeBinding.navView.setupWithNavController(navController)

        bottomNavigation = findViewById(R.id.bottom_nav_events_view)
        bottomNavigation.setOnItemSelectedListener { item ->
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.nav_host_fragment_content_home, true)
                .build()

            when (item.itemId) {
                R.id.imenu_home -> navController.navigate(R.id.nav_home, null, navOptions)
                R.id.imenu_upcoming_events -> navController.navigate(
                    R.id.nav_upcoming_events,
                    null,
                    navOptions
                )

                R.id.imenu_finished_events -> navController.navigate(
                    R.id.nav_finished_events,
                    null,
                    navOptions
                )
            }

            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)

        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home)

        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    //  Replace Fragment
    @Suppress("Unused")
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment_content_home, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    //  Close Fragment
    @Suppress("Unused")
    fun closeFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        transaction.remove(fragment)
        transaction.commit()
    }
}