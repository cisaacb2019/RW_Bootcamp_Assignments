package com.example.bullseye

import android.content.Intent
//import android.content.res.Configuration
//import android.content.pm.ActivityInfo
//import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.os.PersistableBundle
//import android.util.Log
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.bullseye.databinding.ActivityMainBinding
//import java.lang.Math.abs
//import java.lang.Math.random
//import kotlin.math.log
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var sliderValue = 0
    private var targetValue = newTargetValue()
    private var totalScore = 0
    private var totalRound = 1

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        startNewGame()


        binding.hitMeButton.setOnClickListener {
            showResult()
            totalScore += pointsForCurrentRound()
            binding.gameScoreTextView?.text = totalScore.toString()

        }
        binding.startOverButton?.setOnClickListener {

        startNewGame()
        }
        binding.infoButton?.setOnClickListener{
            navigateToAboutPage()//when info button is clicked then run navigate toa bout page function
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                sliderValue = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
        savedInstanceState?.getInt("Score")
        savedInstanceState?.getInt("Round")
    }

    private fun navigateToAboutPage() {
        val intent = Intent(this, AboutActivity::class.java)//this refers to main layout passes activity package
        //whats the intention
        startActivity(intent)//passes intent over to the start activity method to display
    }


    private fun differenceAmount() = kotlin.math.abs(targetValue - sliderValue)
    private fun newTargetValue() = Random.nextInt(1,100)

    private fun pointsForCurrentRound(): Int {
//        //here are some if then statements
//        if something is true {
//            then do this
//        }
//        else if something else is true {
//            then do this
//        }
//        else{
//            do something when none of the above are true
//        }
        val maxScore = 100
        val difference = differenceAmount()//gets absolute value from 0 so cant be negative


        return (maxScore - difference) + bonusPoints()
    }
    private fun startNewGame() {
            totalScore = 0
            totalRound = 1
            sliderValue = 50
            targetValue = newTargetValue()

        binding.gameScoreTextView?.text = totalScore.toString()
        binding.gameRoundTextView?.text = totalRound.toString()
        binding.targetTextView.text = targetValue.toString()
        binding.seekBar.progress = sliderValue
    }

    private fun showResult() {
        val dialogTitle = alertTitle()
        val dialogMessage =
            getString(R.string.result_dialog_message, sliderValue, pointsForCurrentRound())
        // val dialogMessage = "The slider's value is $sliderValue"


        val builder = AlertDialog.Builder(this)
        builder.setTitle(dialogTitle)
        builder.setMessage(dialogMessage)
        builder.setPositiveButton(R.string.result_button_text) { dialog, _ ->
            dialog.dismiss()
            targetValue = newTargetValue()
            binding.targetTextView.text = targetValue.toString()


            totalRound += 1
            binding.gameRoundTextView?.text = totalRound.toString()

        }

        builder.create().show()
    }
    private fun alertTitle(): String {
        val difference = differenceAmount()
        val title: String = when {
            difference == 0 -> {
                getString(R.string.alert_title_1)
            }
            difference < 5 -> {
                getString(R.string.alert_title_2)
            }
            difference <= 10 -> {
                getString(R.string.alert_title_3)
            }
            else -> {
                getString(R.string.alert_title_4)
            }
        }
        return title
    }
    private fun bonusPoints(): Int {
        val  bonus: Int = when (differenceAmount()) {
            0 -> 100
            1 -> 50
            else -> 0
        }


            return bonus
        }
// data store attempt, please ignore I don't know much about it but will learn more about it shortly...
//    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
//        super.onSaveInstanceState(outState, outPersistentState)
//        outState.putInt("Score",totalScore)
//        outState.putInt("Round",totalRound)
//    }


    }
