package `fun`.shdf.mywanandroid.db

import `fun`.shdf.mywanandroid.pojo.ResultBean
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

/**
code-time: 2018/7/24
code-author: by shdf
coder-wechat: zcm656025633
 **/
@Dao
interface BaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(resultBeans: List<ResultBean>)

    @Query("SELECT * FROM xindu")
    fun select(): List<ResultBean>
}