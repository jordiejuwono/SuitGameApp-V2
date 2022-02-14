package com.jordiej.suitgame.ui.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.jordiej.suitgame.databinding.ActivityMenuBinding
import com.jordiej.suitgame.preference.UserPreference
import com.jordiej.suitgame.ui.game.GameActivity

class MenuActivity : AppCompatActivity() {

    private val TAG = MenuActivity::class.simpleName
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding()
        setOnClickListener()
    }

    private fun viewBinding() {
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvUsername.text = UserPreference(this).name
        supportActionBar?.hide()
    }

    private fun setOnClickListener() {
        binding.llPvc.setOnClickListener {
            GameActivity.forStartActivity(this, GameActivity.PLAYER_VS_COM_MODE)
            Toast.makeText(this, "Player VS Computer Starting...", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "Player vs Computer Mode Starting...")
        }
        binding.llPvp.setOnClickListener{
            GameActivity.forStartActivity(this, GameActivity.PLAYER_VS_PLAYER_MODE)
            Toast.makeText(this, "Player VS Player Starting...", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "Player vs Player Mode Starting...")
        }
    }

}