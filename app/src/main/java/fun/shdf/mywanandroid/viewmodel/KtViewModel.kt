package `fun`.shdf.mywanandroid.viewmodel

import `fun`.shdf.mywanandroid.pojo.MyUser
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

open class KtViewModel : ViewModel(){
    var user: MyUser? = null
    lateinit var ldUser: MutableLiveData<MyUser>
    fun initUser(name: String,age: Int): Unit{
        user = MyUser(name,age)
    }
    fun getUser(): LiveData<MyUser>{
        ldUser = MutableLiveData()
        ldUser.value = user
        return ldUser
    }
    //todo 从网络获取数据
    }
