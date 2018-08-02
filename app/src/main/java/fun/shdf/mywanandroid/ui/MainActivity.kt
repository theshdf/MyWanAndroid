package `fun`.shdf.mywanandroid.ui

import `fun`.shdf.mywanandroid.AppWebView
import `fun`.shdf.mywanandroid.GlideImageLoader
import `fun`.shdf.mywanandroid.R
import `fun`.shdf.mywanandroid.R.layout.home_item
import `fun`.shdf.mywanandroid.Resource
import `fun`.shdf.mywanandroid.pojo.BannerBean
import `fun`.shdf.mywanandroid.pojo.Data
import `fun`.shdf.mywanandroid.viewmodel.BannerViewModel
import `fun`.shdf.mywanandroid.viewmodel.HomeViewModel
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder
import com.zyyoona7.popup.EasyPopup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.home_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : BaseActivity() {

    private lateinit var articles: MutableList<Data>
    private lateinit var adapter: CommonAdapter<Data>
    private lateinit var viewModel: HomeViewModel
    private lateinit var bannerViewModel: BannerViewModel
    private var page: Int = 1
    private lateinit var banners: MutableList<String>
    private lateinit var pop: EasyPopup

    override fun initData() {
        articles = mutableListOf()
        banners = mutableListOf()
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        bannerViewModel = ViewModelProviders.of(this)[BannerViewModel::class.java]
        adapter = object : CommonAdapter<Data>(
                this@MainActivity,
                home_item,
                articles) {
            override fun convert(holder: ViewHolder, t: Data, position: Int) {
                holder.itemView.tv_title.text = t.title
                holder.itemView.tv_author.text = t.author
                holder.itemView.tv_date.text = SimpleDateFormat().format(Date(t.publishTime))
            }
        }
    }

    override fun initView() {
        var llm: LinearLayoutManager = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv.layoutManager = llm
        rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rv.adapter = adapter
        ssr.setDefaultCircleProgressColor(Color.RED)
        pop = EasyPopup.create()
                .setContentView(this, R.layout.head_banner)
                //  .setAnimationStyle(R.style.RightPopAnim)
                //是否允许点击PopupWindow之外的地方消失
                .setFocusAndOutsideEnable(true)
                //允许背景变暗
                .setBackgroundDimEnable(true)
                //变暗的透明度(0-1)，0为完全透明
                .setDimValue(0.4f)
                //变暗的背景颜色
                .setDimColor(Color.GRAY)
                //指定任意 ViewGroup 背景变暗
                //  .setDimView(viewGroup)
                .apply()
    }

    override fun initListener() {
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
            override fun onItemLongClick(view: View?, holder: RecyclerView.ViewHolder?, position: Int): Boolean {
                return false
            }

            override fun onItemClick(view: View?, holder: RecyclerView.ViewHolder?, position: Int) {
                val url: String = articles.get(position).link
                intent.setClass(this@MainActivity, AppWebView::class.java)
                intent.putExtra("url", url)
                startActivity(intent)
            }
        })
        //todo 下拉刷新
        ssr.setOnPullRefreshListener(object : SuperSwipeRefreshLayout.OnPullRefreshListener {
            override fun onPullEnable(p0: Boolean) {
            }

            override fun onPullDistance(p0: Int) {
            }

            override fun onRefresh() {
                getBanner()
                getArticles(1)
            }
        })
        ssr.setOnPushLoadMoreListener(object : SuperSwipeRefreshLayout.OnPushLoadMoreListener {
            override fun onPushDistance(p0: Int) {
            }

            override fun onPushEnable(p0: Boolean) {
            }

            override fun onLoadMore() {
                page++
                getBanner()
                getArticles(page)
            }
        })
        ssr.isRefreshing = true
        getBanner()
        getArticles(1)
    }

    //todo 获取banner图
    fun getBanner(){
        bannerViewModel
                .getBanner()
                .observe(this, android.arch.lifecycle.Observer {
                    //todo 获取数据
                    banners.clear()
                    for (data: BannerBean in it!!) {
                        banners.add(data.imagePath)
                    }
                    banner.setImages(banners).setImageLoader(GlideImageLoader()).start()
                })
    }

    //todo 获取文章列表
    private fun getArticles(page: Int) {
        if (page == 1) {
           // viewModel.getReadData(1).observe(this@MainActivity,{res -> res.})
            viewModel.getReadData(page).observe(this@MainActivity, android.arch.lifecycle.Observer {
                it!!.handleResource(object : Resource.OnBackHandle{
                    override fun onHandleLoading() {
                        
                    }
                    override fun onHanleSuccess() {
                        if(it.data != null){
                            articles.clear()
                            articles.addAll(it.data!!.datas)
                            adapter.notifyDataSetChanged()
                            ssr.isRefreshing = false
                        }
                    }
                    override fun onHandleFail() {
                    }
                    override fun onHandleError() {
                    }
                    override fun onHandleComplete() {
                        ssr.setLoadMore(false)
                    }
                })
            }
            )
        } else {
            viewModel.getReadData(page).observe(this@MainActivity, android.arch.lifecycle.Observer {
                it!!.handleResource(object : Resource.OnBackHandle{
                    override fun onHandleLoading() {
                    }
                    override fun onHanleSuccess() {
                        if (it.data != null){
                            articles.addAll(it.data!!.datas)
                            adapter.notifyDataSetChanged()
                            ssr.setLoadMore(false)
                        }
                    }
                    override fun onHandleFail() {
                    }

                    override fun onHandleError() {
                    }

                    override fun onHandleComplete() {
                        ssr.setLoadMore(false)
                    }
                })
            }
            )
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }
}
