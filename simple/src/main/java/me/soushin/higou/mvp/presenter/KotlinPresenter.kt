package me.soushin.higou.mvp.presenter

import android.app.Application

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.soushin.higou.base.app.network.kotlinNet.getJobs
import javax.inject.Inject

import me.soushin.higou.mvp.contract.KotlinContract


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/19/2019 17:55
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

    fun requestUsers(){
        
    }


}
