package me.soushin.higou.base.app.base

import com.jess.arms.mvp.BasePresenter
import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * @author created by Soushin
 * @time 2019/12/25 14 38
 */
open class BasePresenter<M :IModel,V:IView> :BasePresenter<M,V>, CoroutineScope {

    constructor(model:M,rootView:V):super(model, rootView)

    constructor(rootView: V):super(rootView)

    constructor()

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main+job

    override fun onStart() {
        super.onStart()
        job = Job()
    }

    override fun onDestroy() {
        super.onDestroy()
        // 关闭页面后，结束所有协程任务
        job.cancel()
    }



}