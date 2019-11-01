package me.soushin.higou.base.app.base

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.gyf.immersionbar.components.ImmersionOwner
import com.gyf.immersionbar.components.ImmersionProxy
import com.hjq.toast.ToastUtils
import com.jess.arms.base.delegate.IFragment
import com.jess.arms.integration.cache.Cache
import com.jess.arms.integration.cache.CacheType
import com.jess.arms.integration.lifecycle.FragmentLifecycleable
import com.jess.arms.mvp.IPresenter
import com.jess.arms.utils.ArmsUtils
import com.trello.rxlifecycle2.android.FragmentEvent

import javax.inject.Inject

import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

import com.jess.arms.utils.Preconditions.checkNotNull

abstract class BaseFragment<P : IPresenter> : Fragment(), IFragment, FragmentLifecycleable, ImmersionOwner {
    private val mLifecycleSubject = BehaviorSubject.create<FragmentEvent>()
    private var mCache: Cache<String, Any>? = null
    var mContext: Context? = null
        protected set
    /**
     * ImmersionBar代理类
     */
    private val mImmersionProxy = ImmersionProxy(this)
    @Inject
    protected var mPresenter: P? = null//如果当前页面逻辑简单, Presenter 可以为 null

    @Synchronized
    override fun provideCache(): Cache<String, Any> {
        if (mCache == null) {
            mCache = ArmsUtils.obtainAppComponentFromContext(getThis())
                    .cacheFactory()
                    .build(CacheType.FRAGMENT_CACHE) as Cache<String, Any>
        }
        return mCache as Cache<String, Any>
    }

    init {
        //必须确保在Fragment实例化时setArguments()
        arguments = Bundle()
    }

    override fun provideLifecycleSubject(): Subject<FragmentEvent> {
        return mLifecycleSubject
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return initView(inflater, container, savedInstanceState)
    }

    /**
     * @param data 当不需要参数时 `data` 可以为 `null`
     */
    override fun setData(data: Any?) {

    }

    override fun onDestroy() {
        super.onDestroy()
        mImmersionProxy.onDestroy()
        if (mPresenter != null) mPresenter!!.onDestroy()//释放资源
        this.mPresenter = null
    }


    override fun onDetach() {
        super.onDetach()
        mContext = null
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

    override fun onConfigurationChanged(newConfig: Configuration?) {
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

    fun getThis():Context?{
        return mContext
    }

    fun showMessage(message: String) {
        checkNotNull(message)
        ToastUtils.show(message)
    }

    fun launchActivity(intent: Intent) {
        checkNotNull(intent)
        startActivity(intent)
    }

    fun goActivity(clazz: Class<*>) {
        startActivity(Intent(mContext, clazz))
    }

    fun killMyself() {

    }

    /**
     * 是否使用 EventBus
     * Arms 核心库现在并不会依赖某个 EventBus, 要想使用 EventBus, 还请在项目中自行依赖对应的 EventBus
     * 现在支持两种 EventBus, greenrobot 的 EventBus 和畅销书 《Android源码设计模式解析与实战》的作者 何红辉 所作的 AndroidEventBus
     * 确保依赖后, 将此方法返回 true, Arms 会自动检测您依赖的 EventBus, 并自动注册
     * 这种做法可以让使用者有自行选择三方库的权利, 并且还可以减轻 Arms 的体积
     *
     * @return 返回 `true` (默认为 `true`), Arms 会自动注册 EventBus
     */
    override fun useEventBus(): Boolean {
        return true
    }

}
