/**
 * Copyright 2014 Zhenguo Jin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package `fun`.shdf.mywanandroid.util

import `fun`.shdf.mywanandroid.App
import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkInfo.State
import android.telephony.TelephonyManager
import java.util.HashMap
import java.util.regex.Pattern

/**
 * 网络工具类
 *
 * @author
 */
class NetUtil {

    /**
     * 枚举网络状态 NET_NO：没有网络 NET_2G:2g网络 NET_3G：3g网络 NET_4G：4g网络 NET_WIFI：wifi
     * NET_UNKNOWN：未知网络
     */
    enum class NetState {
        NET_NO, NET_2G, NET_3G, NET_4G, NET_WIFI, NET_UNKNOWN
    }

    /**
     * 判断当前是否网络连接
     *
     * @param context 上下文
     * @return 状态码
     */
    fun isConnected(context: Context): NetState {
        var stateCode = NetState.NET_NO
        val cm = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val ni = cm.activeNetworkInfo
        if (ni != null && ni.isConnectedOrConnecting) {
            when (ni.type) {
                ConnectivityManager.TYPE_WIFI -> stateCode = NetState.NET_WIFI
                ConnectivityManager.TYPE_MOBILE -> when (ni.subtype) {
                    TelephonyManager.NETWORK_TYPE_GPRS // 联通2g
                        , TelephonyManager.NETWORK_TYPE_CDMA // 电信2g
                        , TelephonyManager.NETWORK_TYPE_EDGE // 移动2g
                        , TelephonyManager.NETWORK_TYPE_1xRTT, TelephonyManager.NETWORK_TYPE_IDEN -> stateCode = NetState.NET_2G
                    TelephonyManager.NETWORK_TYPE_EVDO_A // 电信3g
                        , TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_EVDO_0, TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSUPA, TelephonyManager.NETWORK_TYPE_HSPA, TelephonyManager.NETWORK_TYPE_EVDO_B, TelephonyManager.NETWORK_TYPE_EHRPD, TelephonyManager.NETWORK_TYPE_HSPAP -> stateCode = NetState.NET_3G
                    TelephonyManager.NETWORK_TYPE_LTE -> stateCode = NetState.NET_4G
                    else -> stateCode = NetState.NET_UNKNOWN
                }
                else -> stateCode = NetState.NET_UNKNOWN
            }

        }
        return stateCode
    }

    companion object {

        /**
         * 判断网络连接是否打开,包括移动数据连接
         *
         * @return 是否联网
         */
        fun isNetworkAvailable(): Boolean {
            var netstate = false
            val connectivity = App().applicationContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivity != null) {

                val info = connectivity.allNetworkInfo
                if (info != null) {
                    for (i in info.indices) {

                        if (info[i].state == State.CONNECTED) {

                            netstate = true
                            break
                        }
                    }
                }
            }
            return netstate
        }

        /**
         * GPS是否打开
         *
         * @param context 上下文
         * @return Gps是否可用
         */
        fun isGpsEnabled(context: Context): Boolean {
            val lm = context
                    .getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        }

        /**
         * 检测当前打开的网络类型是否WIFI
         *
         * @param context 上下文
         * @return 是否是Wifi上网
         */
        fun isWifi(context: Context): Boolean {
            val connectivityManager = context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetInfo = connectivityManager.activeNetworkInfo
            return if (activeNetInfo != null && activeNetInfo.type == ConnectivityManager.TYPE_WIFI) {
                true
            } else false
        }

        /**
         * 检测当前打开的网络类型是否3G
         *
         * @param context 上下文
         * @return 是否是3G上网
         */
        fun is3G(context: Context): Boolean {
            val connectivityManager = context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetInfo = connectivityManager.activeNetworkInfo
            return if (activeNetInfo != null && activeNetInfo.type == ConnectivityManager.TYPE_MOBILE) {
                true
            } else false
        }

        /**
         * 检测当前开打的网络类型是否4G
         *
         * @param context 上下文
         * @return 是否是4G上网
         */
        fun is4G(context: Context): Boolean {
            val connectivityManager = context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetInfo = connectivityManager.activeNetworkInfo
            if (activeNetInfo != null && activeNetInfo.isConnectedOrConnecting) {
                if (activeNetInfo.type == TelephonyManager.NETWORK_TYPE_LTE) {
                    return true
                }
            }
            return false
        }

        /**
         * 只是判断WIFI
         *
         * @param context 上下文
         * @return 是否打开Wifi
         */
        fun isWiFi(context: Context): Boolean {
            val manager = context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                    .state
            return if (wifi == State.CONNECTED || wifi == State.CONNECTING) true else false

        }

        /**
         * IP地址校验
         *
         * @param ip 待校验是否是IP地址的字符串
         * @return 是否是IP地址
         */
        fun isIP(ip: String): Boolean {
            val pattern = Pattern
                    .compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b")
            val matcher = pattern.matcher(ip)
            return matcher.matches()
        }

        /**
         * IP转化成int数字
         *
         * @param addr IP地址
         * @return Integer
         */
        fun ipToInt(addr: String): Int {
            val addrArray = addr.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            var num = 0
            for (i in addrArray.indices) {
                val power = 3 - i
                num += (Integer.parseInt(addrArray[i]) % 256 * Math
                        .pow(256.0, power.toDouble())).toInt()
            }
            return num
        }

        /**
         * 获取URL中参数 并返回Map
         * @param url
         * @return
         */
        fun getUrlParams(url: String?): Map<String, String>? {
            var map: MutableMap<String, String>? = null

            if (url != null && url.indexOf("&") > -1 && url.indexOf("=") > -1) {
                map = HashMap()

                val arrTemp = url.split("&".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                for (str in arrTemp) {
                    val qs = str.split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    map[qs[0]] = qs[1]
                }
            }

            return map
        }
    }

}
