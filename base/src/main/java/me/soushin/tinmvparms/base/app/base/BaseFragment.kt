package me.soushin.tinmvparms.base.app.base

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle

import com.gyf.immersionbar.components.ImmersionOwner
import com.gyf.immersionbar.components.ImmersionProxy
import com.hjq.toast.ToastUtils
import com.jess.arms.integration.AppManager
import com.jess.arms.mvp.IPresenter

import com.jess.arms.utils.Preconditions.checkNotNull

abstract class BaseFragment<P : IPresenter> : com.jess.arms.base.BaseFragment<P>(), ImmersionOwner {

    /**
     * ImmersionBar代理类
     */
    private val mImmersionProxy = ImmersionProxy(this)

    override fun onDestroy() {
        super.onDestroy()
        mImmersionProxy.onDestroy()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        mImmersionProxy.isUserVisibleHint = isVisibleToUser
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mImmersionProxy.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mImmersionProxy.onActivityCreated(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        mImmersionProxy.onResume()
    }

    override fun onPause() {
        super.onPause()
        mImmersionProxy.onPause()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        mImmersionProxy.onHiddenChanged(hidden)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mImmersionProxy.onConfigurationChanged(newConfig)
    }

    /**
     * 懒加载，在view初始化完成之前执行
     * On lazy after view.
     */
    override fun onLazyBeforeView() {}

    /**
     * 懒加载，在view初始化完成之后执行
     * On lazy before view.
     */
    override fun onLazyAfterView() {}

    /**
     * Fragment用户可见时候调用
     * On visible.
     */
    override fun onVisible() {}

    /**
     * Fragment用户不可见时候调用
     * On invisible.
     */
    override fun onInvisible() {}

    /**
     * 是否可以实现沉浸式，当为true的时候才可以执行initImmersionBar方法
     * Immersion bar enabled boolean.
     *
     * @return the boolean
     */
    override fun immersionBarEnabled(): Boolean {
        return false
    }

    override fun initImmersionBar() {

    }

    open fun getThis(): Context {
        return mContext
    }

    open fun showMessage(message: String) {
        checkNotNull(message)
        ToastUtils.show(message)
    }

    open fun launchActivity(intent: Intent) {
        checkNotNull(intent)
        startActivity(intent)
    }

    open fun goActivity(clazz: Class<*>) {
        startActivity(Intent(getThis(), clazz))
    }

    open fun killMyself() {
        AppManager.getAppManager().topActivity?.finish()
    }

}
