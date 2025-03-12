package com.fwahyudianto.militant.ui

//  Import Library
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.fwahyudianto.militant.R
import com.fwahyudianto.militant.databinding.ActivityHomeBinding
import com.fwahyudianto.militant.ui.events.FinishedEventsFragment
import com.fwahyudianto.militant.ui.events.UpcomingEventsFragment
import com.fwahyudianto.militant.ui.home.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import de.hdodenhof.circleimageview.CircleImageView


/**
 * This software, all associated documentation, and all copies are CONFIDENTIAL INFORMATION of Kalpawreksa Teknologi Indonesia
 * https://www.fwahyudianto.id
 * ® Wahyudianto, Fajar
 * Email 	: me@fwahyudianto.id
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

//// 🔥 Override Navigation Handling to Clear Back Stack
//        mHomeBinding.navView.setNavigationItemSelectedListener { item ->
//            val destinationId = item.itemId
//            val currentDestination = navController.currentDestination?.id
//
//            if (currentDestination == destinationId) {
//                mHomeBinding.drawerLayout.closeDrawers()
//                return@setNavigationItemSelectedListener true
//            }
//
//            // ✅ Clear all fragments in back stack before navigating
//            navController.popBackStack(navController.graph.startDestinationId, true)
//
//            // ✅ Navigate to the selected destination
//            navController.navigate(destinationId)
//
//            mHomeBinding.drawerLayout.closeDrawers()
//            return@setNavigationItemSelectedListener true
//        }


        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }

        bottomNavigation = findViewById(R.id.bottom_nav_events_view)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.imenu_upcoming_events -> replaceFragment(UpcomingEventsFragment())
                R.id.imenu_finished_events -> replaceFragment(FinishedEventsFragment())
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

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        // Hapus semua fragment sebelumnya dari back stack
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.nav_host_fragment_content_home, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    fun closeFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        // ✅ Remove the fragment
        transaction.remove(fragment)

        // ✅ Commit the transaction
        transaction.commit()
    }
}