package `fun`.shdf.mywanandroid

import `fun`.shdf.mywanandroid.ui.BaseActivity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.webkit.WebView
import kotlinx.android.synthetic.main.webview.*
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.app.AlertDialog
import android.graphics.Bitmap
import android.webkit.WebViewClient
import android.webkit.WebSettings
/**
code-time: 2018/7/27
code-author: by shdf
coder-wechat: zcm656025633
 **/
class AppWebView : BaseActivity(){
    override fun initData() {
    }

    override fun initView() {
        val url: String = intent.getStringExtra("url")
        webview.loadUrl(url)
        webview.webChromeClient = webChromeClient
        webview.webViewClient = webViewClient
        val webSettings = webview.settings
        webSettings.javaScriptEnabled = true//允许使用js

        /**
         * LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
         * LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
         * LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
         * LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
         */
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE//不使用缓存，只从网络获取数据.

        //支持屏幕缩放
        webSettings.setSupportZoom(true)
        webSettings.builtInZoomControls = true
    }

    override fun initListener() {
    }

    override fun getLayoutId(): Int {
        return R.layout.webview
    }

    //WebViewClient主要帮助WebView处理各种通知、请求事件
    private val webViewClient = object : WebViewClient() {
        override fun onPageFinished(view: WebView, url: String) {//页面加载完成

        }

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {//页面开始加载

        }

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            if (url == "http://www.google.com/") {

                return true//表示我已经处理过了
            }
            return super.shouldOverrideUrlLoading(view, url)
        }

    }

    //WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
    private val webChromeClient = object : WebChromeClient() {
        //不支持js的alert弹窗，需要自己监听然后通过dialog弹窗
        override fun onJsAlert(webView: WebView, url: String, message: String, result: JsResult): Boolean {
            val localBuilder = AlertDialog.Builder(webView.context)
            localBuilder.setMessage(message).setPositiveButton("确定", null)
            localBuilder.setCancelable(false)
            localBuilder.create().show()

            //注意:
            //必须要这一句代码:result.confirm()表示:
            //处理结果为确定状态同时唤醒WebCore线程
            //否则不能继续点击按钮
            result.confirm()
            return true
        }

        //获取网页标题
        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)

        }

        //加载进度回调
        override fun onProgressChanged(view: WebView, newProgress: Int) {

        }
    }

}