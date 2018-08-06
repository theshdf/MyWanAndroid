package `fun`.shdf.mywanandroid.pojo

/**
code-time: 2018/8/6
code-author: by shdf
coder-wechat: zcm656025633
exp:
 **/

data class RegistBean(
    val collectIds: List<Int>,
    val email: String,
    val icon: String,
    val id: Int,
    val password: String,
    val type: Int,
    val username: String
)