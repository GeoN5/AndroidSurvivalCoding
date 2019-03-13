package com.example.androidsurvivalcoding.webbrowser

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.androidsurvivalcoding.R
import kotlinx.android.synthetic.main.activity_web_main.*
import org.jetbrains.anko.browse
import org.jetbrains.anko.email
import org.jetbrains.anko.sendSMS
import org.jetbrains.anko.share

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

        //컨텍스트 메뉴 등록 ->webView longClick -> context menu call
        registerForContextMenu(webView)
    }

    override fun onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack()
        }else{
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main,menu)
        return true
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.action_google,R.id.action_home -> {
                webView.loadUrl("http://www.google.com")
                return true
            }
            R.id.action_naver -> {
                webView.loadUrl("http://www.naver.com")
                return true
            }
            R.id.action_daum -> {
                webView.loadUrl("http://www.daum.net")
                return true
            }
            R.id.action_call -> { //암시적 인텐트
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:010-8295-6649")
                //해당 인텐트롤 수행하는 액티비티가 있는지를 검사하여 반환 Runtime Exception 방지
                if(intent.resolveActivity(packageManager) != null){
                    startActivity(intent)
                }
                return true
            }
            R.id.action_send_text -> { //anko implicit intent
                sendSMS("010-8295-6649","Url${webView.url}")
                return true
            }
            R.id.action_email -> { //anko implicit intent
                email("sgepyh2916@naver.com","나만의 웹 브라우저",webView.url)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.action_share -> { //anko implicit intent
                share(webView.url)
                return true
            }
            R.id.action_browser -> { //anko implicit intent
                browse(webView.url)
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

}
