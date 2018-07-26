package `fun`.shdf.mywanandroid.api

import `fun`.shdf.mywanandroid.base.BaseResponse
import `fun`.shdf.mywanandroid.pojo.Article
import `fun`.shdf.mywanandroid.pojo.ReadBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService{
    //todo 获取gank 闲读的子分类
    @GET("wow")
    fun getGank(): Observable<ReadBean>
    //todo  首页数据接口
    @GET("article/list/{num}/json")
    fun getArticle(@Path("num") num: Int): Observable<BaseResponse<Article>>
}
