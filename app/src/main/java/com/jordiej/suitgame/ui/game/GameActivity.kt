package com.jordiej.suitgame.ui.game

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.jordiej.suitgame.R
import com.jordiej.suitgame.databinding.ActivityGameBinding
import com.jordiej.suitgame.databinding.CustomDialogFragmentBinding
import com.jordiej.suitgame.databinding.DialogBackBinding
import com.jordiej.suitgame.enum.GetWinner
import com.jordiej.suitgame.enum.HandInput
import com.jordiej.suitgame.preference.UserPreference
import com.jordiej.suitgame.ui.fragment.LandingPageActivity
import com.jordiej.suitgame.utils.WinningCondition
import com.shashank.sony.fancytoastlib.FancyToast
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    private val TAG = ActivityGameBinding::class.simpleName
    private lateinit var binding: ActivityGameBinding

    private lateinit var playerHand: HandInput
    private lateinit var enemyHand: HandInput
    private var playerScore = 0
    private var enemyScore = 0

    //setting up flags
    private var gameMode: Int = PLAYER_VS_COM_MODE
    private var pickTurn: GetWinner = GetWinner.PLAYER

    companion object {
        const val GAME_MODE = "game_mode"
        const val PLAYER_VS_COM_MODE = 0
        const val PLAYER_VS_PLAYER_MODE = 1

        @JvmStatic
        fun forStartActivity(context: Context, gameMode: Int) {
            val intent = Intent(context, GameActivity::class.java)
            intent.putExtra(GAME_MODE, gameMode)
            context.startActivity(intent)
        }
    }

    private fun getIntentValue() {
        gameMode = intent.extras?.getInt(GAME_MODE, 0) ?: 0
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding()
        getIntentValue()
        showDialogInstructions()
        setOnClickListener()
    }

    private fun viewBinding() {
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        if (gameMode == PLAYER_VS_COM_MODE) {
            binding.tvPlayer.text = UserPreference(this).name
        } else {
            binding.tvCom.text = getString(R.string.text_player_2)
        }
    }

    private fun showDialogInstructions() {
        val dialogView = CustomDialogFragmentBinding.inflate(layoutInflater)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView.root)
            .create()
        dialogBuilder.show()

        dialogView.tvGotIt.setOnClickListener {
            dialogBuilder.dismiss()
        }

    }

    private fun setEnableClickListener(enable: Boolean){
        if (enable){
            binding.flRock.isEnabled = true
            binding.flPaper.isEnabled = true
            binding.flScissors.isEnabled = true
        } else {
            binding.flRock.isEnabled = false
            binding.flPaper.isEnabled = false
            binding.flScissors.isEnabled = false
        }
    }

    private fun setOnClickListener() {
        //For PVP Mode
        if (gameMode == PLAYER_VS_PLAYER_MODE) {
            binding.tvScore.text = getString(R.string.text_player1_turn)
            binding.tvScore.textSize = 36F

            binding.flRock.setOnClickListener {
                if (pickTurn == GetWinner.PLAYER) {
                    if (playerScore != 3 && enemyScore != 3) {
                        playerHand = HandInput.ROCK
                        binding.ivPlayerHand.setImageResource(R.drawable.ic_check)
                        Toast.makeText(this, "Player 1 Done Choosing", Toast.LENGTH_SHORT).show()
                        binding.tvScore.text = getString(R.string.text_player2_turn)
                        pickTurn = GetWinner.ENEMY
                    }
                } else {
                    if (playerScore != 3 && enemyScore != 3) {
                        enemyHand = HandInput.ROCK
                        getPlayerHand()
                        Log.d(TAG, "Player 1 Choose: $playerHand")
                        getEnemyHand()
                        Log.d(TAG, "Player 2 Choose: $enemyHand")
                        getWinner()
                        binding.tvScore.text = getString(R.string.tv_score)
                        binding.tvScore.textSize = 45F
                        setEnableClickListener(false)
                        binding.llChoices.visibility = View.GONE
                        binding.tvNextRound.visibility = View.VISIBLE
                        gameFinished()
                    }
                }
            }

            binding.flPaper.setOnClickListener {
                if (pickTurn == GetWinner.PLAYER) {
                    if (playerScore != 3 && enemyScore != 3) {
                        playerHand = HandInput.PAPER
                        binding.ivPlayerHand.setImageResource(R.drawable.ic_check)
                        Toast.makeText(this, "Player 1 Done Choosing", Toast.LENGTH_SHORT).show()
                        binding.tvScore.text = getString(R.string.text_player2_turn)
                        pickTurn = GetWinner.ENEMY
                    }
                } else {
                    if (playerScore != 3 && enemyScore != 3) {
                        enemyHand = HandInput.PAPER
                        getPlayerHand()
                        Log.d(TAG, "Player 1 Choose: $playerHand")
                        getEnemyHand()
                        Log.d(TAG, "Player 2 Choose: $enemyHand")
                        getWinner()
                        binding.tvScore.text = getString(R.string.tv_score)
                        binding.tvScore.textSize = 45F
                        setEnableClickListener(false)
                        binding.llChoices.visibility = View.GONE
                        binding.tvNextRound.visibility = View.VISIBLE
                        gameFinished()
                    }
                }
            }

            binding.flScissors.setOnClickListener {
                if (pickTurn == GetWinner.PLAYER) {
                    if (playerScore != 3 && enemyScore != 3) {
                        playerHand = HandInput.SCISSORS
                        binding.ivPlayerHand.setImageResource(R.drawable.ic_check)
                        Toast.makeText(this, "Player 1 Done Choosing", Toast.LENGTH_SHORT).show()
                        binding.tvScore.text = getString(R.string.text_player2_turn)
                        pickTurn = GetWinner.ENEMY
                    }
                } else {
                    if (playerScore != 3 && enemyScore != 3) {
                        enemyHand = HandInput.SCISSORS
                        getPlayerHand()
                        Log.d(TAG, "Player 1 Choose: $playerHand")
                        getEnemyHand()
                        Log.d(TAG, "Player 2 Choose: $enemyHand")
                        getWinner()
                        binding.tvScore.text = getString(R.string.tv_score)
                        binding.tvScore.textSize = 45F
                        setEnableClickListener(false)
                        binding.llChoices.visibility = View.GONE
                        binding.tvNextRound.visibility = View.VISIBLE
                        gameFinished()
                    }
                }
            }
        } else {


            binding.flRock.setOnClickListener {
                if (playerScore != 3 && enemyScore != 3) {
                    //player select hand input
                    playerHand = HandInput.ROCK
                    showSelectedPlayerHand(HandInput.ROCK)
                    getPlayerHand()

                    //computer get random hand
                    getEnemyHand()

                    //logging
                    Log.d(TAG, "Player choose $playerHand")
                    Log.d(TAG, "Computer choose $enemyHand")

                    //get winner
                    getWinner()
                    gameFinished()
                }
            }

            binding.flPaper.setOnClickListener {
                if (playerScore != 3 && enemyScore != 3) {
                    playerHand = HandInput.PAPER
                    showSelectedPlayerHand(HandInput.PAPER)
                    getPlayerHand()
                    getEnemyHand()
                    Log.d(TAG, "Player choose $playerHand")
                    Log.d(TAG, "Computer choose $enemyHand")
                    getWinner()
                    gameFinished()
                }
            }

            binding.flScissors.setOnClickListener {
                if (playerScore != 3 && enemyScore != 3) {
                    playerHand = HandInput.SCISSORS
                    showSelectedPlayerHand(HandInput.SCISSORS)
                    getPlayerHand()
                    getEnemyHand()
                    Log.d(TAG, "Player choose $playerHand")
                    Log.d(TAG, "Computer choose $enemyHand")
                    getWinner()
                    gameFinished()
                }
            }
        }

        binding.tvNextRound.setOnClickListener {
            Toast.makeText(this, "Next Round Start!", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "Next Round")
            viewBinding()
            binding.tvPlayerScore.text = playerScore.toString()
            binding.tvComScore.text = enemyScore.toString()
            getIntentValue()
            pickTurn = GetWinner.PLAYER
            setOnClickListener()
        }

        binding.flInstructions.setOnClickListener {
            showDialogInstructions()
        }

        binding.flHome.setOnClickListener {
            openAlertDialogHome()
        }

        binding.ivReset.setOnClickListener {
            playerScore = 0
            enemyScore = 0

            if (gameMode == PLAYER_VS_PLAYER_MODE){
                pickTurn = GetWinner.PLAYER
            }

            viewBinding()
            setOnClickListener()
            FancyToast.makeText(
                this,
                "Game Reset Successfully!",
                FancyToast.LENGTH_SHORT,
                FancyToast.SUCCESS,
                false
            ).show()
            Log.d(TAG, "Game Reset Successfully!")
        }
    }

    private fun openAlertDialogHome() {
        val dialogView = DialogBackBinding.inflate(layoutInflater)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView.root)
            .create()
        dialogBuilder.show()

        dialogView.tvBackToMode.setOnClickListener {
            AlertDialog.Builder(this)
                .apply {
                    setTitle("Go to Mode Select")
                    setMessage("Are you sure? Your game will be restarted")
                    setPositiveButton("Yes") { _, _ ->
                        finish()
                        Log.d(TAG, "Back to Mode Select")
                    }
                    setNegativeButton("No") { dialog, _ ->
                        dialog.dismiss()
                    }
                }
                .create()
                .show()
        }

        dialogView.tvDismiss.setOnClickListener {
            val intent = Intent(this, LandingPageActivity::class.java)
            AlertDialog.Builder(this)
                .apply {
                    setTitle("Go to Homepage")
                    setMessage("Are you sure? Your game will be restarted")
                    setPositiveButton("Yes") { _, _ ->
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        Log.d(TAG, "Back to Homepage")
                    }
                    setNegativeButton("No") { dialog, _ ->
                        dialog.dismiss()
                    }
                }
                .create()
                .show()
        }

        dialogView.tvBack.setOnClickListener {
            dialogBuilder.dismiss()
        }
    }

    private fun showSelectedPlayerHand(selectedHand: HandInput) {
        val handRock: FrameLayout?
        val handPaper: FrameLayout?
        val handScissors: FrameLayout?
        val selected = AnimationUtils.loadAnimation(this, R.anim.anim_selected)

        handRock = binding.flRock
        handPaper = binding.flPaper
        handScissors = binding.flScissors

        when (selectedHand) {
            HandInput.ROCK -> {
                handRock.startAnimation(selected)
                handRock.setBackgroundResource(R.drawable.bg_selected)
                handPaper.setBackgroundResource(R.drawable.bg_bottom_right_shadow)
                handScissors.setBackgroundResource(R.drawable.bg_bottom_right_shadow)
            }
            HandInput.PAPER -> {
                handRock.setBackgroundResource(R.drawable.bg_bottom_right_shadow)
                handPaper.startAnimation(selected)
                handPaper.setBackgroundResource(R.drawable.bg_selected)
                handScissors.setBackgroundResource(R.drawable.bg_bottom_right_shadow)
            }
            HandInput.SCISSORS -> {
                handRock.setBackgroundResource(R.drawable.bg_bottom_right_shadow)
                handPaper.setBackgroundResource(R.drawable.bg_bottom_right_shadow)
                handScissors.startAnimation(selected)
                handScissors.setBackgroundResource(R.drawable.bg_selected)
            }
        }

    }

    private fun getPlayerHand() {
        val ltr = AnimationUtils.loadAnimation(this, R.anim.anim_left_to_right)
        val playerHandImage = binding.ivPlayerHand

        when (playerHand) {
            HandInput.ROCK -> {
                playerHandImage.setImageResource(R.drawable.batu)
                playerHandImage.startAnimation(ltr)
                playerHandImage.rotation = 90F
            }
            HandInput.PAPER -> {
                playerHandImage.setImageResource(R.drawable.kertas)
                playerHandImage.startAnimation(ltr)
                playerHandImage.rotation = 90F
            }
            HandInput.SCISSORS -> {
                playerHandImage.setImageResource(R.drawable.gunting)
                playerHandImage.startAnimation(ltr)
                playerHandImage.rotation = 110F
            }
        }
    }

    private fun getEnemyHand() {
        val enemyHand = binding.ivComHand
        val rtl = AnimationUtils.loadAnimation(this, R.anim.anim_right_to_left)

        if (gameMode == PLAYER_VS_PLAYER_MODE) {
            when (this.enemyHand){
                HandInput.ROCK -> {
                    enemyHand.setImageResource(R.drawable.batu)
                    enemyHand.startAnimation(rtl)
                    enemyHand.rotation = 90F
                    enemyHand.rotationY = 180F
                }
                HandInput.PAPER -> {
                    enemyHand.setImageResource(R.drawable.kertas)
                    enemyHand.startAnimation(rtl)
                    enemyHand.rotation = 90F
                    enemyHand.rotationY = 180F
                }
                HandInput.SCISSORS -> {
                    enemyHand.setImageResource(R.drawable.gunting)
                    enemyHand.startAnimation(rtl)
                    enemyHand.rotation = 110F
                    enemyHand.rotationY = 180F
                }
            }
        } else {
            val getRandomHand = Random.nextInt(0, 3)
            this.enemyHand = HandInput.values()[getRandomHand]

            when (this.enemyHand) {
                HandInput.ROCK -> {
                    enemyHand.setImageResource(R.drawable.batu)
                    enemyHand.startAnimation(rtl)
                    enemyHand.rotation = 90F
                    enemyHand.rotationY = 180F
                }
                HandInput.PAPER -> {
                    enemyHand.setImageResource(R.drawable.kertas)
                    enemyHand.startAnimation(rtl)
                    enemyHand.rotation = 90F
                    enemyHand.rotationY = 180F
                }
                HandInput.SCISSORS -> {
                    enemyHand.setImageResource(R.drawable.gunting)
                    enemyHand.startAnimation(rtl)
                    enemyHand.rotation = 110F
                    enemyHand.rotationY = 180F
                }
            }
        }


    }

    private fun getWinner() {

        when (WinningCondition().getWinningPlayer(playerHand, enemyHand)) {
            WinningCondition.DRAW -> {
                binding.tvVs.text = getString(R.string.text_result_draw)
                Log.d(TAG, "DRAW")
            }
            WinningCondition.PLAYER_WINS -> {
                if (gameMode == PLAYER_VS_PLAYER_MODE){
                    binding.tvVs.text = getString(R.string.text_player_1_win)
                } else {
                    binding.tvVs.text = getString(R.string.text_result_player_win)
                }
                playerScore++
                binding.tvPlayerScore.text = "$playerScore"
                Log.d(TAG, "PLAYER WIN THIS ROUND")
            }
            WinningCondition.COMPUTER_WINS -> {
                if (gameMode == PLAYER_VS_PLAYER_MODE){
                    binding.tvVs.text = getString(R.string.text_player_2_win)
                } else {
                    binding.tvVs.text = getString(R.string.text_result_com_win)
                }
                enemyScore++
                binding.tvComScore.text = "$enemyScore"
                Log.d(TAG, "OPPONENT WIN THIS ROUND")
            }
        }
        Log.d(TAG, "Score = (Player : $playerScore), (OPPONENT : $enemyScore)")

    }

    private fun showWinnerDialog(suitWinner: GetWinner) {
        val mDialogView: View?

        when (suitWinner) {
            GetWinner.PLAYER -> {
                mDialogView =
                    LayoutInflater.from(this).inflate(R.layout.dialog_player_wins, null)
                val mBuilder = AlertDialog.Builder(this)
                    .setView(mDialogView)
                val mAlertDialog = mBuilder.show()
                val playAgain = mAlertDialog.findViewById<TextView>(R.id.tv_play_again)

                playAgain.setOnClickListener {
                    playerScore = 0
                    enemyScore = 0
                    viewBinding()
                    if (gameMode == PLAYER_VS_PLAYER_MODE){
                        pickTurn = GetWinner.PLAYER
                    }
                    setOnClickListener()
                    mAlertDialog.dismiss()
                    FancyToast.makeText(
                        this,
                        getString(R.string.text_game_reset),
                        FancyToast.LENGTH_SHORT,
                        FancyToast.SUCCESS,
                        false
                    ).show()
                    Log.d(TAG, "Game Reset Successfully!")
                }

                val backToModes = mAlertDialog.findViewById<TextView>(R.id.tv_back_to_mode)
                backToModes.setOnClickListener {
                    finish()
                }

                val dismiss = mAlertDialog.findViewById<TextView>(R.id.tv_dismiss)
                dismiss.setOnClickListener {
                    val intent = Intent(this, LandingPageActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    mAlertDialog.dismiss()
                    startActivity(intent)
                    Toast.makeText(this, getString(R.string.text_back_home), Toast.LENGTH_SHORT).show()
                }
            }

            GetWinner.ENEMY -> {
                mDialogView = if (gameMode == PLAYER_VS_PLAYER_MODE){
                    LayoutInflater.from(this).inflate(R.layout.dialog_player_two_wins, null)
                } else {
                    LayoutInflater.from(this).inflate(R.layout.dialog_computer_wins, null)
                }

                val mBuilder = AlertDialog.Builder(this)
                    .setView(mDialogView)
                val mAlertDialog = mBuilder.show()
                val playAgain = mAlertDialog.findViewById<TextView>(R.id.tv_play_again)

                playAgain.setOnClickListener {
                    playerScore = 0
                    enemyScore = 0
                    viewBinding()
                    setOnClickListener()
                    mAlertDialog.dismiss()
                    FancyToast.makeText(
                        this,
                        getString(R.string.text_game_reset),
                        FancyToast.LENGTH_SHORT,
                        FancyToast.SUCCESS,
                        false
                    ).show()
                    Log.d(TAG, "Game Reset Successfully!")
                }

                val backToModes = mAlertDialog.findViewById<TextView>(R.id.tv_back_to_mode)
                backToModes.setOnClickListener {
                    finish()
                }

                val dismiss = mAlertDialog.findViewById<TextView>(R.id.tv_dismiss)
                dismiss.setOnClickListener {
                    val intent = Intent(this, LandingPageActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    mAlertDialog.dismiss()
                    startActivity(intent)
                    Toast.makeText(this, getString(R.string.text_back_home), Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun gameFinished() {
        if (playerScore == 3) {
            binding.tvScore.text = getString(R.string.text_you_win)
            binding.tvVs.textSize = 16F
            binding.tvVs.text = getString(R.string.text_game_over)
            showWinnerDialog(GetWinner.PLAYER)

            Log.d(TAG, "gameFinished: ===PLAYER WINS===")
        } else if (enemyScore == 3) {
            binding.tvScore.text = getString(R.string.text_com_wins)
            binding.tvVs.textSize = 16F
            binding.tvVs.text = getString(R.string.text_game_over)
            showWinnerDialog(GetWinner.ENEMY)

            Log.d(TAG, "gameFinished: ===OPPONENT WINS===")
        }
    }
}