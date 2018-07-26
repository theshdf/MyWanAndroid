package `fun`.shdf.mywanandroid.pojo

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "xindu")
data class ResultBean(
        var _id: String,
        var icon: String,
        var id: String,
        var title: String,
        var createdAt: Date
)