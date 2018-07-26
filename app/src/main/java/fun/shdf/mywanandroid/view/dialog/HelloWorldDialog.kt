package `fun`.shdf.mywanandroid.view.dialog

import `fun`.shdf.mywanandroid.R
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class HelloWorldDialog : DialogFragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //todo 简单实现对话框的功能
        var view: View = inflater.inflate(R.layout.dialog,container,true)
        return view
    }
}