package `fun`.shdf.mywanandroid.ui

import `fun`.shdf.mywanandroid.App
import `fun`.shdf.mywanandroid.R
import `fun`.shdf.mywanandroid.base.MyAdapter
import `fun`.shdf.mywanandroid.pojo.Article
import `fun`.shdf.mywanandroid.pojo.Data
import `fun`.shdf.mywanandroid.pojo.ResultBean
import `fun`.shdf.mywanandroid.view.dialog.HelloWorldDialog
import `fun`.shdf.mywanandroid.viewmodel.ReadViewModel
import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_read.*

class ReadActivity : AppCompatActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var readViewModel: ReadViewModel
    private lateinit var articles: MutableList<Data>
    private var dd: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)
        linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        articles = mutableListOf()
        recycler.layoutManager = linearLayoutManager
        recycler.adapter = MyAdapter(articles)
        (recycler.adapter)?.notifyDataSetChanged()
        readViewModel = ViewModelProviders.of(this)[ReadViewModel::class.java]
        showDialog()
       // showDialogFragment()
    }

    /**
     * 获取数据
     */
    fun getResult() {
        readViewModel.getReadData().observe(this, Observer {
            articles.clear()
            articles.addAll(it!!.datas)
            recycler.adapter.let { it?.notifyDataSetChanged() }
        }
        )
        recycler.setOnClickListener { }
        recycler.performClick()
    }

    override fun onResume() {
        super.onResume()
        getResult()
        if(App.getApplicationContext()!= null&& App.getApplicationContext() is Context){
            Log.d("Tag","sss")
        }
    }

    fun showDialog() {
        dd = Dialog(this)
        dd?.setContentView(R.layout.dialog)
        dd?.show()
    }

    fun showDialogFragment() {
        var hello: HelloWorldDialog = HelloWorldDialog()
        var transition: FragmentTransaction =  supportFragmentManager.beginTransaction()
        transition.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        hello.show(transition,"hello")
    }

}
