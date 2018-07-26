package `fun`.shdf.mywanandroid.util

import `fun`.shdf.mywanandroid.pojo.ResultBean
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

class MyAdapter(var datas: List<ResultBean>) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.




    }

    override fun getItemCount(): Int = datas?.size

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }
}