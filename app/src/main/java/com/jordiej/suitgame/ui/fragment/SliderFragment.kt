package com.jordiej.suitgame.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.jordiej.suitgame.databinding.FragmentSliderBinding

class SliderFragment(
    private val gameModes: String,
    private val description: String,
    private val imageModes: Int
) : Fragment() {

    private lateinit var binding: FragmentSliderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSliderBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindDataFragment()
    }

    private fun bindDataFragment() {
        binding.tvModes.text = gameModes
        binding.tvDescription.text = description
        context?.let {
            Glide.with(it)
                .load(imageModes)
                .into(binding.ivModes)
        }
    }

}