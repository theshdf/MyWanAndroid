package `fun`.shdf.mywanandroid

import `fun`.shdf.mywanandroid.api.Status
import `fun`.shdf.mywanandroid.base.BaseResponse

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

    constructor(throwable: Throwable) : this() {
        this.throwable = throwable
    }

    constructor(statusCode: Status, msg: String, data: BaseResponse<T>) : this() {
        this.data = data.datas
        this.msg = msg
        this.statusCode = statusCode
    }

    //todo 正常获取数据的情况
    fun response(data: BaseResponse<T>): Resource<T> {
        if (data != null) {
            if (data.isOkStaus) {
                //todo 数据正确
                this.statusCode = Status.SUCCESS
                return Resource(Status.SUCCESS, "", data)
            } else {
                //todo 数据异常
                this.statusCode = Status.FAIL
                return Resource(Status.SUCCESS, "", data)
            }
        } else {
            this.statusCode = Status.FAIL
            return Resource(Status.SUCCESS, "", data)
        }
    }

    fun error(throwable: Throwable): Resource<T> {
        this.statusCode = Status.ERROR
        return Resource(throwable)
    }

    //todo 初始化加载进度条
    fun initload(): Resource<T> {
        this.statusCode = Status.LOADING
        return Resource()
    }

    fun handleResource(listener: OnBackHandle) {
        when (statusCode) {
            Status.LOADING -> listener.onHandleLoading()
            Status.SUCCESS -> listener.onHanleSuccess()
            Status.FAIL -> listener.onHandleFail()
            Status.ERROR -> listener.onHandleError()
        }
        if (statusCode != Status.COMP) {
            listener.onHandleComplete()
        }
    }

    open interface OnBackHandle {
        fun onHandleLoading()
        fun onHanleSuccess()
        fun onHandleFail()
        fun onHandleError()
        fun onHandleComplete()
    }
}