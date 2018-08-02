package `fun`.shdf.mywanandroid.base

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
code-time: 2018/7/25
code-author: by shdf
coder-wechat: zcm656025633
 **/
data class BaseResponse<T>(
        var errorCode: Int,// code = 0 表示请求成功
        @SerializedName("data")
        var datas: T?= null,
        var errorMsg: String?,
        var throwable: Throwable?

) : UnProguard{
        val isOkStaus = errorCode == 0
}




