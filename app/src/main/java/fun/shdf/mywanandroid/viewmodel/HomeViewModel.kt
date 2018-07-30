package `fun`.shdf.mywanandroid.viewmodel

import `fun`.shdf.mywanandroid.api.HttpUtil
import `fun`.shdf.mywanandroid.pojo.Article
import `fun`.shdf.mywanandroid.pojo.ReadBean
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.graphics.ImageDecoder
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.ConnectException

class HomeViewModel : ViewModel(){
    lateinit var readBean: MutableLiveData<Article>
    fun getReadData(page: Int): LiveData<Article>{
        readBean = MutableLiveData()
        //todo  从服务起获取数据
        HttpUtil.getInstance().
                getApiService()
                .getArticle(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    readBean.value = it.datas

                }){
                    if(it is ConnectException){
                        Log.d("Tag",it.cause.toString())
                    }
                    Log.d("Tag",it.message)
                    Log.d("Tag",it.cause.toString())
                }
        return  readBean
    }
}