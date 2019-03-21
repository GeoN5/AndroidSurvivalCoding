package com.example.androidsurvivalcoding.tiltsensor

import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

//X = 수평(왼쪽 -,오른쪽 +), Y = 수직(아래쪽 -, 위쪽 +), Z = 화면의 바깥쪽(+)
//가로모드기 때문에 좌표축이 돌아감(기기 기준으로 생각하기).

class SensorMainActivity : AppCompatActivity() ,SensorEventListener{

    private lateinit var tiltView: TiltView
    private val sensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //화면이 꺼지지 않게 설정
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        //가로 모드 설정
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        //커스텀뷰 설정
        tiltView = TiltView(this)
        setContentView(tiltView)
    }

    //액티비티가 동작할 때만 센서 활성화.
    override fun onResume() {
        super.onResume()
        //이벤트리스너,사용 센서,주기
        sensorManager.registerListener(this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_NORMAL)
    }

    //센서 해제
    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) { //센서 정밀도 변경 콜백

    }

    override fun onSensorChanged(event: SensorEvent?) { //센서값 변경 콜백
        event?.let {
            Log.d("MainActivity","onSensorChanged: x : ${event.values[0]}, y : ${event.values[1]}, z : ${event.values[2]}")
            tiltView.onSensorEvent(event)
        }
    }

}
