package me.soushin.tinmvparms.base.app.base

import android.content.Context
import android.content.Intent

import com.hjq.toast.ToastUtils
import com.jess.arms.mvp.IPresenter


import com.jess.arms.utils.Preconditions.checkNotNull

/**
 * 自定义的Activity基类
 * @author Soushin
 * @time 2019/12/26 11:23
 */
abstract class BaseActivity<P : IPresenter> : com.jess.arms.base.BaseActivity<P>() {

    val `this`: Context
        get() = this

    fun showMessage(message: String) {
        checkNotNull(message)
        ToastUtils.show(message)
    }

    open fun launchActivity(intent: Intent) {
        checkNotNull(intent)
        startActivity(intent)
    }

    open fun showLoading() {

    }

    open fun hideLoading() {

    }

    fun goActivity(clazz: Class<*>) {
        startActivity(Intent(this, clazz))
    }

    open fun killMyself() {
        finish()
    }

    /**
     * 默认不使用EventBus
     */
    override fun useEventBus(): Boolean {
        return false
    }

}
