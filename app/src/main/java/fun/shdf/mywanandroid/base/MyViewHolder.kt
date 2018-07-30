package `fun`.shdf.mywanandroid.base
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.home_item.view.*

/**
Created by shdf on 2018/7/19.
wechat：zcm656025633
exp：
 **/
class MyViewHolder(var view: View)
    : RecyclerView.ViewHolder(view){
    var title: TextView
    var author: TextView
    var date: TextView
    init {
        title = view.tv_title
        author = view.tv_author
        date = view.tv_date
    }
}

