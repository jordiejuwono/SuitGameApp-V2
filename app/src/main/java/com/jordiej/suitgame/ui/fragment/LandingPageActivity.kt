package com.jordiej.suitgame.ui.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.jordiej.suitgame.R
import com.jordiej.suitgame.databinding.ActivityLandingPageBinding
import com.jordiej.suitgame.utils.FragmentAdapter

class LandingPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLandingPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        initViewPager()
    }

    private fun initViewPager() {
        val fragmentAdapter = FragmentAdapter(supportFragmentManager, lifecycle)
        fragmentAdapter.addFragment(
            SliderFragment(
                "Player VS Player",
                "In this mode you can play against your friend. First choose your hand choice, it will be hidden until your friend also done choosing their hand choice",
                R.drawable.ic_player_vs_player
            )
        )
        fragmentAdapter.addFragment(
            SliderFragment(
                "Player VS Computer",
                "In this mode you play against computer, computer hand choice will be random between hand, rock or scissors.",
                R.drawable.ic_robot_colored
            )
        )
        fragmentAdapter.addFragment(FormFragment())

        binding.vpFragment.apply {
            adapter = fragmentAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    when {
                        position == 0 -> {
                            binding.tvNext.visibility = View.VISIBLE
                            binding.tvPrevious.visibility = View.INVISIBLE
                            binding.tvNext.isEnabled = true
                            binding.tvPrevious.isEnabled = false
                        }
                        position < fragmentAdapter.itemCount - 1 -> {
                            binding.tvNext.visibility = View.VISIBLE
                            binding.tvPrevious.visibility = View.VISIBLE
                            binding.tvNext.isEnabled = true
                            binding.tvPrevious.isEnabled = true
                        }
                        position == fragmentAdapter.itemCount - 1 -> {
                            binding.tvNext.visibility = View.INVISIBLE
                            binding.tvPrevious.visibility = View.VISIBLE
                            binding.tvNext.isEnabled = false
                            binding.tvPrevious.isEnabled = true
                        }
                    }
                    super.onPageSelected(position)
                }
            })
        }
        binding.dotsIndicator.setViewPager2(binding.vpFragment)
        binding.tvNext.setOnClickListener {
            if (getNextIndex() != -1) {
                binding.vpFragment.setCurrentItem(getNextIndex(), true)
            }
        }
        binding.tvPrevious.setOnClickListener {
            if (getPreviousIndex() != -1) {
                binding.vpFragment.setCurrentItem(getPreviousIndex(), true)
            }
        }
    }

    private fun getPreviousIndex(): Int {
        val currentPage = binding.vpFragment.currentItem
        return if (currentPage - 1 >= 0) {
            currentPage - 1
        } else {
            -1
        }
    }

    private fun getNextIndex(): Int {
        val maxPages = binding.vpFragment.adapter?.itemCount
        val currentIndex = binding.vpFragment.currentItem
        var selectedIndex = -1
        maxPages?.let {
            if (currentIndex + 1 < maxPages) {
                selectedIndex = currentIndex + 1
            }
        }
        return selectedIndex
    }
}