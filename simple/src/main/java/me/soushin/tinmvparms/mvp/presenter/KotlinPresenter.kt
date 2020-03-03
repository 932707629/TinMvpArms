package me.soushin.tinmvparms.mvp.presenter

import android.app.Application
import com.blankj.ALog

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.http.imageloader.ImageLoader
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import me.soushin.tinmvparms.base.app.base.BasePresenter
import me.soushin.tinmvparms.base.app.network.kotlinNet.retrofit
import me.soushin.tinmvparms.base.app.utils.GsonUtils
import javax.inject.Inject

import me.soushin.tinmvparms.mvp.contract.KotlinContract
import me.soushin.tinmvparms.mvp.model.entity.User
import java.io.File
import java.io.FileOutputStream


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/26/2019 10:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class KotlinPresenter
@Inject
constructor(model: KotlinContract.Model, rootView: KotlinContract.View) :
        BasePresenter<KotlinContract.Model, KotlinContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager


    override fun onDestroy() {
        super.onDestroy();
    }


    fun requestUser(){
        retrofit<List<User>> {
            api= mModel.getUsers(1,true)

            onSuccess {
                ALog.e("请求到的数据打印",GsonUtils.toString(it))
            }
           onFail { msg, errorCode ->
               ALog.e("请求异常",msg,errorCode)
           }
        }
    }


    fun download(){
        mModel.download("https://gbqd-cloudshop-prod.oss-cn-qingdao.aliyuncs.com/upload/payCode/支付宝授权函.docx")
                .subscribeOn(Schedulers.io())
                .map {
                    val ios=it.byteStream()
                    var fos:FileOutputStream ?=null
                    var path=mApplication.cacheDir.absolutePath+"/支付宝授权函.docx"

                    val file =File(path)
                    file.createNewFile()

                    fos=FileOutputStream(file)

                    val byte= ByteArray(1024)
                    var len=0
                    do {
                        if (len>0){
                            len=ios.read(byte)
                            fos.write(byte,0,len)
                        }
                    }while (len!=-1)
                    fos.close()
                    ios.close()
                    return@map path
                }.subscribe(object: ErrorHandleSubscriber<String>(mErrorHandler) {
                    override fun onNext(t: String) {
                        ALog.e("文件下载路径",t)
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                    }
                })

    }




}
