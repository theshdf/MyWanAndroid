package `fun`.shdf.mywanandroid.ui

import `fun`.shdf.mywanandroid.AppWebView
import `fun`.shdf.mywanandroid.R
import `fun`.shdf.mywanandroid.base.ItemOnItemClickListener
import `fun`.shdf.mywanandroid.base.MyAdapter
import `fun`.shdf.mywanandroid.pojo.Data
import `fun`.shdf.mywanandroid.view.dialog.HelloWorldDialog
import `fun`.shdf.mywanandroid.viewmodel.HomeViewModel
import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.Window
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_read.*

class ReadActivity : AppCompatActivity(){

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var readViewModel: HomeViewModel
    private lateinit var articles: MutableList<Data>
    private var dd: Dialog? = null
    private lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_read)
        linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        articles = mutableListOf()
        recycler.layoutManager = linearLayoutManager
        recycler.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        adapter = MyAdapter(articles)
        recycler.adapter = adapter
        adapter.notifyDataSetChanged()
        adapter.setOnClickListener(object : ItemOnItemClickListener{
            override fun onItemClickListener(view: View,position: Int) {
                Logger.d(articles.get(position).link)
                val url: String = articles.get(position).link
               // val intent = Intent(this@ReadActivity,AppWebView::class.java)
                intent.setClass(this@ReadActivity,AppWebView::class.java)
                intent.putExtra("url",url)
                startActivity(intent)
            }
        })
        readViewModel = ViewModelProviders.of(this)[HomeViewModel::class.java]
        showDialog()
       // showDialogFragment()
    }

    /**
     * 获取数据
     */
    fun getResult() {
        readViewModel.getReadData(1).observe(this, Observer {
            articles.clear()
            articles.addAll(it!!.datas!!.datas)
            recycler.adapter.let { it?.notifyDataSetChanged() }
        }
        )
        recycler.setOnClickListener { }
        recycler.performClick()
    }

    override fun onResume() {
        super.onResume()
        getResult()
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
