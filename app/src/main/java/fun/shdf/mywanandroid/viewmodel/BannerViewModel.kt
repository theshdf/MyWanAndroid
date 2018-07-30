package `fun`.shdf.mywanandroid.viewmodel

import `fun`.shdf.mywanandroid.api.ApiService
import `fun`.shdf.mywanandroid.api.HttpUtil
import `fun`.shdf.mywanandroid.pojo.BannerBean
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
code-time: 2018/7/30
code-author: by shdf
coder-wechat: zcm656025633
 **/
class BannerViewModel : ViewModel(){
    private lateinit var bannerList: MutableLiveData<List<BannerBean>>

    fun getBanner(): LiveData<List<BannerBean>>{
        bannerList = MutableLiveData()
        HttpUtil.getInstance().getApiService().getBanner().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe({
                    bannerList.value = it.datas
                }){

                }

        return bannerList
    }
}