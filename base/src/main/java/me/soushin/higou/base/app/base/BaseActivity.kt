package me.soushin.higou.base.app.base

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.InflateException
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

import com.hjq.toast.ToastUtils
import com.jess.arms.base.BaseFragment
import com.jess.arms.base.delegate.IActivity
import com.jess.arms.integration.cache.Cache
import com.jess.arms.integration.cache.CacheType
import com.jess.arms.integration.lifecycle.ActivityLifecycleable
import com.jess.arms.mvp.IPresenter
import com.jess.arms.utils.ArmsUtils
import com.trello.rxlifecycle2.android.ActivityEvent

import javax.inject.Inject

import butterknife.ButterKnife
import butterknife.Unbinder
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import com.jess.arms.utils.Preconditions.checkNotNull
import com.jess.arms.utils.ThirdViewUtil.convertAutoView

abstract class BaseActivity<P : IPresenter> : AppCompatActivity(), IActivity, ActivityLifecycleable {
    private val mLifecycleSubject = BehaviorSubject.create<ActivityEvent>()
    private var mCache: Cache<String, Any>?=null
    private var mUnbinder: Unbinder? = null

    @Inject
    protected var mPresenter: P? = null//如果当前页面逻辑简单, Presenter 可以为 null

    @Synchronized
    override fun provideCache(): Cache<String, Any> {
        if (mCache == null) {
            mCache = ArmsUtils.obtainAppComponentFromContext(getThis())
                    .cacheFactory()
                    .build(CacheType.ACTIVITY_CACHE) as Cache<String, Any>
        }
        return mCache as Cache<String, Any>
    }

    fun getThis():Context{
        return this
    }

    override fun provideLifecycleSubject(): Subject<ActivityEvent> {
        return mLifecycleSubject
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        val view = convertAutoView(name, context, attrs)
        return view ?: super.onCreateView(name, context, attrs)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            val layoutResID = initView(savedInstanceState)
            //如果initView返回0,框架则不会调用setContentView(),当然也不会 Bind ButterKnife
            if (layoutResID != 0) {
                setContentView(layoutResID)
                //绑定到butterknife
                mUnbinder = ButterKnife.bind(this)
            }
        } catch (e: Exception) {
            if (e is InflateException) throw e
            e.printStackTrace()
        }
        initData(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mUnbinder != null && mUnbinder !== Unbinder.EMPTY)
            mUnbinder!!.unbind()
        this.mUnbinder = null
        if (mPresenter != null)
            mPresenter!!.onDestroy()//释放资源
        this.mPresenter = null
    }

    open fun showMessage(message: String) {
        checkNotNull(message)
        ToastUtils.show(message)
    }

    open fun launchActivity(intent: Intent) {
        checkNotNull(intent)
        startActivity(intent)
    }

    fun goActivity(clazz: Class<*>) {
        startActivity(Intent(this, clazz))
    }

    open fun showLoading() {

    }

    open fun hideLoading() {}

    open fun killMyself() {
        finish()
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
        return false
    }

    /**
     * 这个 [Activity] 是否会使用 [Fragment], 框架会根据这个属性判断是否注册 [FragmentManager.FragmentLifecycleCallbacks]
     * 如果返回 `false`, 那意味着这个 [Activity] 不需要绑定 [Fragment], 那你再在这个 [Activity] 中绑定继承于 [BaseFragment] 的 [Fragment] 将不起任何作用
     *
     * @return 返回 `true` (默认为 `true`), 则需要使用 [Fragment]
     */
    override fun useFragment(): Boolean {
        return true
    }


}
