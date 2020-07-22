package com.example.blockselection

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        // Long Clickable
        webview_long_clickable.run {
            settings.let {
                it.javaScriptEnabled = true
                it.domStorageEnabled = true
            }
            loadUrl(URL)
        }

        // Not Long Clickable
        webview_not_long_clickable.run {
            settings.let {
                it.javaScriptEnabled = true
                it.domStorageEnabled = true
            }

            // Setup long click properties for WebView
            setOnLongClickListener { true }
            isLongClickable = false

            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    // Inject CSS
                    view?.evaluateJavascript(CSS_BLOCK_HIGHLIGHT, null)
                    super.onPageFinished(view, url)
                }
            }

            loadUrl(URL)
        }
    }

    companion object {
        private const val URL = "https://velog.io/@haejung"
        private const val CSS_BLOCK_HIGHLIGHT = """
            javascript:(function() {
                var parent = document.getElementsByTagName('head').item(0);
                var style = document.createElement('style');
                style.type = 'text/css';
                
                style.innerHTML = 'body { -webkit-tap-highlight-color: transparent; };';
                
                parent.appendChild(style)
            })()
        """
    }

}