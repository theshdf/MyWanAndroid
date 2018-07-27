package `fun`.shdf.mywanandroid.base

import `fun`.shdf.mywanandroid.R
import `fun`.shdf.mywanandroid.pojo.Article
import `fun`.shdf.mywanandroid.pojo.Data
import `fun`.shdf.mywanandroid.pojo.ResultBean
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.home_item.view.*
import org.w3c.dom.Text
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
Created by shdf on 2018/7/19.
wechat：zcm656025633
exp：
 **/
class MyAdapter(var datas: List<Data>) : RecyclerView.Adapter<MyViewHolder>(){
    private lateinit var listener: ItemOnItemClickListener

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val view:View = LayoutInflater.from(p0.context).inflate(R.layout.home_item,p0,false)
        var viewHolder: MyViewHolder? = null
        if(viewHolder == null){
            viewHolder = MyViewHolder(view)
        }
        return viewHolder
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        p0.title.text= datas.get(p1).title
        p0.author.text = datas.get(p1).author
        p0.date.text = SimpleDateFormat().format(Date(datas.get(p1).publishTime))
        p0.view.setOnClickListener{
            listener.onItemClickListener(it,p1)
        }
    }
     fun setOnClickListener(listener: ItemOnItemClickListener){
        this.listener = listener
    }

}