package `fun`.shdf.mywanandroid

import android.app.Application
import android.content.Context
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger


/**
code-time: 2018/7/24
code-author: by shdf
coder-wechat: zcm656025633
 **/
class App : Application(){

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        //todo 初始化
        Logger.addLogAdapter(AndroidLogAdapter())
    }
    companion object {
        lateinit var context: Context
        fun getApplicationContext(): Context {
            return context
        }
    }
}