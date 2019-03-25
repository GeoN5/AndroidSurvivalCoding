package com.example.androidsurvivalcoding.mygallery

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.androidsurvivalcoding.R
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton


private val REQUEST_READ_EXTERNA_STORAGE = 1000

class GalleryMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery_main)

        //권한이 부여되었는지 확인
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            //권한이 허용되지 않음
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){
                //이전에 이미 권한이 거부되어있을 때 설명
                alert("사진 정보를 얻으려면 외부 저장소 권한이 필수로 필요합니다","권한이 필요한 이유"){
                    yesButton {
                        //권한 요청
                        ActivityCompat.requestPermissions(this@GalleryMainActivity,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            REQUEST_READ_EXTERNA_STORAGE)
                    }
                    noButton {  }
                }.show()
            }else{
                //권한 요청
                ActivityCompat.requestPermissions(this@GalleryMainActivity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_READ_EXTERNA_STORAGE)
            }
        }else{
            //권한이 이미 허용됨
            getAllPhotos()
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_READ_EXTERNA_STORAGE -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //권한 허용됨
                    getAllPhotos()
                }else{
                    //권한 거부
                    toast("권한 거부 됨")
                }
                return
            }
        }
    }

    private fun getAllPhotos(){
        //모든 사진 정보 가져오기
        val cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, //외부 저장소에 저장된 데이터
            null, //가져올 항목 String 배열
            null, //조건
            null, //조건
            MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC") //찍은 날짜 내림차순

        cursor?.let {
            while (cursor.moveToNext()){
                //사진 경로 Uri 가져오기
                val uri = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                Log.d("photo",uri)
            }
            cursor.close()
        }
    }

}
