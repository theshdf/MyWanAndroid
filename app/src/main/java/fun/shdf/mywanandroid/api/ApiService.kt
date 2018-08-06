package `fun`.shdf.mywanandroid.api

import `fun`.shdf.mywanandroid.base.BaseResponse
import `fun`.shdf.mywanandroid.pojo.Article
import `fun`.shdf.mywanandroid.pojo.BannerBean
import `fun`.shdf.mywanandroid.pojo.ReadBean
import `fun`.shdf.mywanandroid.pojo.RegistBean
import io.reactivex.Observable
import retrofit2.http.*

interface ApiService{
    //todo 获取gank 闲读的子分类
    @GET("wow")
    fun getGank(): Observable<ReadBean>
    //todo  首页数据接口
    @GET("article/list/{num}/json")
    fun getArticle(@Path("num") num: Int): Observable<BaseResponse<Article>>

    @GET("banner/json")
    fun getBanner(): Observable<BaseResponse<List<BannerBean>>>

    @POST("user/register")
    @FormUrlEncoded
    fun postRegist(@Field("username")username: String,
                   @Field("password")password: String,
                   @Field("repassword")repassword: String): Observable<BaseResponse<RegistBean>>

    @POST("user/login")
    @FormUrlEncoded
    fun postLogin(@Field("username")username: String,
                  @Field("password")password: String) : Observable<BaseResponse<RegistBean>>
}
