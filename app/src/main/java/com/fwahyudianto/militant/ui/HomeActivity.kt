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
 *  This software, all associated documentation, and all copies are CONFIDENTIAL INFORMATION of Kalpawreksa Teknologi Indonesia
 *  https://www.fwahyudianto.id
 *  ® Wahyudianto, Fajar
 *  Email 	: me@fwahyudianto.id
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
        setSupportActionBar(mHomeBinding.homeMappbar.homeMappbarToolbar)

        mHomeBinding.homeMappbar.homeMappbarFab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.home_mappbar_fab).show()
        }

        //  Set Nav Photo User
        civNavbarPhoto =
            mHomeBinding.homeMnavView.getHeaderView(0).findViewById(R.id.iv_navbar_photo)
        Glide.with(this)
            .load(resources.getString(R.string.developer_photo))
            .into(civNavbarPhoto)

        val navController = findNavController(R.id.home_mappbar_navhost_fragment)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_home, R.id.nav_club, R.id.nav_news, R.id.nav_events, R.id.nav_teams),
            mHomeBinding.homeMdwLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        mHomeBinding.homeMnavView.setupWithNavController(navController)

        bottomNavigation = findViewById(R.id.home_mappbar_bottom_nav_view)
        bottomNavigation.setOnItemSelectedListener { item ->
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.home_mappbar_navhost_fragment, true)
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
//        menuInflater.inflate(R.menu.home, menu)

        return super.onCreateOptionsMenu(menu)

//        val searchItem = menu.findItem(R.id.action_search)
//        val searchView = searchItem.actionView as SearchView
//
//        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
//        searchView.queryHint = "Search events ..."
//
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                // Implement the query submit behavior here
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                // Implement query text change behavior here
//                return true
//            }
//        })

//        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.home_mappbar_navhost_fragment)

        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    //  Replace Fragment
    @Suppress("Unused")
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.home_mappbar_navhost_fragment, fragment)
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