package me.soushin.higou.base.app.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gyf.immersionbar.components.ImmersionOwner;
import com.gyf.immersionbar.components.ImmersionProxy;
import com.hjq.toast.ToastUtils;
import com.jess.arms.base.delegate.IFragment;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.integration.cache.CacheType;
import com.jess.arms.integration.lifecycle.FragmentLifecycleable;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.utils.ArmsUtils;
import com.trello.rxlifecycle2.android.FragmentEvent;

import javax.inject.Inject;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public abstract class BaseFragment<P extends IPresenter> extends Fragment
        implements IFragment, FragmentLifecycleable, ImmersionOwner {
    private final BehaviorSubject<FragmentEvent> mLifecycleSubject = BehaviorSubject.create();
    private Cache<String, Object> mCache;
    protected Context mContext;
    /**
     * ImmersionBar代理类
     */
    private ImmersionProxy mImmersionProxy = new ImmersionProxy(this);
    @Inject
    @Nullable
    protected P mPresenter;//如果当前页面逻辑简单, Presenter 可以为 null

    @NonNull
    @Override
    public synchronized Cache<String, Object> provideCache() {
        if (mCache == null) {
            mCache = ArmsUtils.obtainAppComponentFromContext(getActivity()).cacheFactory().build(CacheType.FRAGMENT_CACHE);
        }
        return mCache;
    }

    public BaseFragment() {
        //必须确保在Fragment实例化时setArguments()
        setArguments(new Bundle());
    }

    @NonNull
    @Override
    public final Subject<FragmentEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView(inflater, container, savedInstanceState);
    }

    /**
     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
     */
    public void setData(@Nullable Object data) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mImmersionProxy.onDestroy();
        if (mPresenter != null) mPresenter.onDestroy();//释放资源
        this.mPresenter = null;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mImmersionProxy.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImmersionProxy.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mImmersionProxy.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mImmersionProxy.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mImmersionProxy.onPause();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mImmersionProxy.onHiddenChanged(hidden);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mImmersionProxy.onConfigurationChanged(newConfig);
    }

    /**
     * 懒加载，在view初始化完成之前执行
     * On lazy after view.
     */
    @Override
    public void onLazyBeforeView() {
    }

    /**
     * 懒加载，在view初始化完成之后执行
     * On lazy before view.
     */
    @Override
    public void onLazyAfterView() {
    }

    /**
     * Fragment用户可见时候调用
     * On visible.
     */
    @Override
    public void onVisible() {
    }

    /**
     * Fragment用户不可见时候调用
     * On invisible.
     */
    @Override
    public void onInvisible() {
    }

    /**
     * 是否可以实现沉浸式，当为true的时候才可以执行initImmersionBar方法
     * Immersion bar enabled boolean.
     *
     * @return the boolean
     */
    @Override
    public boolean immersionBarEnabled() {
        return false;
    }

    @Override
    public void initImmersionBar() {

    }

    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ToastUtils.show(message);
    }

    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        startActivity(intent);
    }

    public void goActivity(Class clazz){
        startActivity(new Intent(getThis(),clazz));
    }

    public void killMyself() {

    }

    public Context getThis() {
        return mContext;
    }

    /**
     * 是否使用 EventBus
     * Arms 核心库现在并不会依赖某个 EventBus, 要想使用 EventBus, 还请在项目中自行依赖对应的 EventBus
     * 现在支持两种 EventBus, greenrobot 的 EventBus 和畅销书 《Android源码设计模式解析与实战》的作者 何红辉 所作的 AndroidEventBus
     * 确保依赖后, 将此方法返回 true, Arms 会自动检测您依赖的 EventBus, 并自动注册
     * 这种做法可以让使用者有自行选择三方库的权利, 并且还可以减轻 Arms 的体积
     *
     * @return 返回 {@code true} (默认为 {@code true}), Arms 会自动注册 EventBus
     */
    @Override
    public boolean useEventBus() {
        return true;
    }

}
