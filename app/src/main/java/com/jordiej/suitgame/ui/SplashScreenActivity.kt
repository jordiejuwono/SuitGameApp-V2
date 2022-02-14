package com.jordiej.suitgame.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.jordiej.suitgame.databinding.ActivitySplashScreenBinding
import com.jordiej.suitgame.ui.fragment.LandingPageActivity
import com.jordiej.suitgame.ui.game.GameActivity

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private val progressBar: Long = 3800

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.llLogo.alpha = 0f
        binding.ivRcs.alpha = 0f
        binding.pbSplashScreen.visibility = View.INVISIBLE
        binding.tvStarting.visibility = View.INVISIBLE

        binding.ivRcs.animate().setDuration(1200).alpha(1f)
        binding.llLogo.animate().setDuration(1200).alpha(1f).withEndAction {
            binding.pbSplashScreen.visibility = View.VISIBLE
            binding.tvStarting.visibility = View.VISIBLE
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, LandingPageActivity::class.java))
                finish()
            }, progressBar)
        }
    }
}