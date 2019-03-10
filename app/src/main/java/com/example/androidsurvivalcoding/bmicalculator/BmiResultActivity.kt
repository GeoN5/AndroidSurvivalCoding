package com.example.androidsurvivalcoding.bmicalculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidsurvivalcoding.R
import kotlinx.android.synthetic.main.activity_bmi_result.*
import org.jetbrains.anko.longToast

class BmiResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi_result)

        val height = intent.getIntExtra("height",0)
        val weight = intent.getIntExtra("weight",0)
        //몸무게 / ( 키 / 100.0의 2.0 제곱값)
        val bmi = weight / Math.pow(height / 100.0, 2.0)

        longToast("$bmi")

        when{
            bmi >= 35 -> {
                resultTextView.text = "고도 비만"
                imageView.setImageResource(R.drawable.ic_sentiment_very_dissatisfied_black_24dp)
            }
            bmi >= 30 -> {
                resultTextView.text = "2단계 비만"
                imageView.setImageResource(R.drawable.ic_sentiment_very_dissatisfied_black_24dp)
            }
            bmi >= 25 -> {
                resultTextView.text = "1단계 비만"
                imageView.setImageResource(R.drawable.ic_sentiment_very_dissatisfied_black_24dp)
            }
            bmi >= 23 -> {
                resultTextView.text = "과체중"
                imageView.setImageResource(R.drawable.ic_sentiment_very_dissatisfied_black_24dp)
            }
            bmi >= 18.5 -> {
                resultTextView.text = "정상"
                imageView.setImageResource(R.drawable.ic_sentiment_satisfied_black_24dp)
            }
            else -> {
                resultTextView.text = "저체중"
                imageView.setImageResource(R.drawable.ic_sentiment_dissatisfied_black_24dp)
            }
        }


    }
}
