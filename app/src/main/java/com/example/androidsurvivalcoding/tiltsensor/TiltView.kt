package com.example.androidsurvivalcoding.tiltsensor

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.hardware.SensorEvent
import android.view.View

class TiltView(context: Context?) : View(context) {
    private val greenPaint: Paint = Paint()
    private val blackPaint: Paint = Paint()

    private var cX: Float = 0f
    private var cY: Float = 0f

    private var xCoord: Float = 0f
    private var yCoord: Float = 0f

    init {
        //녹색 페인트
        greenPaint.color = Color.GREEN
        //검은색 테두리 페인트
        blackPaint.style = Paint.Style.STROKE
    }

    //뷰의 크기가 변경될 때 호출
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        cX = w / 2f
        cY = h / 2f
    }

    //안드로이드의 좌표계: 왼쪽 상단(0,0), 오룬쪽 하단(기기의 가로,세로)
    override fun onDraw(canvas: Canvas?) {
        //바깥 원
        canvas?.drawCircle(cX,cY,100f,blackPaint)
        //녹색  원
        canvas?.drawCircle(yCoord + cX,xCoord + cY,100f,greenPaint)
        //가운데 십자가
        canvas?.drawLine(cX - 20, cY,cX + 20, cY, blackPaint)
        canvas?.drawLine(cX, cY - 20, cX, cY + 20, blackPaint)
    }

    fun onSensorEvent(event: SensorEvent){
        xCoord = event.values[0] * 20
        yCoord = event.values[1] * 20
        //onDraw() 메소드 재호출
        invalidate()
    }
}