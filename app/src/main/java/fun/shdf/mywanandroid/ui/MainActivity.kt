package `fun`.shdf.mywanandroid.ui

import `fun`.shdf.mywanandroid.R
import `fun`.shdf.mywanandroid.viewmodel.KtViewModel
import `fun`.shdf.mywanandroid.viewmodel.ReadViewModel
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var viewmodel: KtViewModel
    lateinit var readViewModel: ReadViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewmodel = ViewModelProviders.of(this).get(KtViewModel::class.java)
        viewmodel.initUser("zcm",10)
        viewmodel.getUser().observe(this, Observer {
            tv.setText(it?.name+it?.age)
        })
        getReadDta()

    }
    fun getReadDta(){
        readViewModel = ViewModelProviders.of(this).get(ReadViewModel::class.java)
        readViewModel.getReadData().observe(this, Observer {
            tv.setText(""+it?.datas)

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }
}
