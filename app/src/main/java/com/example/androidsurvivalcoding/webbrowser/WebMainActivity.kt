package com.example.androidsurvivalcoding.webbrowser

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.androidsurvivalcoding.R
import kotlinx.android.synthetic.main.activity_web_main.*

class WebMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_main)

        webView.apply {
            settings.javaScriptEnabled = true //webView.settings.javaScriptEnabled = true
            //초기화 안할 시 자체 웹 브라우저에서 동작.
            webViewClient = WebViewClient() //webView.webViewClient = WebViewClient()
            loadUrl("http://www.google.com")
        }

        //글자가 입력될 때마다 호출(반응한 뷰,액션ID,이벤트)
        urlEditText.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){ //아이콘 클릭시 로드
                webView.loadUrl("http://${urlEditText.text.toString()}")
                true
            }else{
                false
            }
        }

    }

    override fun onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack()
        }else{
            super.onBackPressed()
        }
    }
}
