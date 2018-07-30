package `fun`.shdf.mywanandroid

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.youth.banner.loader.ImageLoader

/**
code-time: 2018/7/30
code-author: by shdf
coder-wechat: zcm656025633
 **/
class GlideImageLoader : ImageLoader (){
    override fun displayImage(context: Context, path: Any, imageView: ImageView) {
        Glide.with(context).load(path).into(imageView)
    }
}