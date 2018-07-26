package `fun`.shdf.mywanandroid.api

import `fun`.shdf.mywanandroid.pojo.ReadBean
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiService{
    //todo 获取gank 闲读的子分类
    @GET("wow")
    fun getGank(): Observable<ReadBean>
}
