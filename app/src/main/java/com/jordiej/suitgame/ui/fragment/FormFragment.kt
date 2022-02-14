package com.jordiej.suitgame.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.jordiej.suitgame.R
import com.jordiej.suitgame.databinding.FragmentFormBinding
import com.jordiej.suitgame.preference.UserPreference
import com.jordiej.suitgame.ui.game.GameActivity
import com.jordiej.suitgame.ui.menu.MenuActivity

class FormFragment : Fragment() {

    private lateinit var binding: FragmentFormBinding
    private val userPreference: UserPreference? by lazy {
        context?.let { UserPreference(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFormBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setPrefilledData()
        setOnClickListener()
    }

    private fun setPrefilledData() {
        userPreference?.let {
            binding.etName.setText(it.name.orEmpty())
        }
    }

    private fun isFormFilled(): Boolean {
        val name = binding.etName.text.toString().trim()
        var isFormFilled = true

        if (name.isEmpty()) {
            isFormFilled = false
        }
        return isFormFilled
    }

    private fun setOnClickListener() {
        binding.btnConfirm.setOnClickListener {
            if (isFormFilled()) {
                userPreference?.name = binding.etName.text.toString()
                val intent = Intent(context, MenuActivity::class.java)
                intent.flags =  Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                Snackbar.make(binding.root, "Name Cannot be Empty!", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

}