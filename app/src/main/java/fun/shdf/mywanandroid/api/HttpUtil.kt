package `fun`.shdf.mywanandroid.api

import `fun`.shdf.mywanandroid.App
import `fun`.shdf.mywanandroid.util.NetUtil
import android.content.Context
import android.util.Log
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

class HttpUtil private constructor() {
    private  var okHttpClient: OkHttpClient? = null
    internal var cache: Cache? = null
    private val TIMEOUT: Long = 30

    companion object {
        fun getInstance(): HttpUtil {
            return Instance.web
        }
    }

    /**
     * 声明静态内部类，创石化对象
     */
    private object Instance {
        val web = HttpUtil()
    }

    //todo 初始化okhttp
    init {
        if (okHttpClient == null) {
            okHttpClient = OkHttpClient.Builder().addInterceptor(Interceptor {
                //缓存文件夹
                val cacheFile = File(App.getApplicationContext().getExternalCacheDir().toString(), "cache")
                //缓存大小为10M
                val cacheSize = 10 * 1024 * 1024
                //创建缓存对象
                cache = Cache(cacheFile, cacheSize.toLong())
                val cacheBuilder = CacheControl.Builder()
                cacheBuilder.maxAge(0, TimeUnit.SECONDS)
                cacheBuilder.maxStale(365, TimeUnit.DAYS)
                val cacheControl = cacheBuilder.build()
                //todo 请求设置
                var request = it.request()
                if (!NetUtil.isNetworkAvailable()) {
                    request = request.newBuilder()
                            .cacheControl(cacheControl)
                            .addHeader("token", "123")
                            .build()
                }
                //todo 接收到数据之后的设置
                val originalResponse = it.proceed(request)
                val responseBody = originalResponse.body()
                //为了不消耗buffer，我们这里使用source先获得buffer对象，然后clone()后使用
                val source = responseBody!!.source()
                source.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
                //获得返回的数据
                val buffer = source.buffer()
                //使用前clone()下，避免直接消耗
                Log.d("http-json:", buffer.clone().readString(Charset.forName("UTF-8")))
                if (NetUtil.isNetworkAvailable()) {
                    val maxAge = 0 // read from cache
                    originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public ,max-age=$maxAge")
                            .build()
                } else {
                    val maxStale = 60 * 60 * 24 * 28 // tolerate 4-weeks stale
                    originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                            .build()
                }
            }).addInterceptor(HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                Log.d("http-interceptor:", it)
            }).setLevel(HttpLoggingInterceptor.Level.BASIC))
                    .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .cache(cache).build()
        }
    }

    //todo 获取apiservice
    fun getApiService(): ApiService {
        return Retrofit.Builder()
                .baseUrl(ApiConstant.url)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
    }
}