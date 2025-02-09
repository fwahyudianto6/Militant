package com.fwahyudianto.militant

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    //  Get Player List
    @SuppressLint("Recycle")
    private fun getPlayer(): ArrayList<Player> {
        val dataName = resources.getStringArray(R.array.arrPlayersName)
        val dataDescription = resources.getStringArray(R.array.arrPlayersDescription)
        val dataPhoto = resources.obtainTypedArray(R.array.arrPlayersPhoto)
        val lsPlayer = ArrayList<Player>()

        for (i in dataName.indices) {
            val hero = Player(dataName[i], dataDescription[i], dataPhoto.getResourceId(i, -1))
            lsPlayer.add(hero)
        }

        return lsPlayer
    }

    //  Show Player List
    private fun showRecyclerList() {
        oRvPlayer.layoutManager = LinearLayoutManager(this)

        val listHeroAdapter = PlayerListAdapter(arrPlayerList)
        oRvPlayer.adapter = listHeroAdapter
    }
}