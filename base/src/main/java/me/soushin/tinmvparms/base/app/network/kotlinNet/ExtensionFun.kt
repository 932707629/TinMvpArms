package me.soushin.tinmvparms.base.app.network.kotlinNet

import com.jess.arms.di.component.AppComponent
import com.jess.arms.integration.AppManager
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.ArmsUtils
import kotlinx.coroutines.*
import java.io.IOException
import java.lang.Exception
import java.net.ConnectException

/**
 * 扩展方法
 * @auther SouShin
 * @time 2019/12/17 17:21
 */
fun <T> CoroutineScope.retrofit(dsl: RetrofitCoroutineDSL<T>.() -> Unit) {
    //在主线程中开启协程
    this.launch(Dispatchers.Main) {
        val coroutine = RetrofitCoroutineDSL<T>().apply(dsl)
        coroutine.api?.let { call ->
            //async 并发执行 在IO线程中
            val deferred = async(Dispatchers.IO) {
                try {
                    call.execute() //已经在io线程中了，所以调用Retrofit的同步方法
                }catch (e:Exception){
                    getAppComponent().rxErrorHandler().handlerFactory.handleError(e)
                    if (e is ConnectException){
                        coroutine.onFail?.invoke("网络连接出错", -1)
                    }else if (e is IOException){
                        coroutine.onFail?.invoke("未知网络错误", -1)
                    }
                    null
                }
            }
            //当协程取消的时候，取消网络请求
            deferred.invokeOnCompletion {
                if (deferred.isCancelled) {
                    call.cancel()
                    coroutine.clean()
                }
            }
            //await 等待异步执行的结果
            val response = deferred.await()
            if (response == null) {
                coroutine.onFail?.invoke("返回为空", -1)
            } else {
                response.let {
                    if (response.isSuccessful) {
                        //访问接口成功
                        if (response.body()?.status == 1) {
                            //判断status 为1 表示获取数据成功
                            coroutine.onSuccess?.invoke(response.body()!!.data)
                        } else {
                            coroutine.onFail?.invoke(response.body()?.msg ?: "返回数据为空", response.code())
                        }
                    } else {
                        coroutine.onFail?.invoke(response.errorBody().toString(), response.code())
                    }
                }
            }
            coroutine.onComplete?.invoke()
        }
    }
}

fun CoroutineScope.getAppComponent(): AppComponent {
    return ArmsUtils.obtainAppComponentFromContext(AppManager.getAppManager().topActivity)
}

fun BasePresenter<*,*>.getJobs():Job{
    return Job()
}

