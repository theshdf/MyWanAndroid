package `fun`.shdf.mywanandroid.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
/**
code-time: 2018/7/27
code-author: by shdf
coder-wechat: zcm656025633
 **/
open abstract class BaseActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initData()
        initView()
        initListener()
    }
    abstract fun initData()
    abstract fun initView()
    abstract fun initListener()
    abstract fun getLayoutId(): Int

}