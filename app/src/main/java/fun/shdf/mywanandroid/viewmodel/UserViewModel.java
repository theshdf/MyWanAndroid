package fun.shdf.mywanandroid.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import fun.shdf.mywanandroid.pojo.User;

/**
 * 1. 给ui层赋值
 * 2. 处理业务逻辑
 */
public class UserViewModel extends ViewModel{
    private LiveData<User> user;
    private User me;
    public void initUser(String name){
        me = new User();
        me.setName(name);
    }
    public LiveData<User> getUser(){
        MutableLiveData<User> u = new MutableLiveData<>();
        u.setValue(me);
        return u;
    }
}
