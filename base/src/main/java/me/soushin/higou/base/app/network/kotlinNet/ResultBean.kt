package me.soushin.higou.base.app.network.kotlinNet

/**
 *
 * @auther SouShin
 * @time 2019/12/17 17:22
 */
data class ResultBean<T>(var status:Int?,var data:T,var msg:String?)