package `fun`.shdf.mywanandroid.viewmodel

import `fun`.shdf.mywanandroid.api.HttpUtil
import `fun`.shdf.mywanandroid.base.BaseResponse
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
import java.net.UnknownHostException

class HomeViewModel : ViewModel(){
    lateinit var readBean: MutableLiveData<BaseResponse<Article>>
    fun getReadData(page: Int): LiveData<BaseResponse<Article>>{
        readBean = MutableLiveData()
        //todo  从服务起获取数据
        HttpUtil.getInstance().
                getApiService()
                .getArticle(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    readBean.value = it

                }){
                    readBean.value = BaseResponse<Article>(1,null,it.message)
                    if(it is UnknownHostException){
                        Log.d("Tag",it.cause.toString())
                    }
                    Log.d("Tag",it.message)
                    Log.d("Tag",it.cause.toString())
                }
        return  readBean
    }
}