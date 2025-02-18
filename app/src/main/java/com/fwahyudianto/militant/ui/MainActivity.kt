package com.fwahyudianto.militant.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fwahyudianto.militant.R
import com.fwahyudianto.militant.data.model.Player
import com.fwahyudianto.militant.foundation.adapter.PlayerListAdapter

/**
 * This software, all associated documentation, and all copies are CONFIDENTIAL INFORMATION of Kalpawreksa Teknologi Indonesia
 * https://www.fwahyudianto.id
 * ® Wahyudianto, Fajar
 * Email 	: me@fwahyudianto.id
 */

class MainActivity : AppCompatActivity() {
    //  Initialize
    private lateinit var oRvPlayer: RecyclerView
    private val arrPlayerList = ArrayList<Player>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        installSplashScreen()
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        oRvPlayer = findViewById(R.id.rv_players)
        oRvPlayer.setHasFixedSize(true)

        arrPlayerList.addAll(getPlayer())
        showRecyclerList()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    //  Initial Option Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)

        return super.onCreateOptionsMenu(menu)
    }

    //  Selected Option Menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.imenu_list -> {
                oRvPlayer.layoutManager = LinearLayoutManager(this)
            }
            R.id.imenu_grid -> {
                oRvPlayer.layoutManager = GridLayoutManager(this, 2)
            }
            R.id.imenu_about -> {
                val oIntAbout = Intent(this@MainActivity, AboutActivity::class.java)
                startActivity(oIntAbout)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    //  Get Player List
    @SuppressLint("Recycle")
    private fun getPlayer(): ArrayList<Player> {
        val dtNo = resources.getIntArray(R.array.arrPlayersNo)
        val dtPhoto = resources.obtainTypedArray(R.array.arrPlayersPhoto)
        val dtName = resources.getStringArray(R.array.arrPlayersName)
        val dtFullName = resources.getStringArray(R.array.arrPlayersFullName)
        val dtDescription = resources.getStringArray(R.array.arrPlayersDescription)
        val lsPlayer = ArrayList<Player>()

        for (i in dtName.indices) {
            val oPlayer = Player(dtNo[i], dtPhoto.getResourceId(i, -1), dtName[i], dtFullName[i], dtDescription[i])
            lsPlayer.add(oPlayer)
        }

        return lsPlayer
    }

    //  Show Player List
    private fun showRecyclerList() {
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            oRvPlayer.layoutManager = GridLayoutManager(this, 2)
        } else {
            oRvPlayer.layoutManager = LinearLayoutManager(this)
        }

        val lsPlayerAdapter = PlayerListAdapter(arrPlayerList)
        oRvPlayer.adapter = lsPlayerAdapter

        //  Add Toast on Item
        lsPlayerAdapter.setOnItemClickCallback(object : PlayerListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Player) {
                getDetailPlayer(data)
            }
        })
    }

    //  Show Player selected
    private fun showSelectedItem(oPlayer: Player) {
        Toast.makeText(this, "You choose the Player: " + oPlayer.strPlayerName, Toast.LENGTH_SHORT).show()
    }

    //  Move to Detail Player Page
    private fun getDetailPlayer(oPlayer: Player) {
        val oIntPlayerDetail = Intent(this@MainActivity, PlayerDetailActivity::class.java)
        oIntPlayerDetail.putExtra("PlayerDetail", oPlayer)
        startActivity(oIntPlayerDetail)
    }
}