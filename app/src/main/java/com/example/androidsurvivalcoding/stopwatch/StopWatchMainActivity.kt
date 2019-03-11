package com.example.androidsurvivalcoding.stopwatch

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.androidsurvivalcoding.R
import kotlinx.android.synthetic.main.activity_stop_watch_main.*
import java.util.*
import kotlin.concurrent.timer

class StopWatchMainActivity : AppCompatActivity() {
    private var time = 0
    private var isRunning = false
    private lateinit var timerTask:Timer
    private var lab = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stop_watch_main)

        fab.setOnClickListener {
            isRunning = !isRunning

            if(isRunning){
                start()
            }else{
                pause()
            }
        }

        labButton.setOnClickListener {
            recordLabTime()
        }

        resetFab.setOnClickListener {
            reset()
        }

    }

    private fun start(){
        fab.setImageResource(R.drawable.ic_pause_black_24dp)

        timerTask = timer(period = 10){
            time++
            val sec = time / 100
            val milli = time % 100
            runOnUiThread{
                secTextView.text = "$sec"
                milliTextView.text = "$milli"
            }
        }
    }

    private fun pause(){
        fab.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        timerTask.cancel()
    }

    private fun recordLabTime(){
        val labTime = this.time
        val textView = TextView(this)
        textView.text = "$lab LAB : ${labTime / 100}.${labTime % 100}"

        labLayout.addView(textView,0)
        lab++
    }

    private fun reset(){
        timerTask.cancel()

        time = 0
        isRunning = false
        fab.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        secTextView.text = "0"
        milliTextView.text = "00"

        labLayout.removeAllViews()
        lab = 1
    }

}
