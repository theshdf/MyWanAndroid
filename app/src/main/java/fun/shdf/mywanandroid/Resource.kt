package `fun`.shdf.mywanandroid

import `fun`.shdf.mywanandroid.api.Status
import `fun`.shdf.mywanandroid.base.BaseResponse
import android.text.TextUtils
import java.net.ConnectException
import java.net.UnknownHostException

/**
code-time: 2018/8/1
code-author: by shdf
coder-wechat: zcm656025633
exp:动态代理封装网络库
 **/
class Resource<T>() {
    var data: T? = null
    private var statusCode: Status = Status.SUCCESS
    private lateinit var msg: String
    private lateinit var throwable: Throwable

    constructor(statusCode: Status,throwable: Throwable) : this() {
        this.throwable = throwable
        this.statusCode = statusCode
    }

    constructor(statusCode: Status, msg: String, data: BaseResponse<T>?) : this() {
        this.data = data?.datas
        this.msg = msg
        this.statusCode = statusCode
    }

    //todo 正常获取数据的情况
    fun response(data: BaseResponse<T>): Resource<T> {
        if (data != null) {
            if (data.errorCode == 0) {
                //todo 数据正确
                return Resource(Status.SUCCESS, "", data)
            } else {
                //todo 数据异常
                return Resource(Status.FAIL, "", data)
            }
        } else {
            return Resource(Status.FAIL, "", data)
        }
    }

    fun error(throwable: Throwable): Resource<T> {
        return Resource(Status.ERROR,throwable)
    }

    //todo 初始化加载进度条
    fun initload(): Resource<T> {
        return Resource(Status.LOADING,"",null)
    }

    fun handleResource(listener: OnBackHandle<T>) {
        when (statusCode) {
            Status.LOADING -> listener.onHandleLoading()
            Status.SUCCESS ->
                if(data == null){

                }
            else{
                    listener.onHanleSuccess(data as T)
                }

            Status.FAIL ->
                if(!TextUtils.isEmpty(msg)){
                   // listener.onHandleFail(msg)
                }
                else{

                    }
            Status.ERROR ->
                if(throwable is UnknownHostException){
                    //todo  没有网络连接
                 //   listener.onHandleError()
                }
        }
        if (statusCode != Status.LOADING) {
            listener.onHandleComplete()
        }
    }

    open interface OnBackHandle<T> {
        fun onHandleLoading()
        fun onHanleSuccess(data: T)
       /* fun onHandleFail(msg:String)
        fun onHandleError()*/
        fun onHandleComplete()
    }
}