package com.example.androidsurvivalcoding.bmicalculator

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import com.example.androidsurvivalcoding.R
import kotlinx.android.synthetic.main.activity_bmi_main.*
import org.jetbrains.anko.startActivity

class BmiMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi_main)

        loadData()

        resultButton.setOnClickListener {
            heightEditTextLayout.error = null
            weightEditTextLayout.error = null

            if(heightEditText.text!!.isNotEmpty() && weightEditText.text!!.isNotEmpty()) {
                saveData(heightEditText.text.toString().toInt(),weightEditText.text.toString().toInt())

                startActivity<BmiResultActivity>(
                    "height" to heightEditText.text.toString().toInt(),
                    "weight" to weightEditText.text.toString().toInt()
                )
            }else{
                if(heightEditText.text!!.isEmpty()){
                    heightEditTextLayout.error = "값을 입력해주세요."
                }
                if(weightEditText.text!!.isEmpty()){
                    weightEditTextLayout.error = "값을 입력해주세요."
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun saveData(height:Int, weight:Int){
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()

        editor.putInt("KEY_HEIGHT",height).putInt("KEY_WEIGHT",weight).apply()
    }

    private fun loadData(){
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val height = pref.getInt("KEY_HEIGHT",0)
        val weight = pref.getInt("KEY_WEIGHT",0)

        if(height != 0 && weight != 0){
            heightEditText.setText(height.toString())
            weightEditText.setText(weight.toString())
        }
    }
}
